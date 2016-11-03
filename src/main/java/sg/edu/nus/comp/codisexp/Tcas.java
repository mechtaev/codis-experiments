package sg.edu.nus.comp.codisexp;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
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

/**
 * Created by Sergey Mechtaev on 15/4/2016.
 */
public class Tcas implements Subject {

    private static Logger logger = LoggerFactory.getLogger(Tcas.class);

    private static ProgramVariable Cur_Vertical_Sep = ProgramVariable.mkInt("Cur_Vertical_Sep");
    private static ProgramVariable High_Confidence = ProgramVariable.mkInt("High_Confidence");
    private static ProgramVariable Two_of_Three_Reports_Valid = ProgramVariable.mkInt("Two_of_Three_Reports_Valid");
    private static ProgramVariable Own_Tracked_Alt = ProgramVariable.mkInt("Own_Tracked_Alt");
    private static ProgramVariable Own_Tracked_Alt_Rate = ProgramVariable.mkInt("Own_Tracked_Alt_Rate");
    private static ProgramVariable Other_Tracked_Alt = ProgramVariable.mkInt("Other_Tracked_Alt");
    private static ProgramVariable Alt_Layer_Value = ProgramVariable.mkInt("Alt_Layer_Value");
    private static ProgramVariable Up_Separation = ProgramVariable.mkInt("Up_Separation");
    private static ProgramVariable Down_Separation = ProgramVariable.mkInt("Down_Separation");
    private static ProgramVariable Other_RAC = ProgramVariable.mkInt("Other_RAC");
    private static ProgramVariable Other_Capability = ProgramVariable.mkInt("Other_Capability");
    private static ProgramVariable Climb_Inhibit = ProgramVariable.mkInt("Climb_Inhibit");

    private static ProgramVariable BV_Cur_Vertical_Sep = ProgramVariable.mkBV("Cur_Vertical_Sep", 32);
    private static ProgramVariable BV_High_Confidence = ProgramVariable.mkBV("High_Confidence", 32);
    private static ProgramVariable BV_Two_of_Three_Reports_Valid = ProgramVariable.mkBV("Two_of_Three_Reports_Valid", 32);
    private static ProgramVariable BV_Own_Tracked_Alt = ProgramVariable.mkBV("Own_Tracked_Alt", 32);
    private static ProgramVariable BV_Own_Tracked_Alt_Rate = ProgramVariable.mkBV("Own_Tracked_Alt_Rate", 32);
    private static ProgramVariable BV_Other_Tracked_Alt = ProgramVariable.mkBV("Other_Tracked_Alt", 32);
    private static ProgramVariable BV_Alt_Layer_Value = ProgramVariable.mkBV("Alt_Layer_Value", 32);
    private static ProgramVariable BV_Up_Separation = ProgramVariable.mkBV("Up_Separation", 32);
    private static ProgramVariable BV_Down_Separation = ProgramVariable.mkBV("Down_Separation", 32);
    private static ProgramVariable BV_Other_RAC = ProgramVariable.mkBV("Other_RAC", 32);
    private static ProgramVariable BV_Other_Capability = ProgramVariable.mkBV("Other_Capability", 32);
    private static ProgramVariable BV_Climb_Inhibit = ProgramVariable.mkBV("Climb_Inhibit", 32);

    private static Multiset<Node> tcasIntComponents() {
        Multiset<Node> components = HashMultiset.create();
        components.add(IntConst.of(0));
        components.add(IntConst.of(1));
        components.add(IntConst.of(2));
        components.add(Parameter.mkInt("parameter1"));
        components.add(Parameter.mkInt("parameter2"));
        components.add(Cur_Vertical_Sep, 2);
        components.add(High_Confidence, 2);
        components.add(Two_of_Three_Reports_Valid, 2);
        components.add(Own_Tracked_Alt, 2);
        components.add(Own_Tracked_Alt_Rate, 2);
        components.add(Other_Tracked_Alt, 2);
        components.add(Alt_Layer_Value, 2);
        components.add(Up_Separation, 2);
        components.add(Down_Separation, 2);
        components.add(Other_RAC, 2);
        components.add(Other_Capability, 2);
        components.add(Climb_Inhibit, 2);
        components.add(Components.ADD, 2);
        components.add(Components.SUB, 2);
        components.add(Components.GT, 2);
        components.add(Components.GE, 2);
        components.add(Components.MINUS, 2);
        components.add(Components.ITE, 4);
        components.add(Components.AND, 2);
        components.add(Components.OR, 2);
        components.add(Components.NOT, 2);
        return components;
    }

    private static Multiset<Node> tcasBVComponents() {
        Multiset<Node> components = HashMultiset.create();
        components.add(BVConst.ofLong(0, 32));
        components.add(BVConst.ofLong(1, 32));
        components.add(BVConst.ofLong(2, 32));
        components.add(Parameter.mkBV("parameter1", 32));
        components.add(Parameter.mkBV("parameter2", 32));
        components.add(BV_Cur_Vertical_Sep, 2);
        components.add(BV_High_Confidence, 2);
        components.add(BV_Two_of_Three_Reports_Valid, 2);
        components.add(BV_Own_Tracked_Alt, 2);
        components.add(BV_Own_Tracked_Alt_Rate, 2);
        components.add(BV_Other_Tracked_Alt, 2);
        components.add(BV_Alt_Layer_Value, 2);
        components.add(BV_Up_Separation, 2);
        components.add(BV_Down_Separation, 2);
        components.add(BV_Other_RAC, 2);
        components.add(BV_Other_Capability, 2);
        components.add(BV_Climb_Inhibit, 2);
        components.add(Components.ofSize(32).BVADD, 2);
        components.add(Components.ofSize(32).BVSUB, 2);
        components.add(Components.ofSize(32).BVSGT, 2); //FIXME: signed or unsigned?
        components.add(Components.ofSize(32).BVSGE, 2);
        components.add(Components.ofSize(32).BVNEG, 2);
        components.add(Components.ofSize(32).BVITE, 4);
        components.add(Components.AND, 2);
        components.add(Components.OR, 2);
        components.add(Components.NOT, 2);
        return components;
    }

    private static AssignmentTestCase getTestById(int id, ArrayList<TcasTestCase> data) {
        Map<ProgramVariable, Node> assignment = new HashMap<>();
        TcasTestCase tcasTest = data.get(id);
        assignment.put(Cur_Vertical_Sep, IntConst.of(tcasTest.getCur_Vertical_Sep()));
        assignment.put(High_Confidence, IntConst.of(tcasTest.getHigh_Confidence()));
        assignment.put(Two_of_Three_Reports_Valid, IntConst.of(tcasTest.getTwo_of_Three_Reports_Valid()));
        assignment.put(Own_Tracked_Alt, IntConst.of(tcasTest.getOwn_Tracked_Alt()));
        assignment.put(Own_Tracked_Alt_Rate, IntConst.of(tcasTest.getOwn_Tracked_Alt_Rate()));
        assignment.put(Other_Tracked_Alt, IntConst.of(tcasTest.getOther_Tracked_Alt()));
        assignment.put(Alt_Layer_Value, IntConst.of(tcasTest.getAlt_Layer_Value()));
        assignment.put(Up_Separation, IntConst.of(tcasTest.getUp_Separation()));
        assignment.put(Down_Separation, IntConst.of(tcasTest.getDown_Separation()));
        assignment.put(Other_RAC, IntConst.of(tcasTest.getOther_RAC()));
        assignment.put(Other_Capability, IntConst.of(tcasTest.getOther_Capability()));
        assignment.put(Climb_Inhibit, IntConst.of(tcasTest.getClimb_Inhibit()));
        AssignmentTestCase testCase = new AssignmentTestCase(assignment, IntConst.of(tcasTest.getResult()));
        testCase.setId(Integer.toString(id));
        return testCase;
    }

    private static AssignmentTestCase getBVTestById(int id, ArrayList<TcasTestCase> data) {
        Map<ProgramVariable, Node> assignment = new HashMap<>();
        TcasTestCase tcasTest = data.get(id);
        assignment.put(BV_Cur_Vertical_Sep, BVConst.ofLong(tcasTest.getCur_Vertical_Sep(), 32));
        assignment.put(BV_High_Confidence, BVConst.ofLong(tcasTest.getHigh_Confidence(), 32));
        assignment.put(BV_Two_of_Three_Reports_Valid, BVConst.ofLong(tcasTest.getTwo_of_Three_Reports_Valid(), 32));
        assignment.put(BV_Own_Tracked_Alt, BVConst.ofLong(tcasTest.getOwn_Tracked_Alt(), 32));
        assignment.put(BV_Own_Tracked_Alt_Rate, BVConst.ofLong(tcasTest.getOwn_Tracked_Alt_Rate(), 32));
        assignment.put(BV_Other_Tracked_Alt, BVConst.ofLong(tcasTest.getOther_Tracked_Alt(), 32));
        assignment.put(BV_Alt_Layer_Value, BVConst.ofLong(tcasTest.getAlt_Layer_Value(), 32));
        assignment.put(BV_Up_Separation, BVConst.ofLong(tcasTest.getUp_Separation(), 32));
        assignment.put(BV_Down_Separation, BVConst.ofLong(tcasTest.getDown_Separation(), 32));
        assignment.put(BV_Other_RAC, BVConst.ofLong(tcasTest.getOther_RAC(), 32));
        assignment.put(BV_Other_Capability, BVConst.ofLong(tcasTest.getOther_Capability(), 32));
        assignment.put(BV_Climb_Inhibit, BVConst.ofLong(tcasTest.getClimb_Inhibit(), 32));
        AssignmentTestCase testCase = new AssignmentTestCase(assignment, BVConst.ofLong(tcasTest.getResult(), 32));
        testCase.setId(Integer.toString(id));
        return testCase;
    }


    private static ArrayList<AssignmentTestCase> getTestSuite(String id, ArrayList<TcasTestCase> data, boolean useBVEncoding) {
        ArrayList<AssignmentTestCase> testSuite = new ArrayList<>();
        Path path = FileSystems.getDefault().getPath("data", "semfix-suite-50-" + id);
        List<String> ids = null;
        try {
            ids = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : ids) {
            if (useBVEncoding) {
                testSuite.add(getBVTestById(Integer.parseInt(s), data));
            } else {
                testSuite.add(getTestById(Integer.parseInt(s), data));
            }
        }
        return testSuite;
    }

    private static ArrayList<TcasTestCase> loadData() {
        Path path = FileSystems.getDefault().getPath("data", "tcas.json");
        String tcasData = "";
        try {
            tcasData = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<TcasTestCase> tcasTestData = new ArrayList<>(JSON.parseArray(tcasData, TcasTestCase.class));
        return tcasTestData;
    }

    @Override
    public List<String> getTestSuiteIds() {
        ArrayList<String> ids = new ArrayList<>();
        ids.add("1-50");
        ids.add("1-25");
        ids.add("1-10");
        return ids;
    }

    @Override
    public List<AssignmentTestCase> getTestSuite(String id, boolean useBVEncoding) {
        ArrayList<TcasTestCase> data = loadData();
        String[] idParts = id.split("-");
        ArrayList<AssignmentTestCase> testSuite = getTestSuite(idParts[0], data, useBVEncoding);
        int size = Integer.parseInt(idParts[1]);
        return testSuite.subList(0, size);
    }

    @Override
    public List<String> getComponentsIds() {
        ArrayList<String> ids = new ArrayList<>();
        ids.add("adhoc");
        return ids;
    }


    @Override
    public Multiset<Node> getComponents(String id, String testId, boolean useBVEncoding) {
        if (useBVEncoding) {
            return tcasBVComponents();
        } else {
            return tcasIntComponents();
        }
    }

}
