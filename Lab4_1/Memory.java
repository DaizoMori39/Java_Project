// I am using the answer code (proj3) provided from Prof. Carpenter, 
// so I added and rewrote the functions.

import java.util.*;

class Memory {
	//scanner is stored here as a static field so it is avaiable to the execute method for factor
	public static Scanner data;
	
	// Class and data structures to represent variables
	static class Value {
		Core type;
		int integerVal;
		int[] arrayVal;
		Value(Core t) {
			this.type = t;
		}
	}
	
	public static HashMap<String, Value> global;
	public static Stack<Stack<HashMap<String, Value>>> local;
	public static HashMap<String, Function> funcMap; //I need map of function for project4
	
	// Helper methods to manage memory
	
	// This inializes the global memory structure
	// Called before executing the DeclSeq
	public static void initializeGlobal() {
		global = new HashMap<String, Value>();
		funcMap = new HashMap<String, Function>(); //initialize the map of function
	}
	
	// Initializes the local data structure
	// Called before executing the main StmtSeq
	public static void initializeLocal() {
		// I rewrite it to a stack of frames (stack of stack of maps)
		local = new Stack<Stack<HashMap<String, Value>>>();
		local.push(new Stack<HashMap<String, Value>>());
		// top of the stack using peek 
		local.peek().push(new HashMap<String, Value>());
	}
	
	// Pushes a "scope" for if/loop stmts
	public static void pushScope() {
		// added peek() 
		local.peek().push(new HashMap<String, Value>());
	}
	
	// Pops a "scope"
	public static void popScope() {
		// added peek() 
		local.peek().pop();
	}
	
	// Handles decl integer
	public static void declareInteger(String id) {
		Value v = new Value(Core.INTEGER);
		if (local != null) {
			//added peek()
			local.peek().peek().put(id, v);
		} else {
			global.put(id, v);
		}
	}
	
	// Handles decl record
	public static void declareObject(String id) {
		Value v = new Value(Core.OBJECT);
		if (local != null) {
			//added peek()
			local.peek().peek().put(id, v);
		} else {
			global.put(id, v);
		}
	}
	
	// Retrives a value from memory (integer or record at index 0
	public static int load(String id) {
		int value;
		Value v = getLocalOrGlobal(id);
		if (v.type == Core.INTEGER) {
			value = v.integerVal;
		} else {
			value = v.arrayVal[0];
		}
		return value;
	}
	
	// Retrieves a record value from the index
	public static int load(String id, int index) {
		Value v = getLocalOrGlobal(id);
		return v.arrayVal[index];
	}
	
	// Stores a value (integer or record at index 0
	public static void store(String id, int value) {
		Value v = getLocalOrGlobal(id);
		if (v.type == Core.INTEGER) {
			v.integerVal = value;
		} else {
			v.arrayVal[0] = value;
		}
	}
	
	// Stores a value at record index
	public static void store(String id, int index, int value) {
		Value v = getLocalOrGlobal(id);
		v.arrayVal[index] = value;
	}
	
	// Handles "new record" assignment
	public static void allocate(String id, int index) {
		Value v = getLocalOrGlobal(id);
		v.arrayVal = new int[index];
	}
	
	// Handles "id := record id" assignment
	public static void alias(String lhs, String rhs) {
		Value v1 = getLocalOrGlobal(lhs);
		Value v2 = getLocalOrGlobal(rhs);
		v1.arrayVal = v2.arrayVal;
	}
	
	// Looks up value of the variables, searches local then global
	//added peek()s
	private static Value getLocalOrGlobal(String id) {
		Value result;
		if (local.peek().size() > 0) {
			if (local.peek().peek().containsKey(id)) {
				result = local.peek().peek().get(id);
			} else {
				HashMap<String, Value> temp = local.peek().pop();
				result = getLocalOrGlobal(id);
				local.peek().push(temp);
			}
		} else {
			result = global.get(id);
		}
		return result;
	}
	
	public static void pushFrameAndExecute(String name, Parameter args) {
		Function f = funcMap.get(name);
		ArrayList<String> formals = f.param.execute();
		ArrayList<String> arguments = args.execute();
		Stack<HashMap<String, Value>> frame = new Stack<HashMap<String, Value>>();
		frame.push(new HashMap<String, Value>());
		for (int i=0; i<arguments.size(); i++) {
			Value v1 = getLocalOrGlobal(arguments.get(i));
			Value v2 = new Value(Core.OBJECT);
			v2.arrayVal = v1.arrayVal;
			frame.peek().put(formals.get(i), v2);
		}
		local.push(frame);
		f.ss.execute();
	}
	//pop frame from local
	public static void popFrame() {
		local.pop();
	}
}