import java.util.*;

class Function {
	String name; 		// ID
	Parameter param;	// <parameters>
	StmtSeq ss;			// <stmt-seq>
	
	void parse() {
		// <function> ::= procedure ID ( <parameters> ) is <stmt-seq> end
		// There is no "|(or)" so we don't need to write if sentence.
		Parser.expectedToken(Core.PROCEDURE);
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
		Parser.expectedToken(Core.IS);
		Parser.scanner.nextToken();
		ss = new StmtSeq();
		ss.parse();
		Parser.expectedToken(Core.END);
		Parser.scanner.nextToken();
	}
	
	// <function> ::= procedure ID ( <parameters> ) is <stmt-seq> end
	void print(int indent) {							
		System.out.print("procedure " + name + " (");	// procedure ID (
		param.print();									// parameters>
		System.out.println(") is ");					// ) is
		ss.print(indent+1);								// <stmt-seq>
		System.out.println("end");						// end
	}
	
	// Prof. Carpenter provided us excute function in lecture, so I used that.
	void execute() {
		// Check if duplicate function name
		if (Memory.funcMap.containsKey(name)) {
			System.out.println("ERROR: " + name + " is a duplicate function.");
			System.exit(1);
		}
		// Store function in funcMap so we can use it is Call.execute
		Memory.funcMap.put(name, this);
		// Check for duplicated in the formal parameters
		List<String> fp = param.execute();
		HashSet<String> fpSet = new HashSet<String>(fp);
		if (fp.size() != fpSet.size()) {
			System.out.println("ERROR: Function " + name + " has duplicate formal paramters.");
			System.exit(1);
		}
	}
}
