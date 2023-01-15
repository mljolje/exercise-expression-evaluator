grammar EvalExp;

parse
 : expression EOF
 ;

expression
 : LPAREN expression RPAREN                       #parenExpression
 | NOT expression                                 #notExpression
 | left=expression op=comparator right=expression #comparatorExpression
 | left=expression op=binary right=expression     #binaryExpression
 | bool                                           #boolExpression
 | IDENTIFIER                                     #identifierExpression
 | STRING                                         #stringExpression
 | DECIMAL                                        #decimalExpression
 | INTEGER                                        #integerExpression
 | NULL                                           #nullExpression
 ;

comparator
 : GT | GE | LT | LE | EQ | NEQ
 ;

binary
 : AND | OR | ANDJS | ORJS
 ;

bool
 : TRUE | FALSE
 ;

AND        : 'AND' ;
OR         : 'OR' ;
ANDJS      : '&&' ;
ORJS       : '||' ;
NOT        : '!';
TRUE       : 'true' ;
FALSE      : 'false' ;
GT         : '>' ;
GE         : '>=' ;
LT         : '<' ;
LE         : '<=' ;
EQ         : '==' ;
NEQ        : '!=' ;
LPAREN     : '(' ;
RPAREN     : ')' ;
DECIMAL    : '-'? [0-9]+ '.' [0-9]+ ;
INTEGER    : '-'? [0-9]+ ;
IDENTIFIER : [a-zA-Z_] [a-zA-Z_0-9.]* ;
SPACE      : [ \r\t\u000C\n]+ -> skip;
NULL       : 'null';
STRING     : '"' (~[\r\n"] | '""')* '"' ;


