package codersFile;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;


import coders.Steps;
import coders.methodEnumsEncoder.Encoders;
import util.EncodeThings;
import util.files.FileUtil;

public class EncodeFileMode {
	
	private static PrintStream out;
	private static Scanner sc;
	private static String internalEncoding;
	private static double cycles = 0;

	public static void run(PrintStream outStream, Scanner scanner, double frecuency) {
		out = outStream;
		sc = scanner;
		
		out.println("Archive to read?  ");
		out.println("Use 'Ext' to exit.");
		String option = sc.next();
		FileUtil bfu = new FileUtil();
		
		List<String> list;
		try {
			list = bfu.readLines(option);
			for (String s : list) {
				String aux = String.format("Encode binary: %s", encodeMode(s));
				String aux2 = String.format("Encode hex: %s", 
							EncodeThings.encodeHex(internalEncoding));
				out.println(aux + "\n" + aux2 + "\n");
				cycles += Steps.getCycles();
				double time = Steps.getCycles() * (1/(frecuency*1000000));
				out.println("Time to execute: " + time);
			}
		}  catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static double getCycles() {
		return cycles;
	}
	
	private static String encodeMode(String s) {
		
		try {
			String[] line = s.split("/");
			out.println("\n" + s);
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
			throw new RuntimeException(e);
		}	
	}
}
