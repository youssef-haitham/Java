package dfa;

import java.util.Hashtable;

public class DFA {

	String[] alphabet = { "0", "1" };
	String currentState = "0";
	String[] states;
	String[] goalStates;
	Hashtable<String, String> stateTable;

	// DFA takes a comma separated string as an input and makes a DFA out of it
	public DFA(String description) {
		states = (description.split("#"))[0].split(";");
		goalStates = (description.split("#"))[1].split(",");
		stateTable = new Hashtable<String, String>();
		String nextStateZero = "";
		String nextStateOne = "";
		for (int i = 0; i < states.length; i++) {
			nextStateZero = (states[i].split(","))[1];
			nextStateOne = (states[i].split(","))[2];
			stateTable.put(i + "0", nextStateZero);
			stateTable.put(i + "1", nextStateOne);
		}
	}

	// run method tests inputs for the DFA made by the DFA method
	public boolean run(String binaryString) {
		for (int i = 0; i < binaryString.length(); i++) {
			String stateTableInput = currentState + binaryString.charAt(i);
			currentState = stateTable.get(stateTableInput);
		}
		for (int i = 0; i < goalStates.length; i++) {
			if (currentState.equals(goalStates[i])) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		DFA x = new DFA("0,0,1,00;1,2,1,01;2,0,3,10;3,3,3,11#0,1,2");
		System.out.println(x.run("100"));
	}

}
