package coders;

import java.io.PrintStream;

import java.util.Scanner;

import coders.methodEnums.Encoders;
import util.EncodeThings;

public class EncodeMode {
	
	private static PrintStream out;
	private static Scanner sc;
	private static String internalEncoding;
	private static String steps;

	public static String run(PrintStream outStream, Scanner scanner) {
		out = outStream;
		sc = scanner;
		String aux = String.format("Encode binary: %s", encodeMode());
		if (!aux.equals("Encode binary: exit")) {
			String aux2 = String.format("Encode hex: %s", 
					EncodeThings.encodeHex(internalEncoding));
			return aux + "\n" + aux2 + "\n";
		}
		return aux + "\n" + steps;
		
	}
	
	public static double getCycles() {
		return Steps.getCycles();
	}
	
	private static String encodeMode() {
		out.println("Please use / as separator, breaks otherwise.");
		out.println("What to encode?  ");
		out.println("Use 'Ext' to exit.");
		String option = sc.next();
		try {
			String[] line = option.split("/");
			for (int i = 0; i < line.length; i++) {
				line[i] = line[i].toUpperCase();
			}
			System.out.print(Steps.getSteps(line));
			String key = line[0];
			String result;
			switch (key.substring(0, 3)) {
				case "EXT": {
					return "exit";
				}
				case "NOP": {
					result = Encoders.NOP.encode(line); 
					break;
				}
				case "MOV": {
					result = Encoders.MOV.encode(line);
					break;
				}
				case "ADD": {
					result = Encoders.Add.encode(line); 
					break;
				}
				case "SUB": {
					result = Encoders.Sub.encode(line);
					break;
				}
				case "OR ": {
					result = Encoders.Or.encode(line);
					break;
				}
				case "AND": {
					result = Encoders.And.encode(line);
					break;
				}
				case "XOR": {
					result = Encoders.Xor.encode(line);
					break;
				}
				case "CMP": {
					result = Encoders.Cmp.encode(line);
					break;
				}
				case "NOT": {
					result = Encoders.Not.encode(line);
					break;
				}
				case "INC": {
					result = Encoders.Inc.encode(line);
					break;
				}
				case "DEC": {
					result = Encoders.Dec.encode(line);
					break;
				}
				case "NEG": {
					result = Encoders.Neg.encode(line);
					break;
				}
				case "CLI": {
					result = Encoders.Cli.encode(line);
					break;
				}
				case "STI": {
					result = Encoders.Sti.encode(line);
					break;
				}
				case "INT": {
					result = Encoders.Int.encode(line);
					break;
				}
				case "IRE": {//IRET
					result = Encoders.Iret.encode(line);
					break;
				}
				case "RET": {
					result = Encoders.Ret.encode(line);
					break;
				}
				case "JMP": {
					result = Encoders.Jmp.encode(line);
					break;
				}
				case "CALL": {
					result = Encoders.Call.encode(line);
					break;
				}
				default:
					if (key.substring(0, 2).equals("BR")) {
						result = Encoders.BR.encode(line);
						internalEncoding = result;
						break;
					}
					out.println("Bad input, repeat");
					throw new IllegalArgumentException();
					
				}
			internalEncoding = result;
			return result;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new IllegalArgumentException();
		}	
	}
}
