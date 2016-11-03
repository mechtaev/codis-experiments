package sg.edu.nus.comp.codisexp;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import sg.edu.nus.comp.codis.Components;
import sg.edu.nus.comp.codis.AssignmentTestCase;
import sg.edu.nus.comp.codis.ast.*;
import sg.edu.nus.comp.codis.ast.theory.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Sergey Mechtaev on 16/6/2016.
 */
public class Grade implements Subject {

    private static ProgramVariable aval = ProgramVariable.mkInt("aval");
    private static ProgramVariable bval = ProgramVariable.mkInt("bval");
    private static ProgramVariable cval = ProgramVariable.mkInt("cval");
    private static ProgramVariable dval = ProgramVariable.mkInt("dval");
    private static ProgramVariable score = ProgramVariable.mkInt("score");

    private static Multiset<Node> gradeIntComponents(String id) {
        Multiset<Node> components = HashMultiset.create();

        switch (id) {
            case "adhoc":
                components.add(IntConst.of(0), 2); //A
                components.add(IntConst.of(1), 2); //B
                components.add(IntConst.of(2), 2); //C
                components.add(IntConst.of(3), 2); //D
                components.add(IntConst.of(4), 2); //failed
                components.add(Parameter.mkInt("parameter1"));
                components.add(Parameter.mkInt("parameter2"));
                components.add(aval, 3);
                components.add(bval, 3);
                components.add(cval, 3);
                components.add(dval, 3);
                components.add(score, 4);
                components.add(Components.ADD, 2);
                components.add(Components.SUB, 2);
                components.add(Components.GT, 2);
                components.add(Components.GE, 2);
                components.add(Components.MINUS, 2);
                components.add(Components.ITE, 4);
                components.add(Components.AND, 2);
                components.add(Components.OR, 2);
                components.add(Components.NOT, 2);
                break;
            case "25":
                components.add(IntConst.of(0), 1); //A
                components.add(IntConst.of(1), 1); //B
                components.add(IntConst.of(2), 1); //C
                components.add(IntConst.of(3), 1); //D
                components.add(IntConst.of(4), 1); //failed
                components.add(Parameter.mkInt("parameter1"));
                components.add(aval, 2);
                components.add(bval, 2);
                components.add(cval, 2);
                components.add(dval, 2);
                components.add(score, 2);
                components.add(Components.ADD, 1);
                components.add(Components.SUB, 1);
                components.add(Components.GT, 1);
                components.add(Components.GE, 1);
                components.add(Components.MINUS, 1);
                components.add(Components.ITE,2);
                components.add(Components.AND, 1);
                components.add(Components.OR, 1);
                components.add(Components.NOT, 1);
                break;
            case "50":
                components.add(IntConst.of(0), 2); //A
                components.add(IntConst.of(1), 2); //B
                components.add(IntConst.of(2), 2); //C
                components.add(IntConst.of(3), 2); //D
                components.add(IntConst.of(4), 2); //failed
                components.add(Parameter.mkInt("parameter1"));
                components.add(Parameter.mkInt("parameter2"));
                components.add(aval, 4);
                components.add(bval, 4);
                components.add(cval, 4);
                components.add(dval, 4);
                components.add(score, 4);
                components.add(Components.ADD, 2);
                components.add(Components.SUB, 2);
                components.add(Components.GT, 2);
                components.add(Components.GE, 2);
                components.add(Components.MINUS, 2);
                components.add(Components.ITE, 4);
                components.add(Components.AND, 2);
                components.add(Components.OR, 2);
                components.add(Components.NOT, 2);
                break;
        }
        return components;
    }

    private static AssignmentTestCase loadTestById(int id, boolean whitebox) {
        ArrayList<AssignmentTestCase> testSuite = new ArrayList<>();
        Path inputPath;
        Path outputPath;
        if (whitebox) {
            inputPath = FileSystems.getDefault().getPath("data", "grade-whitebox", id + ".in");
            outputPath = FileSystems.getDefault().getPath("data", "grade-whitebox", id + ".out");
        } else {
            inputPath = FileSystems.getDefault().getPath("data", "grade-blackbox", id + ".in");
            outputPath = FileSystems.getDefault().getPath("data", "grade-blackbox", id + ".out");
        }
        List<Integer> inputs = null;
        Integer output = null;
        try {
            String[] inputData = Files.readAllLines(inputPath).get(0).split("\\s+");
            inputs = Arrays.asList(inputData).stream().map(Integer::parseInt).collect(Collectors.toList());
            String outputData = Files.readAllLines(outputPath).get(0);
            output = Integer.parseInt(outputData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<ProgramVariable, Node> assignment = new HashMap<>();
        assignment.put(aval, IntConst.of(inputs.get(0)));
        assignment.put(bval, IntConst.of(inputs.get(1)));
        assignment.put(cval, IntConst.of(inputs.get(2)));
        assignment.put(dval, IntConst.of(inputs.get(3)));
        assignment.put(score, IntConst.of(inputs.get(4)));
        AssignmentTestCase testCase = new AssignmentTestCase(assignment, IntConst.of(output));
        if (whitebox) {
            testCase.setId("w" + id);
        } else {
            testCase.setId("b" + id);
        }
        return testCase;
    }


    @Override
    public List<String> getTestSuiteIds() {
        ArrayList<String> ids = new ArrayList<>();
        ids.add("whitebox");
        ids.add("blackbox");
        ids.add("all");
        return ids;
    }

    @Override
    public List<AssignmentTestCase> getTestSuite(String id, boolean useBVEncoding) {
        List<AssignmentTestCase> whitebox = new ArrayList<>();
        List<AssignmentTestCase> blackbox = new ArrayList<>();
        List<AssignmentTestCase> all = new ArrayList<>();
        for (int i=1; i<=9; i++) {
            whitebox.add(loadTestById(i, true));
        }
        for (int i=1; i<=9; i++) {
            blackbox.add(loadTestById(i, false));
        }
        all.addAll(whitebox);
        all.addAll(blackbox);
        if (id.equals("whitebox")) {
            return whitebox;
        } else if (id.equals("blackbox")) {
            return blackbox;
        } else {
            return all;
        }
    }

    @Override
    public List<String> getComponentsIds() {
        ArrayList<String> ids = new ArrayList<>();
        ids.add("adhoc");
        ids.add("25");
        ids.add("50");
        return ids;
    }


    @Override
    public Multiset<Node> getComponents(String id, String testId, boolean useBVEncoding) {
        if (useBVEncoding) {
            throw new UnsupportedOperationException();
            //return gradeBVComponents();
        } else {
            return gradeIntComponents(id);
        }
    }

}
