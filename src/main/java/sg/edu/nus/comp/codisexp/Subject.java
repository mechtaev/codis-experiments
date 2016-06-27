package sg.edu.nus.comp.codisexp;

import com.google.common.collect.Multiset;
import sg.edu.nus.comp.codis.ast.TestCase;
import sg.edu.nus.comp.codis.ast.Node;

import java.util.List;


/**
 * Created by Sergey Mechtaev on 16/6/2016.
 */
public interface Subject {

    List<String> getTestSuiteIds();

    List<TestCase> getTestSuite(String id, boolean useBVEncoding);

    Multiset<Node> getComponents(boolean useBVEncoding);

}
