package sg.edu.nus.comp.codisexp;

import com.alibaba.fastjson.JSON;
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
public class Tcas {

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

    public static Map<Node, Integer> tcasComponents() {
        Map<Node, Integer> componentMultiset = new HashMap<>();
        Parameter p1 = Parameter.mkInt("parameter1");
        Parameter p2 = Parameter.mkInt("parameter2");
        componentMultiset.put(IntConst.of(0), 1);
        componentMultiset.put(IntConst.of(1), 1);
        componentMultiset.put(p1, 1);
        componentMultiset.put(p2, 1);
        componentMultiset.put(Cur_Vertical_Sep, 1);
        componentMultiset.put(High_Confidence, 1);
        componentMultiset.put(Two_of_Three_Reports_Valid, 1);
        componentMultiset.put(Own_Tracked_Alt, 1);
        componentMultiset.put(Own_Tracked_Alt_Rate, 1);
        componentMultiset.put(Other_Tracked_Alt, 1);
        componentMultiset.put(Alt_Layer_Value, 1);
        componentMultiset.put(Up_Separation, 1);
        componentMultiset.put(Down_Separation, 1);
        componentMultiset.put(Other_RAC, 1);
        componentMultiset.put(Other_Capability, 1);
        componentMultiset.put(Climb_Inhibit, 1);
        componentMultiset.put(Components.ADD, 1);
        componentMultiset.put(Components.SUB, 1);
        componentMultiset.put(Components.GT, 1);
        componentMultiset.put(Components.GE, 1);
        componentMultiset.put(Components.AND, 1);
        componentMultiset.put(Components.OR, 1);
        componentMultiset.put(Components.MINUS, 1);
        componentMultiset.put(Components.NOT, 1);
        componentMultiset.put(Components.ITE, 2);
        return componentMultiset;
    }

    public static TestCase getTestById(int id, ArrayList<TcasTestCase> data) {
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
        return new TestCase(assignment, IntConst.of(tcasTest.getResult()));
    }

    public static ArrayList<TestCase> getTestSuite(int id, ArrayList<TcasTestCase> data) {
        ArrayList<TestCase> testSuite = new ArrayList<>();
        Path path = FileSystems.getDefault().getPath("data", "semfix-suite-50-" + id);
        List<String> ids = null;
        try {
            ids = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : ids) {
            testSuite.add(getTestById(Integer.parseInt(s), data));
        }
        return testSuite;
    }

    public static ArrayList<TcasTestCase> loadData() {
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

    public static void synthesize() {

        Synthesis synthesizer = new CEGIS(new CBS(Z3.getInstance()));

        ArrayList<TcasTestCase> data = loadData();

        Map<Node, Integer> componentMultiset = tcasComponents();

        ArrayList<TestCase> testSuite = getTestSuite(1, data);

        Optional<Node> node = synthesizer.synthesize(testSuite, componentMultiset);

        if(node.isPresent()) {
            logger.info("Synthesized patch: " + node.get());
            logger.info("Simplified: " + Simplifier.simplify(node.get()));
        } else {
            logger.info("FAIL");
        }

    }
}
