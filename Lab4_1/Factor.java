class Factor {
	Id id;
	Expr index;
	int constant;
	Expr expr;
	
	void parse() {
		if (Parser.scanner.currentToken() == Core.ID) {
			id = new Id();
			id.parse();
			if (Parser.scanner.currentToken() == Core.LBRACE) {
				Parser.scanner.nextToken();
				index = new Expr();
				index.parse();
				Parser.expectedToken(Core.RBRACE);
				Parser.scanner.nextToken();
				
			}
		} else if (Parser.scanner.currentToken() == Core.CONST) {
			constant = Parser.scanner.getConst();
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.LPAREN) {
			Parser.scanner.nextToken();
			expr = new Expr();
			expr.parse();
			Parser.expectedToken(Core.RPAREN);
			Parser.scanner.nextToken();
		} else if (Parser.scanner.currentToken() == Core.IN) {
			// Setting constant to -1 to identify this case
			constant = -1;
			Parser.expectedToken(Core.IN);
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.LPAREN);
			Parser.scanner.nextToken();
			Parser.expectedToken(Core.RPAREN);
			Parser.scanner.nextToken();
		} else {
			System.out.println("ERROR: Expected ID, CONST, LPAREN, or IN, recieved " + Parser.scanner.currentToken());
			System.exit(0);
		}
	}
	
	void print() {
		if (id != null) {
			id.print();
			if (index != null) {
				System.out.print("[");
				index.print();
				System.out.print("]");
			}
		} else if (expr != null) {
			System.out.print("(");
			expr.print();
			System.out.print(")");
		} else if (constant == -1) {
			System.out.print("in()");
		} else {
			System.out.print(constant);
		}
	}
	
	int execute() {
		int value;
		if (id != null && index != null) {
			value = Memory.load(id.getId(), index.execute());
		} else if (id != null) {
			value = Memory.load(id.getId());
		} else if (expr != null) {
			value = expr.execute();
		} else if (constant == -1) {
			if (Memory.data.currentToken() == Core.CONST) {
				value = Memory.data.getConst();
				Memory.data.nextToken();
			} else {
				value = -1;
				System.out.println("ERROR: Data file out of values!");
				System.exit(1);
			}
		} else {
			value = constant;
		}
		return value;
	}
}