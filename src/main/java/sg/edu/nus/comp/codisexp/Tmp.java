package sg.edu.nus.comp.codisexp;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apache.commons.lang3.tuple.Pair;
import sg.edu.nus.comp.codis.*;
import sg.edu.nus.comp.codis.ast.*;
import sg.edu.nus.comp.codis.ast.theory.Add;
import sg.edu.nus.comp.codis.ast.theory.IntConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Sergey Mechtaev on 13/6/2016.
 */
public class Tmp {

    static void run() {
        Solver solver = Z3.buildSolver();
        InterpolatingSolver iSolver = Z3.buildInterpolatingSolver();

        Synthesis synthesizer = new CODIS(solver, iSolver, 2);

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
            System.out.println("Synthesized patch: " + node);
        }
    }
}
