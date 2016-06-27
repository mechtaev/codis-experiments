package sg.edu.nus.comp.codisexp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import fj.data.Either;
import org.apache.commons.lang3.tuple.Pair;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.edu.nus.comp.codis.*;
import sg.edu.nus.comp.codis.ast.*;
import sg.edu.nus.comp.codis.ast.theory.*;

import java.util.concurrent.TimeUnit;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Sergey Mechtaev on 7/4/2016.
 */
public class Main {

    @Option(name="-s", usage="subject name", metaVar="SUBJECT")
    private String subjectName;

    @Option(name="-a", usage="synthesis algorithm", metaVar="ALGORITHM")
    private String algorithm;

    @Option(name="-t", usage="test suite id", metaVar="TESTS")
    private String testSuiteId;

    @Option(name="-l", usage="list available configurations (JSON)")
    private boolean list;

    @Argument
    private List<String> arguments = new ArrayList<>();


    private Map<String, Subject> subjects;
    private Map<String, Synthesis> synthesizers;

    {
        subjects = new HashMap<>();
        subjects.put("grade", new Grade());
        subjects.put("median", new Median());
        subjects.put("smallest", new Smallest());
        subjects.put("tcas", new Tcas());

        Solver solver = MathSAT.buildSolver();
        InterpolatingSolver iSolver = MathSAT.buildInterpolatingSolver();

        synthesizers = new HashMap<>();
        synthesizers.put("CBS", new ComponentBasedSynthesis(solver, true, Optional.empty()));
        synthesizers.put("CEGIS+CBS", new CEGIS(new ComponentBasedSynthesis(solver, true, Optional.empty()), solver));
        synthesizers.put("TBS(3)", new TreeBoundedSynthesis(iSolver, new TBSConfig(3)));
        synthesizers.put("CEGIS+TBS(3)", new CEGIS(new TreeBoundedSynthesis(iSolver, new TBSConfig(3)), solver));
        synthesizers.put("CODIS(3)", new CODIS(solver, iSolver, new CODISConfig(3)
                .setIterationsBeforeRestart(100)
                .setMaximumConflictsCheck(4)
                .forbidStructure()));
        synthesizers.put("CODIS-NOCL(3)", new CODIS(solver, iSolver, new CODISConfig(3)
                .setIterationsBeforeRestart(100)
                .forbidStructure()
                .disableConflictLearning()));
    }


    public static void main(String[] args) {
        new Main().run(args);
    }

    private void run(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        if (list) {
            printConfigs();
            return;
        }

        Logger logger = LoggerFactory.getLogger(Main.class);

        boolean useBVEncoding = false; // TODO: configure solver to compute BV interpolants

        Synthesis synthesizer = synthesizers.get(algorithm);
        Subject subject = subjects.get(subjectName);

        logger.info("Evaluating algorithm " + algorithm + " on subject " + subjectName + " with test suite " + testSuiteId);

        List<TestCase> testSuite = subject.getTestSuite(testSuiteId, useBVEncoding);
        Multiset<Node> components = subject.getComponents(useBVEncoding);

        long startTime = System.currentTimeMillis();

        Optional<Pair<Program, Map<Parameter, Constant>>> result = synthesizer.synthesize(testSuite, components);

        long estimatedTimeMs = System.currentTimeMillis() - startTime;
        long estimatedTimeSec = TimeUnit.SECONDS.convert(estimatedTimeMs, TimeUnit.MILLISECONDS);
        logger.info("Time: " + estimatedTimeSec + " sec");

        if (result.isPresent()) {
            Node node = result.get().getLeft().getSemantics(result.get().getRight());
            logger.info("Synthesized patch: " + node);
            logger.info("Simplified: " + Simplifier.simplify(node));
        } else {
            logger.info("FAIL");
        }
    }

    private void printConfigs() {
        Configs configs = new Configs();
        configs.setSubjects(subjects.keySet().stream()
                .map(s -> new SubjectConfig(s, subjects.get(s).getTestSuiteIds()))
                .collect(Collectors.toList()));
        configs.setAlgorithms(new ArrayList<>(synthesizers.keySet()));
        System.out.println(JSON.toJSONString(configs, SerializerFeature.PrettyFormat));

    }

    private class Configs {
        private List<SubjectConfig> subjects;
        private List<String> algorithms;
        public List<SubjectConfig> getSubjects() {
            return subjects;
        }
        public void setSubjects(List<SubjectConfig> subjects) {
            this.subjects = subjects;
        }
        public List<String> getAlgorithms() {
            return algorithms;
        }
        public void setAlgorithms(List<String> algorithms) {
            this.algorithms = algorithms;
        }
    }

    private class SubjectConfig {
        private String name;
        private List<String> tests;
        public SubjectConfig(String name, List<String> tests) {
            this.name = name;
            this.tests = tests;
        }
        public String getName() {
            return name;
        }
        public List<String> getTests() {
            return tests;
        }
    }

    static void runTest1() {
        Solver solver = MathSAT.buildSolver();
        InterpolatingSolver iSolver = MathSAT.buildInterpolatingSolver();

        CODISConfig config = new CODISConfig(2);
        Synthesis synthesizer = new CODIS(solver, iSolver, config);

        ProgramVariable x = ProgramVariable.mkInt("x");
        ProgramVariable y = ProgramVariable.mkInt("y");
        ProgramVariable z = ProgramVariable.mkInt("z");

        Multiset<Node> components = HashMultiset.create();
        components.add(x);
        components.add(y);
        components.add(z);
        components.add(Components.ADD);

        ArrayList<TestCase> testSuite = new ArrayList<>();
        Map<ProgramVariable, Node> assignment1 = new HashMap<>();
        assignment1.put(x, IntConst.of(1));
        assignment1.put(y, IntConst.of(1));
        assignment1.put(z, IntConst.of(1));
        testSuite.add(TestCase.ofAssignment(assignment1, IntConst.of(2)));

        Map<ProgramVariable, Node> assignment2 = new HashMap<>();
        assignment2.put(x, IntConst.of(2));
        assignment2.put(y, IntConst.of(1));
        assignment2.put(z, IntConst.of(1));
        testSuite.add(TestCase.ofAssignment(assignment2, IntConst.of(3)));

        Map<ProgramVariable, Node> assignment3 = new HashMap<>();
        assignment3.put(x, IntConst.of(1));
        assignment3.put(y, IntConst.of(2));
        assignment3.put(z, IntConst.of(1));
        testSuite.add(TestCase.ofAssignment(assignment3, IntConst.of(3)));

        Optional<Pair<Program, Map<Parameter, Constant>>> result = synthesizer.synthesize(testSuite, components);

        if (result.isPresent()) {
            Node node = result.get().getLeft().getSemantics(result.get().getRight());
            System.out.println("Synthesized program: " + node);
        }
    }

    static void runTest2() {
        InterpolatingSolver iSolver = MathSAT.buildInterpolatingSolver();

        SynthesisWithLearning synthesizer = new TreeBoundedSynthesis(iSolver, new TBSConfig(3).enableConciseInterpolants());

        ProgramVariable x = ProgramVariable.mkInt("x");
        ProgramVariable y = ProgramVariable.mkInt("y");
        ProgramVariable z = ProgramVariable.mkInt("z");

        Multiset<Node> components = HashMultiset.create();
        components.add(x);
        components.add(y);
        components.add(z);
        components.add(Components.ADD);

        ArrayList<TestCase> testSuite = new ArrayList<>();
        Map<ProgramVariable, Node> assignment1 = new HashMap<>();
        assignment1.put(x, IntConst.of(1));
        assignment1.put(y, IntConst.of(1));
        assignment1.put(z, IntConst.of(1));
        testSuite.add(TestCase.ofAssignment(assignment1, IntConst.of(-1)));

        Either<Pair<Program, Map<Parameter, Constant>>, Node> result = synthesizer.synthesizeOrLearn(testSuite, components);

        System.out.println("Conflict: " + Printer.print(result.right().value()));
    }


}
