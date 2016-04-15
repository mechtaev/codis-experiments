package sg.edu.nus.comp.codisexp;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.tuple.Triple;
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
 * Created by Sergey Mechtaev on 7/4/2016.
 */
public class Main {



    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);

        Tcas.synthesize();

    }
}
