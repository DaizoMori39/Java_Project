class Assign implements Stmt {
	// type is
	// 0 if id = <expr> assignment
	// 1 if id[<expr>] = <expr> assignment
	// 2 if "new" assignment
	// 3 if "id : id" assignment
	int type;
	// assignTo is the id on the LHS of assignment
	Id assignTo;
	// assignFrom is the id on RHS of "id : id" assignment
	Id assignFrom;
	// Two possible expressions in an assignment
	Expr index;
	Expr expr;
	
	public void parse() {
		type=0;
		assignTo = new Id();
		assignTo.parse();
		if (Parser.scanner.currentToken() == Core.LBRACE) {
			type = 1;
			Parser.scanner.nextToken();
			index = new Expr();
			index.parse();
			Parser.expectedToken(Core.RBRACE);
			Parser.scanner.nextToken();
		}
		if (Parser.scanner.currentToken() == Core.ASSIGN) {
			Parser.scanner.nextToken();
			if (Parser.scanner.currentToken() == Core.NEW) {
				// new object assign
				type = 2;
				Parser.scanner.nextToken();
				Parser.expectedToken(Core.OBJECT);
				Parser.scanner.nextToken();
				Parser.expectedToken(Core.LPAREN);
				Parser.scanner.nextToken();
				index = new Expr();
				index.parse();
				Parser.expectedToken(Core.RPAREN);
				Parser.scanner.nextToken();
			} else {
				// Expression assign, type will be either 0 or 1
				expr = new Expr();
				expr.parse();
			}
		} else {
			// Aliasing assign
			Parser.expectedToken(Core.COLON);
			Parser.scanner.nextToken();
			type = 3;
			assignFrom = new Id();
			assignFrom.parse();
		}
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		assignTo.print();
		if (type == 1) {
			System.out.print("[");
			index.print();
			System.out.print("]");
		}
		if (type == 0 || type == 1) {
			System.out.print("=");
			expr.print();
		} else if (type == 2) {
			System.out.print("=");
			System.out.print("new object(");
			index.print();
			System.out.print(")");
		} else if (type == 3) {
			System.out.print(":");
			assignFrom.print();
		}
		System.out.println(";");
	}
	
	public void execute() {
		if (type == 0) {
			Memory.store(assignTo.getId(), expr.execute());
		}
		else if (type == 1) {
			Memory.store(assignTo.getId(), index.execute(), expr.execute());
		} else if (type == 2) {
			Memory.allocate(assignTo.getId(), index.execute());
		} else if (type == 3) {
			Memory.alias(assignTo.getId(), assignFrom.getId());
		}
	}
}