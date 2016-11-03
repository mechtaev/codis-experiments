grammar SyGuS;

sygus
  : setLogicCmd cmd+
  | cmd+
  ;

cmd
  : sortDefCmd
  | varDeclCmd
  | funDeclCmd
  | funDefCmd
  | synthFunCmd
  | constraintCmd
  | checkSynthCmd
  | setOptsCmd;

setLogicCmd
  : '(' 'set-logic' SYMBOL ')'
  ;

sortExpr
  : 'Int'                             # IntSort
  | 'Bool'                            # BoolSort
  | 'Real'                            # RealSort
  | '(' 'BitVec' INT_CONST ')'        # BitVecSort
  | '(' 'Enum' '(' SYMBOL+ ')' ')'    # EnumSort
  | '(' 'Array' sortExpr sortExpr ')' # ArraySort
  | SYMBOL                            # NamedSort
  ;

sortDefCmd
  : '(' 'define-sort' SYMBOL sortExpr ')'
  ;

varDeclCmd
  : '(' 'declare-var' SYMBOL sortExpr ')'
  ;

funDeclCmd
  : '(' 'declare-fun' SYMBOL '(' sortExpr* ')' sortExpr ')'
  ;

term
  : '(' SYMBOL term* ')' # ApplicationTerm
  | literal              # LiratalTerm
  | SYMBOL               # NamedTerm
  | letTerm              # LetBindingTerm
  ;

letTerm
  : '(' 'let' '(' ( '(' SYMBOL sortExpr ')' )+ ')' term ')'
  ;

gTerm
  : '(' SYMBOL gTerm* ')'             # ApplicationGTerm
  | literal                           # LiteralGTerm
  | SYMBOL                            # NamedGTerm
  | letGTerm                          # LetBingingGTerm
  | '(' 'Constant' sortExpr ')'       # ConstantGTerm
  | '(' 'Variable' sortExpr ')'       # VariableGTerm
  | '(' 'InputVariable' sortExpr ')'  # InputVariableGTerm
  | '(' 'LocalVariable' sortExpr ')'  # LocalVariableGTerm
  ;

letGTerm
  : '(' 'let' '(' ( '(' SYMBOL sortExpr gTerm ')' )+ ')' gTerm ')'
  ;

funDefCmd
  : '(' 'define-fun' SYMBOL '(' ( '(' SYMBOL sortExpr ')' )* ')' sortExpr term ')'
  ;

synthFunCmd
  : '(' 'synth-fun' SYMBOL '(' ( '(' SYMBOL sortExpr ')' )* ')' sortExpr '(' ntDef+ ')' ')'
  ;

ntDef
  : '(' SYMBOL sortExpr '(' gTerm+ ')' ')'
  ;

constraintCmd
  : '(' 'constraint' term ')'
  ;

checkSynthCmd
  : '(' 'check-synth' ')'
  ;

setOptsCmd
  : '(' 'set-options' '(' ( '(' SYMBOL QUOTED_LITERAL ')' )+ ')' ')'
  ;


SYMBOL
  : SYMBOL_START (SYMBOL_START | DIGIT)*
  ;

fragment SYMBOL_START
  : ('a'..'z')
  | ('A'..'Z')
  | [_+-*&|!~<>=/%?.$^]
  ;

QUOTED_LITERAL
  : '"' ( ('a'..'z') | ('A'..'Z') | DIGIT | '.' )+ '"'
  ;

literal
  : INT_CONST
  | REAL_CONST
  | BOOL_CONST
  | BV_CONST
  | ENUM_CONST
  ;

INT_CONST
  : (DIGIT)+
  | '-' (DIGIT)+
  ;

REAL_CONST
  : (DIGIT)+ '.' (DIGIT)+
  | '-' (DIGIT)+ '.' (DIGIT)+
  ;

BOOL_CONST
  : 'true'
  | 'false'
  ;

BV_CONST
  : '#b' DIGIT+
  | '#x' ( DIGIT | ('a'..'f') | ('A'..'F') )+
  ;

ENUM_CONST
  : SYMBOL '::' SYMBOL
  ;

DIGIT
  : ('0'..'9')
  ;

//
// Whitespace and comments
//

WHITESPACE
  : [ \t\r\n]+ -> skip
  ; // skip spaces, tabs, newlines

LINE_COMMENT
  : ';' ~[\r\n]* -> skip
  ;

