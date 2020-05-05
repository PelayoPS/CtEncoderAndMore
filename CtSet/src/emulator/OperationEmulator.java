package emulator;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import coders.Steps;


public class OperationEmulator {

	private Registers registers;
	private Flags flags;
	private int lastOperationResult;
	private static PrintStream out;
	private static Scanner sc;
	
	private class Registers {
		protected HashMap<String, String> registers = new HashMap<String, String>();
//Hacerlo mejor
		public Registers() {
			registers.put("R0", "0000");
			registers.put("R1", "0000");
			registers.put("R2", "0000");
			registers.put("R3", "0000");
			registers.put("R4", "0000");
			registers.put("R5", "0000");
			registers.put("R6", "0000");
			registers.put("R7", "0000");
		}
	}
	
	private class Flags {
		protected HashMap<String, String> flags = new HashMap<String, String>();

		public Flags() {
			flags.put("Z", "0");
			flags.put("C", "0");
			flags.put("O", "0");
			flags.put("S", "0");
		}
	}
	
	public OperationEmulator(PrintStream outStream, Scanner scanner) {
		this.registers = new Registers();
		this.flags = new Flags();
		OperationEmulator.sc = scanner;
		OperationEmulator.out = outStream;
	}
	
	private void checkFlags() {
		if (this.lastOperationResult < 0) {
			this.flags.flags.replace("S",
					this.flags.flags.get("S"), "1");
		}
		if (this.lastOperationResult > 65535) {
			this.flags.flags.replace("O",
					this.flags.flags.get("O"), "1");
		}
		if (this.lastOperationResult == 0) {
			this.flags.flags.replace("Z",
					this.flags.flags.get("Z"), "1");
		}
	}
	
	public void emulateOperations() {
		out.println("Please use / as separator, breaks otherwise.");
		out.println("What to do?  ");
		out.println("Use 'Ext' to exit.");
		String option = sc.next();
		try {
			String[] line = option.split("/");
			for (int i = 0; i < line.length; i++) {
				line[i] = line[i].toUpperCase();
			}
			out.print(Steps.getSteps(line));
			String key = line[0];
			switch (key.substring(0, 3)) {
				case "NOP": {
					//does nothing; 
					break;
				}
				case "MOV": {
					mov(line);
					break;
				}
				case "ADD": {
					add(line[1],line[2], line[3]); 
					break;
				}
				case "SUB": {
					sub(line[1],line[2], line[3]);
					break;
				}
				case "OR ": {
					or(line[1],line[2], line[3]);
					break;
				}
				case "AND": {
					and(line[1],line[2], line[3]);
					break;
				}
				case "XOR": {
					xor(line[1],line[2], line[3]);
					break;
				}
				case "CMP": {
					cmp(line[1],line[2]);
					break;
				}
				case "NOT": {
					not(line[1]);
					break;
				}
				case "INC": {
					inc(line[1]);
					break;
				}
				case "DEC": {
					dec(line[1]);
					break;
				}
				case "NEG": {
					//result = encodeNeg(line);
					System.out.println("In progress...");
					break;
				}
				case "CLI": {
					//result = encodeCli();
					System.out.println("In progress...");
					break;
				}
				case "STI": {
					//result = encodeSti();
					System.out.println("In progress...");
					break;
				}
				case "INT": {
					//result = encodeInt(line);
					System.out.println("In progress...");
					break;
				}
				case "IRE": {//IRET
					//result = encodeIret();
					System.out.println("In progress...");
					break;
				}
				case "RET": {
					//result = encodeRet();
					System.out.println("In progress...");
					break;
				}
				case "JMP": {
					//result = jmp(line);
					System.out.println("In progress...");
					break;
				}
				case "CALL": {
					//result = encodeCall(line);
					System.out.println("In progress...");
					break;
				}
				default:
					if (key.substring(0, 2).equals("BR")) {
						//result = encodeBrs(line);
						System.out.println("In progress...");
						break;
					}
					System.out.println("Bad input, repeat");
					throw new IllegalArgumentException();
					
				}
			checkFlags();
			showResult();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}	
	}
	
	public void showResult() {
		out.println(this.registers.registers.toString());
		out.println(this.flags.flags.toString());
	}
	
	private int toIntFromHex(String hex) {
		return Integer.decode("0x"+hex);
	}
	
	private String encodeHex(int number) {
		String aux = String.format("%04X", number).toUpperCase();
		if (aux.length() > 4) {
			aux = aux.substring(aux.length()-4, aux.length());
		}
		return aux;
	}
	
	public void mov(String[] line) {
		String des = line[1];
		String src = line[2];
		switch (line[0]) {
		case "MOV": {
			switch (line[1].charAt(0)) {
				case 'R': {
					switch (line[2].charAt(0)) {
						case '[': {
							movMToR(des, src);
							break;
						}
						case 'R': {
							movRToR(des, src);
							break;
						}
						default:
							throw new IllegalArgumentException("Unexpected value: " + line[2].charAt(0));
						}
					break;
				}
				case '[': {
					movRToM(des, src);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + line[1].charAt(0));
				}
			break;
		}
		case "MOVL": {
			movl(des, src);
			break;
		}
		case "MOVH": {
			movh(des, src);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + line[0]);
		}		
	}
	
	/**
	 * from register to register
	 * @param des
	 * @param src
	 */
	private void movRToR(String des, String src) {
		this.registers.registers.replace(des, 
				this.registers.registers.get(des), 
				this.registers.registers.get(src));
	}
	
	/**
	 * from memory to register 
	 * @param des
	 * @param src
	 */
	private void movMToR(String des, String src) {
		this.registers.registers.replace(des, 
				this.registers.registers.get(des), 
				this.registers.registers.get(src));
	}
	
	/**
	 * from register to memory 
	 * @param des
	 * @param src
	 */
	private void movRToM(String des, String src) {
		this.registers.registers.replace(des, 
				this.registers.registers.get(des), 
				this.registers.registers.get(src));
	}
	
	/**
	 * from hex low to register 
	 * @param des
	 * @param src
	 */
	private void movl(String des, String src) {
		this.registers.registers.replace(des, 
				this.registers.registers.get(des), 
				(this.registers.registers.get(des).
						substring(0, 2) + src));
	}
	
	/**
	 * from hex high to register 
	 * @param des
	 * @param src
	 */
	private void movh(String des, String src) {
		this.registers.registers.replace(des, 
				this.registers.registers.get(des), 
				(src + this.registers.registers.get(des).
						substring(2, 4)));
	}
	
	/**
	 * adds in hexadecimal
	 * @param des
	 * @param reg1
	 * @param reg2
	 */
	public void add(String des, String reg1, String reg2) {
		int reg1Value = toIntFromHex(this.registers.registers.get(reg1));
		int reg2Value = toIntFromHex(this.registers.registers.get(reg2));
		this.lastOperationResult = reg1Value + reg2Value;
		String hexResult = encodeHex(lastOperationResult);
		this.registers.registers.replace(des,
				this.registers.registers.get(des), hexResult);
	}
	
	/**
	 * subtracts in hexadecimal
	 * @param des
	 * @param reg1
	 * @param reg2
	 */
	public void sub(String des, String reg1, String reg2) {
		int reg1Value = toIntFromHex(this.registers.registers.get(reg1));
		int reg2Value = toIntFromHex(this.registers.registers.get(reg2));
		this.lastOperationResult = reg1Value - reg2Value;
		String hexResult = encodeHex(lastOperationResult);
		this.registers.registers.replace(des,
				this.registers.registers.get(des), hexResult);
	}
	
	/**
	 * or operation in hexadecimal
	 * @param des
	 * @param reg1
	 * @param reg2
	 */
	public void or(String des, String reg1, String reg2) {
		int reg1Value = toIntFromHex(this.registers.registers.get(reg1));
		int reg2Value = toIntFromHex(this.registers.registers.get(reg2));
		this.lastOperationResult = reg1Value | reg2Value;
		String hexResult = encodeHex(lastOperationResult);
		this.registers.registers.replace(des,
				this.registers.registers.get(des), hexResult);
	}
	
	/**
	 * and operation in hexadecimal
	 * @param des
	 * @param reg1
	 * @param reg2
	 */
	public void and(String des, String reg1, String reg2) {
		int reg1Value = toIntFromHex(this.registers.registers.get(reg1));
		int reg2Value = toIntFromHex(this.registers.registers.get(reg2));
		this.lastOperationResult = reg1Value & reg2Value;
		String hexResult = encodeHex(lastOperationResult);
		this.registers.registers.replace(des,
				this.registers.registers.get(des), hexResult);
	}
	
	/**
	 * and operation in hexadecimal
	 * @param des
	 * @param reg1
	 * @param reg2
	 */
	public void xor(String des, String reg1, String reg2) {
		int reg1Value = toIntFromHex(this.registers.registers.get(reg1));
		int reg2Value = toIntFromHex(this.registers.registers.get(reg2));
		this.lastOperationResult = reg1Value ^ reg2Value;
		String hexResult = encodeHex(lastOperationResult);
		this.registers.registers.replace(des,
				this.registers.registers.get(des), hexResult);
	}
	
	/**
	 * cmp operation in hexadecimal
	 * @param reg1
	 * @param reg2
	 */
	public void cmp(String reg1, String reg2) {
		int reg1Value = toIntFromHex(this.registers.registers.get(reg1));
		int reg2Value = toIntFromHex(this.registers.registers.get(reg2));
		this.lastOperationResult = reg2Value - reg1Value;
	}
	
	/**
	 * not operation in hexadecimal
	 * @param des
	 */
	public void not(String desSrc) {
		int reg1Value = toIntFromHex(this.registers.registers.get(desSrc));
		this.lastOperationResult = ~reg1Value;
		String hexResult = encodeHex(lastOperationResult);
		this.registers.registers.replace(desSrc,
				this.registers.registers.get(desSrc), hexResult);
	}
	
	/**
	 * inc operation in hexadecimal
	 * @param des
	 */
	public void inc(String desSrc) {
		int reg1Value = toIntFromHex(this.registers.registers.get(desSrc));
		this.lastOperationResult = reg1Value + 1;
		String hexResult = encodeHex(lastOperationResult);
		this.registers.registers.replace(desSrc,
				this.registers.registers.get(desSrc), hexResult);
	}
	
	/**
	 * dec operation in hexadecimal
	 * @param des
	 */
	public void dec(String desSrc) {
		int reg1Value = toIntFromHex(this.registers.registers.get(desSrc));
		this.lastOperationResult = reg1Value - 1;
		String hexResult = encodeHex(lastOperationResult);
		this.registers.registers.replace(desSrc,
				this.registers.registers.get(desSrc), hexResult);
	}
	
	/**
	 * neg operation in hexadecimal
	 * @param des
	 */
	public void neg(String desSrc) {
		not(desSrc);
		inc(desSrc);
	}
}
