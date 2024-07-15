class DeclSeq {
	Decl decl;		// <decl>
	DeclSeq ds;		// <decl-seq>
	Function fc;	// <function>
	
	void parse() {
		// <decl-seq> ::= <decl > | <decl><decl-seq> | <function> | <function><decl-seq>
		if(Parser.scanner.currentToken() == Core.PROCEDURE){
			fc = new Function();
			fc.parse();
		}else{
			decl = new Decl();
			decl.parse();
		}
		if (Parser.scanner.currentToken() != Core.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
	}
	
	void print(int indent) {
		if(fc != null){
			fc.print(indent);
		}else{
			decl.print(indent);
		}
		if (ds != null) {
			ds.print(indent);
		}
	}
	
	void execute() {
		if(fc != null){
			fc.execute();
		}else{
			decl.execute();
		}
		if (ds != null) {
			ds.execute();
		}
	}
}