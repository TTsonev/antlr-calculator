/**
 * @author Trayan Tsonev
 * @id
 */

grammar BigCalcProg;

program 
        : (statement ';')+ EOF
        ;

statement
        : Variable '=' expression                                       # assignStatement       
        | expression                                                    # expressionStatement
        | 'if' '(' expression ')' statement ';' 'else' statement        # ifStatement
        ;

expression  
        : expression op=('*' | '/') expression  # mulDiv
        | expression op=('+' | '-') expression  # addSub
        | '(' expression ')'                    # parenth
        | Number                                # num
        | Variable                              # var
        ;

Variable 
        : Letter Digit*
        ;

Number  
        : Digit* '.' Digit+
        | Digit+
        ;

Letter
        : ('a'..'z' | 'A'..'Z')
        ;

Digit   
        : [0-9]
        ;

WS      : [ \t\r\n\u000C]+ -> skip  
        ;

COMMENT
        :   '/*' .*? '*/' -> skip
        ;

LINE_COMMENT
        : '//' ~[\r\n]* -> skip 
        ;
