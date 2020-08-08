//T11_37_1096_Youssef_Haitham
package fdfa;

import java.util.Hashtable;
import java.util.Stack;

public class fdfa {

	String[] alphabet = { "0", "1" };
	String currentState = "0";
	String[] states;
	String[] goalStates;
	Hashtable<String, String> stateTable;
	Hashtable<String, String> actionTable;
	boolean found = false;

	public fdfa(String description) {
		states = (description.split("#"))[0].split(";");
		goalStates = (description.split("#"))[1].split(",");
		stateTable = new Hashtable<String, String>();
		actionTable = new Hashtable<String, String>();
		String nextStateZero = "";
		String nextStateOne = "";
		for (int i = 0; i < states.length; i++) {
			nextStateZero = (states[i].split(","))[1];
			nextStateOne = (states[i].split(","))[2];
			actionTable.put(i +"", (states[i].split(","))[3]);
			stateTable.put(i + "0", nextStateZero);
			stateTable.put(i + "1", nextStateOne);
		}
	}

	public String run(String binaryString) {
		String output = "";
		Stack stackedStates = new Stack<String>();
		int r = 0;
		int l = 0;
		String currentState = "0";
		while (r < binaryString.length()) {
			stackedStates.push(currentState);
			while (l < binaryString.length()) {
				String stateTableInput = currentState + binaryString.charAt(l);
				currentState = stateTable.get(stateTableInput);
				stackedStates.push(currentState);
				l+=1;
			}
			String removedState = (String)stackedStates.pop();
			String firstPop = removedState;
			if(isGoal(removedState)) {
				stackedStates.clear();
				output += actionTable.get(removedState);
				currentState = "0";
				found = true;
			}
			while(!stackedStates.isEmpty()) {
				if(l==r) {
					return output;
				}
				removedState = (String)stackedStates.pop();
				if(isGoal(removedState)) {
					stackedStates.clear();
					output+= actionTable.get(removedState);
					currentState = "0";
					found = true;
				}
				l-=1;
			}
			if(found == false) {
				output += actionTable.get(firstPop);
				return output;
			}
			found = false;
			r = l;
			
		}


		return output;
	}

	public boolean isGoal(String state) {
		for (int i = 0; i < goalStates.length; i++) {
			if (state.equals(goalStates[i])) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		fdfa x = new fdfa("0,4,1,000;1,1,2,001;2,3,2,010;3,1,4,011;4,4,0,100#2,4");
		System.out.println(x.run("000"));
		System.out.println(x.run("101"));
		System.out.println(x.run("1110"));
		System.out.println(x.run("11000"));
		System.out.println(x.run("0110"));
		
		System.out.println("------------------");
		
		fdfa y = new fdfa("0,1,1,000;1,4,2,001;2,2,3,010;3,4,0,011;4,2,4,100#4");
		System.out.println(y.run("0101"));
		System.out.println(y.run("1001"));
		System.out.println(y.run("0011"));
		System.out.println(y.run("11100"));
		System.out.println(y.run("00"));
	}

}