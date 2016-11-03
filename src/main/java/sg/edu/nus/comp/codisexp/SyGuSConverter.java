package sg.edu.nus.comp.codisexp;

import com.google.common.collect.Multiset;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import sg.edu.nus.comp.codis.AssignmentTestCase;
import sg.edu.nus.comp.codis.Components;
import sg.edu.nus.comp.codis.TestCase;
import sg.edu.nus.comp.codis.ast.BVType;
import sg.edu.nus.comp.codis.ast.Hole;
import sg.edu.nus.comp.codis.ast.Node;
import sg.edu.nus.comp.codis.ast.ProgramVariable;
import sg.edu.nus.comp.codis.ast.theory.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Convert SyGuS into CBS
 */
public class SyGuSConverter {

    public static Pair<List<AssignmentTestCase>, List<Node>> convert(File file) throws IOException, SyGuSConversionException {
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(file));
        SyGuSLexer lexer = new SyGuSLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SyGuSParser parser = new SyGuSParser(tokens);
        ParseTree tree = parser.sygus();
        ParseTreeWalker walker = new ParseTreeWalker();
        SyGuSTranslationListener listener = new SyGuSTranslationListener();
        walker.walk(listener, tree);
        return listener.get();
    }

    /** Currently, parse only three things:
     * 1. define-fun
     * 2. synth-fun assuming that it completely recursive
     * 3. constraint assuming that it is in the form (= (SYMBOL ...) ...)
     * everything else ignore
     */
    private static class SyGuSTranslationListener implements SyGuSListener {

        private boolean ignore = false;

        private Map<String, Node> definedFunctions;
        private List<Pair<BVConst, BVConst>> constraints;
        private List<Node> components;

        private Stack<Node> lastTermStack = new Stack<>();

        SyGuSTranslationListener() {
            definedFunctions = new HashMap<>();
            constraints = new ArrayList<>();
            components = new ArrayList<>();
        }

        Pair<List<AssignmentTestCase>, List<Node>> get() throws SyGuSConversionException {
            List<AssignmentTestCase> testSuite = new ArrayList<>();
            for (Pair<BVConst, BVConst> constraint : constraints) {
                Map<ProgramVariable, Node> assignment = new HashMap<>();
                assignment.put(new ProgramVariable("x", new BVType(64)), constraint.getLeft());
                testSuite.add(new AssignmentTestCase(assignment, constraint.getRight()));
            }
            return new ImmutablePair<>(testSuite, this.components);
        }

        @Override
        public void enterSetLogicCmd(@NotNull SyGuSParser.SetLogicCmdContext ctx) {
            if (ignore) return;
            ignore = true;
        }

        @Override
        public void exitSetLogicCmd(@NotNull SyGuSParser.SetLogicCmdContext ctx) {
            ignore = false;
        }

        @Override
        public void enterFunDeclCmd(@NotNull SyGuSParser.FunDeclCmdContext ctx) {
            if (ignore) return;
            ignore = true;
        }

        @Override
        public void exitFunDeclCmd(@NotNull SyGuSParser.FunDeclCmdContext ctx) {
            ignore = false;
        }

        @Override
        public void enterLetTerm(@NotNull SyGuSParser.LetTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitLetTerm(@NotNull SyGuSParser.LetTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterCheckSynthCmd(@NotNull SyGuSParser.CheckSynthCmdContext ctx) {
            if (ignore) return;
            ignore = true;
        }

        @Override
        public void exitCheckSynthCmd(@NotNull SyGuSParser.CheckSynthCmdContext ctx) {
            ignore = false;
        }

        @Override
        public void enterLetGTerm(@NotNull SyGuSParser.LetGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitLetGTerm(@NotNull SyGuSParser.LetGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterLiteral(@NotNull SyGuSParser.LiteralContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitLiteral(@NotNull SyGuSParser.LiteralContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterSetOptsCmd(@NotNull SyGuSParser.SetOptsCmdContext ctx) {
            if (ignore) return;
            ignore = true;
        }

        @Override
        public void exitSetOptsCmd(@NotNull SyGuSParser.SetOptsCmdContext ctx) {
            ignore = false;
        }

        @Override
        public void enterSygus(@NotNull SyGuSParser.SygusContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitSygus(@NotNull SyGuSParser.SygusContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterSynthFunCmd(@NotNull SyGuSParser.SynthFunCmdContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitSynthFunCmd(@NotNull SyGuSParser.SynthFunCmdContext ctx) {
            if (ignore) return;

        }

        @Override
        public void enterFunDefCmd(@NotNull SyGuSParser.FunDefCmdContext ctx) {
            if (ignore) return;
            this.lastTermStack = new Stack<>();
        }

        @Override
        public void exitFunDefCmd(@NotNull SyGuSParser.FunDefCmdContext ctx) {
            if (ignore) return;
            this.definedFunctions.put(ctx.SYMBOL().get(0).getText(), this.lastTermStack.peek());
        }

        @Override
        public void enterConstraintCmd(@NotNull SyGuSParser.ConstraintCmdContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitConstraintCmd(@NotNull SyGuSParser.ConstraintCmdContext ctx) {
            if (ignore) return;
            // as a weird side effect, we have (= CONST CONST) on the stack
            Equal constraint = (Equal) this.lastTermStack.pop();
            BVConst input = ((BVConst) constraint.getLeft());
            BVConst output = ((BVConst) constraint.getRight());
            this.constraints.add(new ImmutablePair<>(input, output));
        }

        @Override
        public void enterSortDefCmd(@NotNull SyGuSParser.SortDefCmdContext ctx) {
            if (ignore) return;
            ignore = true;
        }

        @Override
        public void exitSortDefCmd(@NotNull SyGuSParser.SortDefCmdContext ctx) {
            ignore = false;
        }


        @Override
        public void enterCmd(@NotNull SyGuSParser.CmdContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitCmd(@NotNull SyGuSParser.CmdContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterNtDef(@NotNull SyGuSParser.NtDefContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitNtDef(@NotNull SyGuSParser.NtDefContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterVarDeclCmd(@NotNull SyGuSParser.VarDeclCmdContext ctx) {
            if (ignore) return;
            ignore = true;
        }

        @Override
        public void exitVarDeclCmd(@NotNull SyGuSParser.VarDeclCmdContext ctx) {
            ignore = false;
        }

        @Override
        public void visitTerminal(@NotNull TerminalNode terminalNode) {
            if (ignore) return;
        }

        @Override
        public void visitErrorNode(@NotNull ErrorNode errorNode) {
            if (ignore) return;
        }

        @Override
        public void enterEveryRule(@NotNull ParserRuleContext parserRuleContext) {
            if (ignore) return;
        }

        @Override
        public void exitEveryRule(@NotNull ParserRuleContext parserRuleContext) {
            if (ignore) return;
        }


        /* Sorts: ------------------------------------------------------------*/
        @Override
        public void enterBoolSort(@NotNull SyGuSParser.BoolSortContext ctx) {if (ignore) return;}
        @Override
        public void exitBoolSort(@NotNull SyGuSParser.BoolSortContext ctx) {if (ignore) return;}
        @Override
        public void enterNamedSort(@NotNull SyGuSParser.NamedSortContext ctx) {if (ignore) return;}
        @Override
        public void exitNamedSort(@NotNull SyGuSParser.NamedSortContext ctx) {if (ignore) return;}
        @Override
        public void enterRealSort(@NotNull SyGuSParser.RealSortContext ctx) {if (ignore) return;}
        @Override
        public void exitRealSort(@NotNull SyGuSParser.RealSortContext ctx) {if (ignore) return;}
        @Override
        public void enterIntSort(@NotNull SyGuSParser.IntSortContext ctx) {if (ignore) return;}
        @Override
        public void exitIntSort(@NotNull SyGuSParser.IntSortContext ctx) {if (ignore) return;}
        @Override
        public void enterBitVecSort(@NotNull SyGuSParser.BitVecSortContext ctx) {if (ignore) return;}
        @Override
        public void exitBitVecSort(@NotNull SyGuSParser.BitVecSortContext ctx) {if (ignore) return;}
        @Override
        public void enterEnumSort(@NotNull SyGuSParser.EnumSortContext ctx) {if (ignore) return;}
        @Override
        public void exitEnumSort(@NotNull SyGuSParser.EnumSortContext ctx) {if (ignore) return;}
        @Override
        public void enterArraySort(@NotNull SyGuSParser.ArraySortContext ctx) {if (ignore) return;}
        @Override
        public void exitArraySort(@NotNull SyGuSParser.ArraySortContext ctx) {if (ignore) return;}
        /* End sorts: ------------------------------------------------------------*/


        /* Terms: ------------------------------------------------------------*/

        @Override
        public void enterApplicationTerm(@NotNull SyGuSParser.ApplicationTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitApplicationTerm(@NotNull SyGuSParser.ApplicationTermContext ctx) {
            if (ignore) return;
            switch (ctx.SYMBOL().getText()) {
                case "bvlshr":
                    Node right1 = this.lastTermStack.pop();
                    Node left1 = this.lastTermStack.pop();
                    this.lastTermStack.push(new BVUnsignedShiftRight(left1, right1));
                    break;
                case "bvshl":
                    Node right2 = this.lastTermStack.pop();
                    Node left2 = this.lastTermStack.pop();
                    this.lastTermStack.push(new BVShiftLeft(left2, right2));
                    break;
                case "ite":
                    Node elseBranch = this.lastTermStack.pop();
                    Node thenBranch = this.lastTermStack.pop();
                    Node cond = this.lastTermStack.pop();
                    this.lastTermStack.push(new ITE(cond, thenBranch, elseBranch));
                    break;
                case "=":
                    Node right3 = this.lastTermStack.pop();
                    Node left3 = this.lastTermStack.pop();
                    this.lastTermStack.push(new Equal(left3, right3));
                    break;
            }
        }

        @Override
        public void enterLetBindingTerm(@NotNull SyGuSParser.LetBindingTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitLetBindingTerm(@NotNull SyGuSParser.LetBindingTermContext ctx) {
            if (ignore) return;
            throw new RuntimeException("unsupported let binding");
        }

        @Override
        public void enterNamedTerm(@NotNull SyGuSParser.NamedTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitNamedTerm(@NotNull SyGuSParser.NamedTermContext ctx) {
            if (ignore) return;
            //assume they all are BitVec 64
            this.lastTermStack.push(new Hole(ctx.getText(), new BVType(64), Node.class));
        }

        @Override
        public void enterLiratalTerm(@NotNull SyGuSParser.LiratalTermContext ctx) {
            if (ignore) return;
            //assume they all are BitVec 64 literals
            String text = ctx.getText();
            assert text.charAt(0) == '#' && text.charAt(0) == 'x';
            this.lastTermStack.push(BVConst.ofHEXString(text.substring(2), 64));
        }

        @Override
        public void exitLiratalTerm(@NotNull SyGuSParser.LiratalTermContext ctx) {
            if (ignore) return;
        }

        /* End terms ------------------------------------------------------------*/


        /* GTerms: ------------------------------------------------------------*/

        @Override
        public void enterVariableGTerm(@NotNull SyGuSParser.VariableGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitVariableGTerm(@NotNull SyGuSParser.VariableGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterLocalVariableGTerm(@NotNull SyGuSParser.LocalVariableGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitLocalVariableGTerm(@NotNull SyGuSParser.LocalVariableGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterLiteralGTerm(@NotNull SyGuSParser.LiteralGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitLiteralGTerm(@NotNull SyGuSParser.LiteralGTermContext ctx) {
            if (ignore) return;
            //assume they all are BitVec 64 literals
            String text = ctx.getText();
            assert text.charAt(0) == '#' && text.charAt(0) == 'x';
            this.lastTermStack.push(BVConst.ofHEXString(text.substring(2), 64));
        }

        @Override
        public void enterApplicationGTerm(@NotNull SyGuSParser.ApplicationGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitApplicationGTerm(@NotNull SyGuSParser.ApplicationGTermContext ctx) {
            if (ignore) return;
            if (this.definedFunctions.containsKey(ctx.SYMBOL().getText())) {
                this.components.add(this.definedFunctions.get(ctx.SYMBOL().getText()));
            } else {
                switch (ctx.SYMBOL().getText()) {
                    case "bvnot":
                        this.components.add(Components.ofSize(64).BVNOT);
                        break;
                    case "bvand":
                        this.components.add(Components.ofSize(64).BVAND);
                        break;
                    case "bvor":
                        this.components.add(Components.ofSize(64).BVOR);
                        break;
                    case "bvxor":
                        this.components.add(Components.ofSize(64).BVXOR);
                        break;
                    case "bvneg":
                        this.components.add(Components.ofSize(64).BVNEG);
                        break;
                    case "bvadd":
                        this.components.add(Components.ofSize(64).BVADD);
                        break;
                    case "bvmul":
                        this.components.add(Components.ofSize(64).BVMUL);
                        break;
                    case "bvudiv":
                        this.components.add(Components.ofSize(64).BVUDIV);
                        break;
                    case "bvurem":
                        this.components.add(Components.ofSize(64).BVUREM);
                        break;
                }
            }
        }

        @Override
        public void enterConstantGTerm(@NotNull SyGuSParser.ConstantGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitConstantGTerm(@NotNull SyGuSParser.ConstantGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterLetBingingGTerm(@NotNull SyGuSParser.LetBingingGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitLetBingingGTerm(@NotNull SyGuSParser.LetBingingGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void enterNamedGTerm(@NotNull SyGuSParser.NamedGTermContext ctx) {
            if (ignore) return;

        }

        @Override
        public void exitNamedGTerm(@NotNull SyGuSParser.NamedGTermContext ctx) {
            if (ignore) return;
            //this is a bad hack
            if (ctx.getText().equals("x")) {
                this.components.add(new ProgramVariable(ctx.getText(), new BVType(64)));
            }
        }

        @Override
        public void enterInputVariableGTerm(@NotNull SyGuSParser.InputVariableGTermContext ctx) {
            if (ignore) return;
        }

        @Override
        public void exitInputVariableGTerm(@NotNull SyGuSParser.InputVariableGTermContext ctx) {
            if (ignore) return;
        }

        /* End GTerms ------------------------------------------------------------*/

    }
}
