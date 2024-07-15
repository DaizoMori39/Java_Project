// <stmt> ::= <assign> | <if> | <loop> | <out> | <decl> | <call> 


class StmtSeq {
	Stmt stmt;
	StmtSeq ss;
	
	void parse() {
		// added if-statement for <call> 
		// check if currentToken is BEGIN 
		// because <call> ::= begin ID ( <parameters> ) ;
		if(Parser.scanner.currentToken() == Core.BEGIN){
			stmt = new Call();
		}
		else if (Parser.scanner.currentToken() == Core.ID) {
			stmt = new Assign();
		} else if (Parser.scanner.currentToken() == Core.OUT) {
			stmt = new Output();
		}  else if (Parser.scanner.currentToken() == Core.IF) {
			stmt = new If();
		} else if (Parser.scanner.currentToken() == Core.WHILE) {
			stmt = new Loop();
		}  else if (Parser.scanner.currentToken() == Core.INTEGER || Parser.scanner.currentToken() == Core.OBJECT) {
			stmt = new Decl();
		} else {
			System.out.println("ERROR: Bad start to statement: " + Parser.scanner.currentToken());
			System.exit(0);
		}
		stmt.parse();
		if (Parser.scanner.currentToken() != Core.END && Parser.scanner.currentToken() != Core.ELSE) {
			ss = new StmtSeq();
			ss.parse();
		}
	}
			
	void print(int indent) {
		stmt.print(indent);
		if (ss != null) {
			ss.print(indent);
		}
	}
	
	void execute() {
		stmt.execute();
		if (ss != null) {
			ss.execute();
		}
	}
}