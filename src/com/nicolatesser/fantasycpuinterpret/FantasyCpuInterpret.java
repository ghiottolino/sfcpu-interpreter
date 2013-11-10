package com.nicolatesser.fantasycpuinterpret;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FantasyCpuInterpret {
	public static void main(String[] args) throws Exception {

		String input = null;

		if (args != null && args.length > 0) {
			input = args[0];
		} else {
			input = "./input.txt";
		}

		FantasyCpuProgramParser fantasyCpuProgramParser = new FantasyCpuProgramParser();
		List<String> program = fantasyCpuProgramParser.parseProgram(input);

		FantasyCpuInterpret interpret = new FantasyCpuInterpret();

		// for (String line : program) {
		// interpret.interpret(line);
		// }

		while (true) {
			String line = program.get(interpret.ip);
			interpret.interpret(line);
		}

		// interpret.printOverview();
		// System.out.println(program.size());

	}

	private int a;
	private int b;
	private int c;
	private int z;
	private int ip;
	Map<Integer, Integer> ram = new HashMap<Integer, Integer>();

	private void interpret(String line) {
		if (line == null)
			return;

		line = line.replaceAll("\\s", " ");
		line = line.replaceAll(",", " ");
		line = line.replaceAll("\\s\\s", " ");

		String[] commandComponents = line.split(" ");
		if (commandComponents != null && commandComponents.length > 0) {
			String command = commandComponents[0];
			String[] params = Arrays.copyOfRange(commandComponents, 1,
					commandComponents.length);
			interpretCommand(command, Arrays.asList(params));

		}
	}

	private void printOverview() {
		System.out.println(" \n");
		System.out.println("A:" + a);
		System.out.println("B:" + b);
		System.out.println("C:" + c);
		System.out.println("Z:" + z);
		System.out.println("IP:" + ip);
	}

	private void interpretCommand(String command, List<String> params) {

		String reg;
		String value;
		int registerValue;
		int intValue;
		switch (command) {
		case "LOAD":

			reg = params.get(0);
			value = params.get(1);
			setRegister(reg, value);
			ip = ip + 1;
			break;

		case "OR":

			reg = params.get(0);
			value = params.get(1);
			registerValue = getRegister(reg);
			intValue = Integer.parseInt(value);
			setRegister(reg, registerValue | intValue);
			ip = ip + 1;
			break;

		case "XOR":

			reg = params.get(0);
			value = params.get(1);
			registerValue = getRegister(reg);
			intValue = Integer.parseInt(value);
			setRegister(reg, registerValue ^ intValue);
			ip = ip + 1;
			break;

		case "PRINT":

			reg = params.get(0);
			registerValue = getRegister(reg);
			System.out.print(registerValue);
			ip = ip + 1;
			break;

		case "CMP":

			reg = params.get(0);
			value = params.get(1);
			registerValue = getRegister(reg);
			intValue = Integer.parseInt(value);
			if (registerValue == intValue) {
				z = 1;
			} else {
				z = 0;
			}
			ip = ip + 1;
			break;

		// IP <- (Z == 0) ? IP + address : IP + 1
		case "JNE":
			value = params.get(0);
			intValue = Integer.parseInt(value);
			if (z == 0) {
				ip = ip + intValue;
			} else {
				ip = ip + 1;
			}

			break;

		case "DEC":
			reg = params.get(0);
			registerValue = getRegister(reg);
			setRegister(reg, registerValue - 1);
			ip = ip + 1;
			break;

		case "STOP":
			printOverview();
			System.exit(0);
			ip = ip + 1;
			break;


		default:
			System.out.println("didn't know how to interpret command "
					+ command + " with params " + params);
			break;
		}

	}

	private int getRegister(String reg) {
		switch (reg) {
		case "A":
			return a;

		case "B":
			return b;

		case "C":
			return c;

		case "z":
			return z;

		case "IP":
			return ip;


		default:
			break;
		}
		return 0;

	}

	private void setRegister(String reg, String value) {
		int intValue = Integer.parseInt(value);
		setRegister(reg, intValue);
	}

	private void setRegister(String reg, int value) {
		switch (reg) {
		case "A":
			a = value;
			break;
		case "B":
			b = value;
			break;
		case "C":
			c = value;
			break;
		case "z":
			z = value;
			break;
		case "IP":
			ip = value;
			break;

		default:
			break;
		}

	}

}
