class Call implements Stmt {
	String name;		// ID
	Parameter param;	// <parameters>
	
	//<call> ::= begin ID ( <parameters> ) ;
	// there is no "|(or)", so we just need to parse 1 type of sentence
	public void parse() {
		Parser.expectedToken(Core.BEGIN);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.ID);
		name = Parser.scanner.getId();
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.LPAREN);
		Parser.scanner.nextToken();
		param = new Parameter();
		param.parse();
		Parser.expectedToken(Core.RPAREN);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	//we need to print "begin ID ( <parameters> ) ;"
	public void print(int indent) {
		System.out.print("begin " + name + "(");	// begin ID (
		param.print();								// <parameters> 
		System.out.print(");");						// );
	}
	
	public void execute() {
		// Check if there is the function (I put the function into funcMap in Function.java,
		// so there should be the function name)
		if (Memory.funcMap.containsKey(name)) {
			Memory.pushFrameAndExecute(name, param);
			Memory.popFrame();
			
		}else{
			System.out.println("ERROR: No declaration of the function." + name);
			System.exit(1);
		};
		
	}
}