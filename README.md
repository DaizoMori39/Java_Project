# Java_Project
#実行方法
Lab4_1のファイルを全て同じ場所にダウンロードしてください。/n

Lab4_1のファイルの中にCorrectというファイルがあります。/n
 x.codeが仮想のプログラムコードになります。/n
 x.dataが対応するコードのデータになります。/n
 x.expectedが実行されたコードの期待されている出力です。(xにはすべて同じ数字が入ります。)/n
 
Javaのコードファイルのコンパイルが終わった後、
 Main Correct/x.code Correct/x.data
のように実行してください。

例えば
 Correct/ode Correct/2.data
では



７が出力されます。

以下の文法が、x.codeに書かれているコードの文法になり、Javaファイルはそのコードを実行します。

<procedure> ::= procedure ID is <decl-seq> begin <stmt-seq> end 
 | procedure ID is begin <stmt-seq> end
<decl-seq> ::= <decl > | <decl><decl-seq> | <function> | <function><decl-seq>
<stmt-seq> ::= <stmt> | <stmt><stmt-seq> 
<decl> ::= <decl-integer> | <decl-obj> 
<decl-integer> ::= integer id ; 
<decl-obj> ::= object id ; 
<function> ::= procedure ID ( <parameters> ) is <stmt-seq> end
<parameters> ::= ID | ID , <parameters> 
<stmt> ::= <assign> | <if> | <loop> | <out> | <decl> | <call> 
<call> ::= begin ID ( <parameters> ) ;
<assign> ::= id = <expr> ; | id [ <expr> ] = <expr> ; | id = new object( <expr> ); | id : id ; 
<out> ::= out ( <expr> ) ; 
<if> ::= if <cond> then <stmt-seq> end 
 | if <cond> then <stmt-seq> else <stmt-seq> end
<loop> ::= while <cond> do <stmt-seq> end
<cond> ::= <cmpr> | not <cond> | [ <cond> ] | <cmpr> or <cond> | <cmpr> and <cond> 
<cmpr> ::= <expr> == <expr> | <expr> < <expr> 
<expr> ::= <term> | <term> + <expr> | <term> – <expr> 
<term> ::= <factor> | <factor> * <term> | <factor> / <term> 
<factor> ::= id | id [ <expr> ] | const | ( <expr> ) | in ( ) 
