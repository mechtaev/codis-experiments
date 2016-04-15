package sg.edu.nus.comp.codisexp;

import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.edu.nus.comp.codis.*;
import sg.edu.nus.comp.codis.ast.*;
import sg.edu.nus.comp.codis.ast.theory.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Sergey Mechtaev on 7/4/2016.
 */
public class Main {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);

        DivergentTest generator = new DivergentTest(Z3.getInstance());
        Synthesis synthesizer = new CBS(Z3.getInstance());

        // correct repair is (x - y < 2)

        Map<Node, Integer> componentMultiset = new HashMap<>();
        ProgramVariable x = ProgramVariable.mkInt("x");
        ProgramVariable y = ProgramVariable.mkInt("y");
        Parameter p = Parameter.mkInt("p");
        componentMultiset.put(x,1);
        componentMultiset.put(y,1);
        componentMultiset.put(p, 1);
        componentMultiset.put(Components.ADD,1);
        componentMultiset.put(Components.SUB, 1);
        componentMultiset.put(Components.GT,1);
        componentMultiset.put(Components.GE, 1);

        ArrayList<TestCase> testSuite = new ArrayList<>();
        Map<ProgramVariable, Node> assignment1 = new HashMap<>();
        assignment1.put(x,IntConst.of(2));
        assignment1.put(y,IntConst.of(1));
        testSuite.add(new TestCase(assignment1, BoolConst.TRUE));

        Map<ProgramVariable, Node> assignment2 = new HashMap<>();
        assignment2.put(x, IntConst.of(1));
        assignment2.put(y, IntConst.of(4));
        testSuite.add(new TestCase(assignment2, BoolConst.TRUE));

        Map<ProgramVariable, Node> assignment3 = new HashMap<>();
        assignment3.put(x, IntConst.of(4));
        assignment3.put(y, IntConst.of(1));
        testSuite.add(new TestCase(assignment3, BoolConst.FALSE));

        Map<ProgramVariable, Node> assignment4 = new HashMap<>();
        assignment4.put(x, IntConst.of(1));
        assignment4.put(y, IntConst.of(1));
        testSuite.add(new TestCase(assignment4, BoolConst.TRUE));

        logger.info("Want to generate (x - y < 2) but don't have enough tests");

        Optional<Node> node = synthesizer.synthesize(testSuite, componentMultiset);
        if(node.isPresent()) {
            logger.info("Synthesized patch: " + node.get());
            logger.info("Simplified: "
                    + Simplifier.symplify(node.get()));
        } else {
            logger.info("FAIL");
        }

        logger.info("Generating additional test ...");

        Optional<Triple<TestCase, Node, Node>> result = generator.generate(componentMultiset, testSuite);

        if (result.isPresent()) {
            logger.info("Divergent test: " + result.get().getLeft());
            logger.info("First program: " + result.get().getMiddle());
            logger.info("Second program: " + result.get().getRight());
        }

    }
}
