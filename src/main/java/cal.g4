grammar cal;

prog: decl_list? func_list? main;

// Declarations Section
decl_list: decl+;

decl:  var_decl | const_decl;
var_decl:   Variable IDENTIFIER COLON var_type SEMICOLON;
const_decl: Constant IDENTIFIER COLON Int ASSIGN NUM SEMICOLON
          | Constant IDENTIFIER COLON Bool ASSIGN BOOL SEMICOLON;

var_type: Int | Bool;

// Functions Section
func_list: func_decl+;
func_decl: return_type IDENTIFIER LOB params? ROB Is decl_list? Begin stm_blk? End;

params:      IDENTIFIER COLON var_type ',' params
           | IDENTIFIER COLON var_type;

return_type: Int | Bool | Void;

// Main Section
main: Main Begin decl_list? stm_blk? End;

// Statements
stm_blk: stm+;
stm:  assignment_stm
    | func_call_stm
    | if_stm
    | while_stm
    | skip_stm
    | return_stm;

assignment_stm: IDENTIFIER ASSIGN exp SEMICOLON
              | IDENTIFIER ASSIGN cond SEMICOLON;
func_call_stm:  func_call SEMICOLON;
if_stm:         If cond Begin stm_blk? End Else Begin stm_blk? End
      |         If cond Begin stm_blk? End;
while_stm:      While cond Begin stm_blk End;
skip_stm:       Skip SEMICOLON;
return_stm:     Return LOB exp ROB SEMICOLON
              | Return LOB cond ROB SEMICOLON;

exp:            LOB exp ROB
              | frag arith_op frag
              | func_call
              | frag;
cond:           LOB cond ROB
              | exp comp_op exp
              | cond ('&' | '|') cond
              | NOT cond
              | exp;

func_call: IDENTIFIER LOB args? ROB;
arith_op: PLUS | MINUS;
comp_op: EQ | NEQ | LT | LTE | GT | GTE;
args:    IDENTIFIER',' args
       | IDENTIFIER;
frag: NUM | BOOL | IDENTIFIER | '-'IDENTIFIER;
BOOL: True | False;

// Reserved keywords
Variable: V A R I A B L E;
Constant: C O N S T A N T;
Return: R E T U R N;
Int: I N T;
Bool: B O O L;
Void: V O I D;
Main: M A I N;
If: I F;
Else: E L S E;
True: T R U E;
False: F A L S E;
While: W H I L E;
Begin: B E G I N;
End: E N D;
Is: I S;
Skip: S K I P;

// Letter frags
fragment A: 'a' | 'A';
fragment B: 'b' | 'B';
fragment C: 'c' | 'C';
fragment D: 'd' | 'D';
fragment E: 'e' | 'E';
fragment F: 'f' | 'F';
fragment G: 'g' | 'G';
fragment H: 'h' | 'H';
fragment I: 'i' | 'I';
fragment K: 'k' | 'K';
fragment L: 'l' | 'L';
fragment M: 'm' | 'M';
fragment N: 'n' | 'N';
fragment O: 'o' | 'O';
fragment P: 'p' | 'P';
fragment R: 'r' | 'R';
fragment S: 's' | 'S';
fragment T: 't' | 'T';
fragment U: 'u' | 'U';
fragment V: 'v' | 'V';
fragment W: 'w' | 'W';

// Inputs
NUM:           '-'?[1-9]NUM*
             | [0-9];
IDENTIFIER:    [a-zA-Z]([a-zA-Z] | [0-9] | '_')*;

// Special Chars
COMMA:      ',';
SEMICOLON:  ';';
COLON:      ':';
ASSIGN:     ':=';
LOB:        '(';
ROB:        ')';
PLUS:       '+';
MINUS:      '-';
NOT:        '∼';
OR:         '|';
AND:        '&';
EQ:         '=';
NEQ:        '!=';
LT:         '<';
LTE:        '<=';
GT:         '>';
GTE:        '>=';

WS:         [ \t\n\r]+ -> skip;

MLTCOMMENT: '/*' ('/'*? MLTCOMMENT | ('/'* | '*'*) ~[/*])*? '*'*? '*/' -> skip;
LNCOMMENT:      '//'~[\n\r]* -> skip;