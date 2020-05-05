package coders;

import java.util.ArrayList;
import java.util.List;

public class Steps {
	private static int cycles = 0;
	private static List<String> sTeps = new ArrayList<String>();

	public static String getSteps(String[] line) {
		String step = String.format("%s\n%s\n%s\n", 
				"1 PC-IB, IB-MAR, TMPE_CLR, CARRY_IN, ADD, ALU-TMPS, READ",
				"2 TMPS-IB, IB-PC",
				"3 MDR-IB, IB-IR");
		step += addSteps(line, step);
		
		
		return step + "\n" + String.format("cycles: %d\n", getCycles());
	}
	
	public static int getCycles() {
		return cycles;
	}
	
	private static String addSteps(String[] line, String step) {
		String key = line[0];
		sTeps.add(key);
		step = selectSteps(line);
		return step;
	}
	
	private static String selectSteps(String[] line) {
		String key = line[0];
		String result = "";
		switch (key.substring(0, 3)) {
		case "NOP": {
			result = "4 FIN"; 
			cycles = 4;
			break;
		}
		case "MOV": {
			result = encodeMOV(line);
			break;
		}
		case "ADD": {
			result = encodeAdd(line); 
			break;
		}
		case "SUB": {
			result = encodeSub(line);
			break;
		}
		case "OR ": {
			result = encodeOr(line);
			break;
		}
		case "AND": {
			result = encodeAnd(line);
			break;
		}
		case "XOR": {
			result = encodeXor(line);
			break;
		}
		case "CMP": {
			result = encodeCmp(line);
			break;
		}
		case "NOT": {
			result = encodeNot(line);
			break;
		}
		case "INC": {
			result = encodeInc(line);
			break;
		}
		case "DEC": {
			result = encodeDec(line);
			break;
		}
		case "NEG": {
			result = encodeNeg(line);
			break;
		}
		case "CLI": {
			result = encodeCli();
			break;
		}
		case "STI": {
			result = encodeSti();
			break;
		}
		case "INT": {
			result = encodeInt(line);
			break;
		}
		case "IRE": {//IRET
			result = encodeIret();
			break;
		}
		case "RET": {
			result = encodeRet();
			break;
		}
		case "JMP": {
			result = encodeJmp(line);
			break;
		}
		case "CALL": {
			result = encodeCall(line);
			break;
		}
		default:
			if (key.substring(0, 2).equals("BR")) {
				result = encodeBrs(line);
				break;
			}
			System.out.println("Bad input, repeat");
		}
		String res = result;
		return res;
	}
	
	private static String encodeBrs(String[] line) {
		cycles = 6;
		return "4 PC-IB, IB-TMPE\n" + 
				"5 ExtIRl-IB, ADD, ALU-TMPS\n" + 
				"6 TMPS-IB, IB-PC, FIN";
	}

	private static String encodeCall(String[] line) {
		switch (line[1].charAt(0)) {
		case 'R': {
			cycles = 7;
			return ("4 R7-IB, TMPE_SET, ADD, ALU-TMPS\n" + 
					"5 PC-IB, IB-MDR\n" + 
					"6 TMPS-IB, IB-R7, IB-MAR, WRITE\n" + 
					"7 Rx-IB, IB-PC, FIN");
		}
		default:
			cycles = 8;
			return ("4 R7-IB, TMPE_SET, ADD, ALU-TMPS\n" + 
					"5 PC-IB, IB-MDR, IB-TMPE\n" + 
					"6 TMPS-IB, IB-R7, IB-MAR, WRITE\n" + 
					"7 ExtIRl-IB, ADD, ALU-TMPS\n" + 
					"8 TMPS-IB, IB-PC, FIN");
	}
	}

	private static String encodeJmp(String[] line) {
		switch (line[1].charAt(0)) {
			case 'R': {
				cycles = 4;
				return "4 Rx-IB, IB-PC, FIN";
			}
			default:
				cycles = 6;
				return "4 PC-IB, IB-TMPE\n" + 
						"5 ExtIRl-IB, ADD, ALU-TMPS\n" + 
						"6 TMPS-IB, IB-PC, FIN";
		}
	}

	private static String encodeRet() {
		cycles = 6;
		return "4 R7-IB, TMPE_CLR, CARRY_IN, ADD, ALU-TMPS, IB-MAR, READ\n" + 
				"5 TMPS-IB, IB-R7\n" + 
				"6 MDR-IB, IB-PC, FIN";
	}

	private static String encodeIret() {
		cycles = 9;
		return "4 R7-IB, TMPE_CLR, CARRY_IN, ADD, ALU-TMPS, IB-MAR, READ\n" + 
				"5 TMPS-IB, IB-R7\n" + 
				"6 MDR-IB, IB-PC\n" + 
				"7 R7-IB, TMPE_CLR, CARRY_IN, ADD, ALU-TMPS, IB-MAR, READ\n" + 
				"8 TMPS-IB, IB-R7\n" + 
				"9 MDR-IB, IB-SR, FIN";
	}

	private static String encodeInt(String[] line) {
		cycles = 13;
		return "4 R7-IB, TMPE_SET, ADD, ALU-TMPS\n" + 
				"5 SR-IB, IB-MDR\n" + 
				"6 TMPS-IB, IB-R7, IB-MAR, WRITE\n" + 
				"7 R7-IB, TMPE_SET, ADD, ALU-TMPS\n" + 
				"8 PC-IB, IB-MDR\n" + 
				"9 TMPS-IB, IB-R7, IB-MAR, WRITE\n" + 
				"10 Ciclo de espera\n" + 
				"11 ExtIRl-IB, IB-MAR, READ\n" + 
				"12 Ciclo de espera\n" + 
				"13 MDR-IB, IB-PC, FIN";
	}

	private static String encodeSti() {
		cycles = 4;
		return "4 STI, FIN";
	}

	private static String encodeCli() {
		cycles = 4;
		return "4 CLI, FIN";
	}

	private static String encodeNeg(String[] line) {
		cycles = 5;
		return "4 Rd/s-IB, TMPE_CLR, SUB, ALU-SR, ALU-TMPS\n" + 
				"5 TMPS-IB, IB-Rd/s, FIN";
	}

	private static String encodeDec(String[] line) {
		cycles = 5;
		return "4 Rd/s-IB, TMPE_SET, ADD, ALU-SR, ALU-TMPS\n" + 
				"5 TMPS-IB, IB-Rd/s, FIN";
	}

	private static String encodeInc(String[] line) {
		cycles = 5;
		return "4 Rd/s-IB, TMPE_CLR, CARRY_IN, ADD, ALU-SR, ALU-TMPS\n" + 
				"5 TMPS-IB, IB-Rd/s, FIN";
	}

	private static String encodeNot(String[] line) {
		cycles = 5;
		return "4 Rd/s-IB, TMPE_SET, XOR, ALU-SR, ALU-TMPS\n" + 
				"5 TMPS-IB, IB-Rd/s, FIN"; 
	}


	private static String encodeCmp(String[] line) {
		cycles = 5;
		return "4 Rs1-IB, IB-TMPE\n" + 
				"5 Rs2-IB, SUB, ALU-SR, FIN";
	}

	private static String encodeXor(String[] line) {
		cycles = 6;
		return "4 Rs1-IB, IB-TMPE\n" + 
				"5 Rs2-IB, XOR, ALU-SR, ALU-TMPS\n" + 
				"6 TMPS-IB, IB-Rd, FIN";
	}

	private static String encodeAnd(String[] line) {
		cycles = 6;
		return "4 Rs1-IB, IB-TMPE\n" + 
				"5 Rs2-IB, AND, ALU-SR, ALU-TMPS\n" + 
				"6 TMPS-IB, IB-Rd, FIN";
	}

	private static String encodeOr(String[] line) {
		cycles = 6;
		return "4 Rs1-IB, IB-TMPE\n" + 
				"5 Rs2-IB, OR, ALU-SR, ALU-TMPS\n" + 
				"6 TMPS-IB, IB-Rd, FIN";
	}

	private static String encodeSub(String[] line) {
		cycles = 6;
		return "4 Rs1-IB, IB-TMPE\n" + 
				"5 Rs2-IB, SUB, ALU-SR, ALU-TMPS\n" + 
				"6 TMPS-IB, IB-Rd, FIN";
	}

	private static String encodeAdd(String[] line) {
		cycles = 6;
		return "4 Rs1-IB, IB-TMPE\n" + 
				"5 Rs2-IB, ADD, ALU-SR, ALU-TMPS\n" + 
				"6 TMPS-IB, IB-Rd, FIN";
	}

	private static String encodeMOV(String[] line) {
		switch (line[0]) {
			case "MOV": {
				switch (line[1].charAt(0)) {
					case 'R': {
						switch (line[2].charAt(0)) {
							case '[': {
								cycles = 6;
								return "4 Ri-IB, IB-MAR, READ\n" + 
										"5 Wait cycle\n" + 
										"6 MDR-IB, IB-Rd, FIN";
							}
							case 'R': {
								cycles = 4;
								return "4 Rs-IB, IB-Rd, FIN";
							}
							default:
							}
					}
					case '[': {
						cycles = 6;
						return "4 Ri-IB, IB-MAR\n" + 
								"5 Rs-IB, IB-MDR, WRITE\n" + 
								"6 FIN";
					}
					default:
						}
			}
			case "MOVL": {
				cycles = 4;
				return "4 IRl-IBl, IBl-Rdl, FIN";
			}
			case "MOVH": {
				cycles = 4;
				return "4 IRl-IBh, IBh-Rdh, FIN";
			}
			default:
			}
		return null;		
	}
}
