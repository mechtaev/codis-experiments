package sg.edu.nus.comp.codisexp;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apache.commons.lang3.tuple.Pair;
import sg.edu.nus.comp.codis.AssignmentTestCase;
import sg.edu.nus.comp.codis.TestCase;
import sg.edu.nus.comp.codis.ast.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey Mechtaev on 3/11/2016.
 */
public class ICFP implements Subject {
    @Override
    public List<String> getTestSuiteIds() {
        List<String> ids = new ArrayList<>();
        for (int i = 1; i <= 150; i++) {
            for (int j = 10; j <= 1000; j*=10) {
                ids.add("" + i + "_" + j);
            }
        }
        return ids;
    }

    @Override
    public List<AssignmentTestCase> getTestSuite(String id, boolean useBVEncoding) {
        try {
            Pair<List<AssignmentTestCase>, List<Node>> result = SyGuSConverter.convert(new File("data/SyGuS/ICFP/" + id + ".sl"));
            return result.getLeft();
        } catch (IOException | SyGuSConversionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getComponentsIds() {
        List<String> ids = new ArrayList<>();
        ids.add("2");
        ids.add("3");
        ids.add("4");
        return ids;
    }

    @Override
    public Multiset<Node> getComponents(String id, String testId, boolean useBVEncoding) {
        try {
            Pair<List<AssignmentTestCase>, List<Node>> result = SyGuSConverter.convert(new File("data/SyGuS/ICFP/" + testId + ".sl"));
            Multiset<Node> components = HashMultiset.create();
            int i = Integer.parseInt(id);
            for (Node node : result.getRight()) {
                components.add(node, i);
            }
            return components;
        } catch (IOException | SyGuSConversionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
