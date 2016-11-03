package sg.edu.nus.comp.codisexp;

import com.google.common.collect.Multiset;
import sg.edu.nus.comp.codis.AssignmentTestCase;
import sg.edu.nus.comp.codis.ast.Node;

import java.util.List;


/**
 * Created by Sergey Mechtaev on 16/6/2016.
 */
public interface Subject {

    List<String> getTestSuiteIds();

    List<AssignmentTestCase> getTestSuite(String id, boolean useBVEncoding);

    List<String> getComponentsIds();

    Multiset<Node> getComponents(String id, String testId, boolean useBVEncoding);

}
