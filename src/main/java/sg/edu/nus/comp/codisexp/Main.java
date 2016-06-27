package sg.edu.nus.comp.codisexp;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import fj.data.Either;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.edu.nus.comp.codis.*;
import sg.edu.nus.comp.codis.ast.*;
import sg.edu.nus.comp.codis.ast.theory.*;
import java.util.concurrent.TimeUnit;


import java.util.*;

/**
 * Created by Sergey Mechtaev on 7/4/2016.
 */
public class Main {

    public static void main(String[] args) {

//        if (true) {
//            runTest2();
//            return;
//        }

        Logger logger = LoggerFactory.getLogger(Main.class);

        List<Subject> subjects = new ArrayList<>();
        //subjects.add(new Grade());
        //subjects.add(new Median());
        subjects.add(new Smallest());
        subjects.add(new Tcas());

        Solver solver = MathSAT.buildSolver();
        InterpolatingSolver iSolver = MathSAT.buildInterpolatingSolver();

        boolean useBVEncoding = false; // TODO: configure solver to compute BV interpolants

        Map<String, Synthesis> synthesizers = new HashMap<>();

        //synthesizers.put("CBS", new ComponentBasedSynthesis(solver, true, Optional.empty()));a
        //synthesizers.put("CEGIS+CBS", new CEGIS(new ComponentBasedSynthesis(solver, true, Optional.empty()), solver));
        //synthesizers.put("CEGIS+CBS(3)", new CEGIS(new ComponentBasedSynthesis(solver, true, Optional.of(3)), solver));
        //synthesizers.put("TBS(3)", new TreeBoundedSynthesis(iSolver, new TBSConfig(3)));
        //synthesizers.put("TBS(2)", new TreeBoundedSynthesis(iSolver, new TBSConfig(2)));
        //synthesizers.put("CEGIS+TBS(3)", new CEGIS(new TreeBoundedSynthesis(iSolver, 3, true), solver));
        //synthesizers.put("CEGIS+TBS(5)", new CEGIS(new TreeBoundedSynthesis(iSolver, new TBSConfig(5)), solver));
        //synthesizers.put("CODIS(2)", new CODIS(solver, iSolver, new CODISConfig(2)));
        CODISConfig config = new CODISConfig(3)
                .setMaximumLeafExpansions(3)
                .setIterationsBeforeRestart(50);
        synthesizers.put("CODIS(3)", new CODIS(solver, iSolver, config));

        for (Map.Entry<String, Synthesis> entry : synthesizers.entrySet()) {
            logger.info("Evaluating " + entry.getKey() + " synthesizer");
            Synthesis synthesizer = entry.getValue();
            for (Subject subject : subjects) {
                logger.info("Subject: " + subject.getName());
                List<String> ids = subject.getTestSuiteIds();
                for (String id : ids) {
                    //if (!id.equals("all")) continue;
                    logger.info("Test suite: " + id);
                    List<TestCase> testSuite = subject.getTestSuite(id, useBVEncoding);
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
            }
        }
    }

    static void runTest1() {
        Solver solver = Z3.buildSolver();
        InterpolatingSolver iSolver = Z3.buildInterpolatingSolver();

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
