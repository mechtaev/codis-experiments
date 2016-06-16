package sg.edu.nus.comp.codisexp;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Multiset;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.edu.nus.comp.codis.*;
import sg.edu.nus.comp.codis.ast.*;
import sg.edu.nus.comp.codis.ast.theory.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergey Mechtaev on 7/4/2016.
 */
public class Main {



    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);

        List<Subject> subjects = new ArrayList<>();
        //subjects.add(new Tcas());
        //subjects.add(new Grade());
        //subjects.add(new Median());
        subjects.add(new Smallest());

        Solver solver = Z3.buildSolver();
        InterpolatingSolver iSolver = MathSAT.buildInterpolatingSolver();

        boolean useBVEncoding = false; // TODO: configure solver to compute BV interpolants

        Map<String, Synthesis> synthesizers = new HashMap<>();

        //synthesizers.put("CBS", new ComponentBasedSynthesis(solver, true, Optional.empty()));
        //synthesizers.put("CEGIS+CBS", new CEGIS(new ComponentBasedSynthesis(solver, true, Optional.empty()), solver));
        //synthesizers.put("CEGIS+CBS(3)", new CEGIS(new ComponentBasedSynthesis(solver, true, Optional.of(3)), solver));
        //synthesizers.put("TBS(3)", new TreeBoundedSynthesis(iSolver, 3, true));
        //synthesizers.put("TBS(2)", new TreeBoundedSynthesis(iSolver, 2, true));
        //synthesizers.put("CEGIS+TBS(3)", new CEGIS(new TreeBoundedSynthesis(iSolver, 3, true), solver));
        //synthesizers.put("CEGIS+TBS(2)", new CEGIS(new TreeBoundedSynthesis(iSolver, 2, true), solver));
        synthesizers.put("CODIS(2)", new CODIS(solver, iSolver, 2));
        //synthesizers.put("CODIS(3)", new CODIS(solver, iSolver, 3));

        for (Map.Entry<String, Synthesis> entry : synthesizers.entrySet()) {
            logger.info("Evaluating " + entry.getKey() + " synthesizer");
            Synthesis synthesizer = entry.getValue();
            for (Subject subject : subjects) {
                logger.info("Subject: " + subject.getName());
                List<String> ids = subject.getTestSuiteIds();
                for (String id : ids) {
                    if (!id.equals("whitebox")) continue;
                    logger.info("Test suite: " + id);
                    List<TestCase> testSuite = subject.getTestSuite(id, useBVEncoding);
                    Multiset<Node> components = subject.getComponents(useBVEncoding);
                    long startTime = System.currentTimeMillis();
                    Optional<Pair<Program, Map<Parameter, Constant>>> result = synthesizer.synthesize(testSuite, components);
                    long estimatedTimeMs = System.currentTimeMillis() - startTime;
                    long estimatedTimeSec = TimeUnit.SECONDS.convert(estimatedTimeMs, TimeUnit.MILLISECONDS);
                    logger.info("Time: " + estimatedTimeSec + " sec");
                    if(result.isPresent()) {
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
}
