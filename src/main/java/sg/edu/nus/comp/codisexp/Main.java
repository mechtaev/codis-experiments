package sg.edu.nus.comp.codisexp;

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

        LoggerFactory.getLogger(Main.class).info("from Main");

        Hole i = new Hole("i", IntType.TYPE, Node.class);
        Hole j = new Hole("j", IntType.TYPE, Node.class);
        Hole a = new Hole("a", BoolType.TYPE, Node.class);
        Hole b = new Hole("b", BoolType.TYPE, Node.class);

        Synthesis synthesizer = new CBS(Z3.getInstance());

        Map<Node, Integer> componentMultiset = new HashMap<>();
        ProgramVariable x = new ProgramVariable("x", IntType.TYPE);
        ProgramVariable y = new ProgramVariable("y", IntType.TYPE);
        componentMultiset.put(x, 2);
        componentMultiset.put(y, 2);
        componentMultiset.put(new Greater(i, j), 2);
        componentMultiset.put(new And(a, b), 1);
        componentMultiset.put(new Or(a, b), 1);
        componentMultiset.put(new Parameter("alpha", IntType.TYPE), 1);
        componentMultiset.put(new Parameter("beta", IntType.TYPE), 1);


        ArrayList<TestCase> testSuite = new ArrayList<>();
        Map<ProgramVariable, Node> assignment1 = new HashMap<>();
        assignment1.put(x, IntConst.of(2));
        assignment1.put(y, IntConst.of(1));
        testSuite.add(new TestCase(assignment1, BoolConst.TRUE));

        Map<ProgramVariable, Node> assignment2 = new HashMap<>();
        assignment2.put(x, IntConst.of(1));
        assignment2.put(y, IntConst.of(3));
        testSuite.add(new TestCase(assignment2, BoolConst.FALSE));

        Map<ProgramVariable, Node> assignment3 = new HashMap<>();
        assignment3.put(x, IntConst.of(2));
        assignment3.put(y, IntConst.of(0));
        testSuite.add(new TestCase(assignment3, BoolConst.FALSE));

        Map<ProgramVariable, Node> assignment4 = new HashMap<>();
        assignment4.put(x, IntConst.of(0));
        assignment4.put(y, IntConst.of(1));
        testSuite.add(new TestCase(assignment4, BoolConst.FALSE));

        Map<ProgramVariable, Node> assignment5 = new HashMap<>();
        assignment5.put(x, IntConst.of(0));
        assignment5.put(y, IntConst.of(0));
        testSuite.add(new TestCase(assignment5, BoolConst.FALSE));

        Optional<Node> node = synthesizer.synthesize(testSuite, componentMultiset);
        if (node.isPresent()) {
            System.out.println("Original: " + node.get());
            System.out.println("Simplified: " + Simplifier.symplify(node.get()));
        } else {
            System.out.println("FAIL");
        }
    }
}
