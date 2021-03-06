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
public class Smallest implements Subject {

    private static ProgramVariable num1 = ProgramVariable.mkInt("num1");
    private static ProgramVariable num2 = ProgramVariable.mkInt("num2");
    private static ProgramVariable num3 = ProgramVariable.mkInt("num3");
    private static ProgramVariable num4 = ProgramVariable.mkInt("num4");

    private static Multiset<Node> gradeIntComponents(String id) {
        Multiset<Node> components = HashMultiset.create();

        switch (id) {
            case "adhoc":
                components.add(Parameter.mkInt("parameter1"));
                components.add(Parameter.mkInt("parameter2"));
                components.add(num1, 4);
                components.add(num2, 4);
                components.add(num3, 4);
                components.add(num4, 4);
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
                components.add(Parameter.mkInt("parameter1"));
                components.add(num1, 2);
                components.add(num2, 2);
                components.add(num3, 2);
                components.add(num4, 2);
                components.add(Components.ADD, 2);
                components.add(Components.SUB, 2);
                components.add(Components.GT, 2);
                components.add(Components.GE, 2);
                components.add(Components.MINUS, 2);
                components.add(Components.ITE, 2);
                components.add(Components.AND, 1);
                components.add(Components.OR, 1);
                components.add(Components.NOT, 1);
                break;
            case "50":
                components.add(Parameter.mkInt("parameter1"));
                components.add(Parameter.mkInt("parameter2"));
                components.add(num1, 4);
                components.add(num2, 4);
                components.add(num3, 4);
                components.add(num4, 4);
                components.add(Components.ADD, 4);
                components.add(Components.SUB, 4);
                components.add(Components.GT, 4);
                components.add(Components.GE, 4);
                components.add(Components.MINUS, 4);
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
            inputPath = FileSystems.getDefault().getPath("data", "smallest-whitebox", id + ".in");
            outputPath = FileSystems.getDefault().getPath("data", "smallest-whitebox", id + ".out");
        } else {
            inputPath = FileSystems.getDefault().getPath("data", "smallest-blackbox", id + ".in");
            outputPath = FileSystems.getDefault().getPath("data", "smallest-blackbox", id + ".out");
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
        assignment.put(num1, IntConst.of(inputs.get(0)));
        assignment.put(num2, IntConst.of(inputs.get(1)));
        assignment.put(num3, IntConst.of(inputs.get(2)));
        assignment.put(num4, IntConst.of(inputs.get(3)));
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
        for (int i=1; i<=8; i++) {
            whitebox.add(loadTestById(i, true));
        }
        for (int i=1; i<=8; i++) {
            blackbox.add(loadTestById(i, false));
        }
        all.addAll(whitebox);
        all.addAll(blackbox);
        switch (id) {
            case "whitebox":
                return whitebox;
            case "blackbox":
                return blackbox;
            default:
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
