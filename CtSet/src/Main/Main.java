package Main;

import java.io.PrintStream;

import java.util.Scanner;

//import coders.DecodeMode;
import coders.EncodeMode;
import codersFile.EncodeFileMode;
import emulator.OperationEmulator;


public class Main {

	private static PrintStream out = System.out;
	private static Scanner sc = new Scanner( System.in );
	private static double totalTime = 0;
	
	@SuppressWarnings("finally")
	public static void main(String[] args) {
		out.println("decode and files are in progress.");
		out.println("Select mode(1 = decode | 2 = encode | 3 = emulateOperations"
				+ " | 4 = encodeFile | 5 = exit) ");
		int option = sc.nextInt();
		OperationEmulator oe = new OperationEmulator(out, sc);
		do {
		switch (option) {
			case 1: {
				//out.print(DecodeMode.run(out, sc));
				main(args);//while in progress
				break;
			}
			case 2: {
				try {
					out.println("Please enter the frecuency in MHz:");
					double frecuency = Double.valueOf(sc.next());
					String aux = EncodeMode.run(out, sc);
					out.println(aux);
					double time = EncodeMode.getCycles() * (1/(frecuency*1000000));
					out.println("Time to execute: " + time);
					totalTime += time;
					if (aux.equals("Encode binary: exit\n")) {
						option = 4;
					}
					out.println("Total time to execute: " + totalTime);
				} catch (Exception e) {
				} finally {
					break;
				}
			}
			case 3: {
				oe.emulateOperations();
				break;
			}
			case 4 : {
				try {
					out.println("Please enter the frecuency in MHz:");
					double frecuency = Double.valueOf(sc.next());
					EncodeFileMode.run(out, sc, frecuency);
					double time = EncodeFileMode.getCycles() * (1/(frecuency*1000000));
					totalTime += time;
					out.println("Total time to execute: " + totalTime);
				} catch (Exception e) {
				} finally {
					break;
				}
			}
			case 5: {
				break;//exit
			}
			default:
				System.err.println("Unexpected value: " + option);
				main(args);
			}
		} while(option != 4);
		
	}
	

}
