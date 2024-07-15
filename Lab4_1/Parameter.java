import java.util.*;

class Parameter {
	String name;		// ID
	Parameter param;	// <parameters> 
	
	// <parameters> ::= ID | ID , <parameters> 
	void parse() {
		Parser.expectedToken(Core.ID);
		name = Parser.scanner.getId();
		Parser.scanner.nextToken();
		if (Parser.scanner.currentToken() == Core.COMMA) {
			Parser.scanner.nextToken();
			param = new Parameter();
			param.parse();
		}
	}
	
	void print() {
		System.out.print(name);			// ID
		if (param != null) {
			System.out.print(", ");		// ,
			param.print();				// <parameters>
		}
	}
	
	ArrayList<String> execute() {
		ArrayList<String> value;
		//check if param is null or not
		if (param == null) {
			// there is no more param, so I create the new list
			value = new ArrayList<String>();
		// if not null
		} else {
			// going reccursion
			value = param.execute();
		}
		// add the iD to value
		value.add(name);
		// return with the value
		return value;
	}
}
