package coders.methodEnumsEncoder;

import java.util.HashMap;

import util.EncodeThings;

/**
 * This an enum where I store the different operations that can be called when 
 * encoding.
 * @author Usuario
 *
 */
public enum Encoders {
	NOP() {
		/**
		 * Always returns the same result
		 */
		public String encode(String[] line) {
			return getResult("00000 00000000000");
		}
	},
	MOV() {
		/**
		 * Encodes the different types of move
		 */
		public String encode(String[] line) {
			/**
			 * Checks the first part of the asked string with the predefined 
			 * format
			 */
			switch (line[0]) {
				case "MOV": {
					switch (line[1].charAt(0)) {
						//move from register
						case 'R': {
							switch (line[2].charAt(0)) {
								//to memory
								case '[': {
									return getResult(String.format("00010 %s 00000",
											EncodeThings.encodeRegisters(line[1].charAt(1),line[2].charAt(2))));
								}
								//to register
								case 'R': {
									return getResult(String.format("00001 %s 00000",EncodeThings.encodeRegisters(line[1].charAt(1),line[2].charAt(1))));
								}
								//something bad getting here
								default:
									throw new IllegalArgumentException("Unexpected value: " + line[2].charAt(0));
								}
						}
						//move to memory
						case '[': {
							return getResult(String.format("00011 %s 00000",EncodeThings.encodeRegisters(line[1].charAt(2),line[2].charAt(1))));
						}
						//something bad getting here
						default:
							throw new IllegalArgumentException("Unexpected value: " + line[1].charAt(0));
						}
				}
				//move low
				case "MOVL": {
					return getResult(String.format("00100 %s %s",EncodeThings.encodeRegister(line[1].charAt(1)), EncodeThings.encodeNumber(line[2])));
				}
				//move high
				case "MOVH": {
					return getResult(String.format("00101 %s %s",EncodeThings.encodeRegister(line[1].charAt(1)), EncodeThings.encodeNumber(line[2])));
				}
				//something bad getting here
				default:
					throw new IllegalArgumentException("Unexpected value: " + line[0]);
				}		
		}
	},
	Add() {
		/**
		 * Encodes the add operation
		 */
		public String encode(String[] line) {
			// 01000 Rd Rs1 Rs2 00
			return getResult(String.format("01000 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	Sub() {
		/**
		 * Encodes the sub operation
		 */
		public String encode(String[] line) {
			// 01001 Rd Rs1 Rs2 00
			return getResult(String.format("01001 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	Or() {
		/**
		 * Encodes the or operation
		 */
		public String encode(String[] line) {
			// 01010 Rd Rs1 Rs2 00 
			return getResult(String.format("01010 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	And() {
		/**
		 * Encodes the and operation
		 */
		public String encode(String[] line) {
			// 01011 Rd Rs1 Rs2 00 
			return getResult(String.format("01011 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	Xor() {
		/**
		 * Encodes the xor operation
		 */
		public String encode(String[] line) {
			// 01100 Rd Rs1 Rs2 00
			return getResult(String.format("01100 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	Cmp() {
		/**
		 * Encodes the cmp operation
		 */
		public String encode(String[] line) {
			// 01101 Rs1 Rs2 000 00
			return getResult(String.format("01101 %s 000 00",
					EncodeThings.encodeRegisters(line[1].charAt(1),line[2].charAt(1)))); 
		}
	},
	Not() {
		/**
		 * Encodes the not operation
		 */
		public String encode(String[] line) {
			// 10000 Rd/s 00000000
			return getResult(String.format("10000 %s 00000000",
					EncodeThings.encodeRegister(line[1].charAt(1)))); 
		}
	},
	Inc() {
		/**
		 * Encodes the inc operation
		 */
		public String encode(String[] line) {
			// 10001 Rd/s 00000000 I
			return getResult(String.format("10001 %s 00000000",
					EncodeThings.encodeRegister(line[1].charAt(1))));
		}
	},
	Dec() {
		/**
		 * Encodes the dec operation
		 */
		public String encode(String[] line) {
			// 10010 Rd/s 00000000
			return getResult(String.format("10010 %s 00000000",
					EncodeThings.encodeRegister(line[1].charAt(1))));
		}
	},
	Neg() {
		/**
		 * Encodes the neg operation
		 */
		public String encode(String[] line) {
			// 10011 Rd/s 00000000
			return getResult(String.format("10011 %s 00000000",
					EncodeThings.encodeRegister(line[1].charAt(1))));
		}
	},
	Cli() {
		/**
		 * Encodes the cli operation
		 */
		public String encode(String[] line) {
			return getResult("10100 00000000000");
		}
	},
	Sti() {
		/**
		 * Encodes the sti operation
		 */
		public String encode(String[] line) {
			return getResult("10101 00000000000");
		}
	},
	Int() {
		/**
		 * Encodes the int operation
		 */
		public String encode(String[] line) {
			// 10110 000 Inm8 
			return getResult(String.format("10011 %s 00000000",
					EncodeThings.encodeNumber(line[1])));
		}
	},
	Iret() {
		/**
		 * Encodes the iret operation
		 */
		public String encode(String[] line) {
			return getResult("10111 00000000000");
		}
	},
	Ret() {
		/**
		 * Encodes the ret operation
		 */
		public String encode(String[] line) {
			return getResult("11100 00000000000");
		}
	},
	Jmp() {
		/**
		 * Encodes the different types of jump operations
		 */
		public String encode(String[] line) {
			switch (line[1].charAt(0)) {
				//Jumps to the value of the register
				case 'R': {
					return getResult(String.format("11001 %s 00000000",
							EncodeThings.encodeRegister(line[1].charAt(1))));
				}
				//Jumps to a position
				default:
					return getResult(String.format("11000 000 %s",
							EncodeThings.encodeNumber(line[1])));
			}
		}
	},
	Call() {
		/**
		 * Encodes the call operation
		 */
		public String encode(String[] line) {
			switch (line[1].charAt(0)) {
				//Jumps to the value of the register
				case 'R': {
					return getResult(String.format("11011 %s 00000000",
							EncodeThings.encodeRegister(line[1].charAt(1))));
				}
				//Jumps to a position
				default:
					return getResult(String.format("11010 000 %s",
							EncodeThings.encodeNumber(line[1])));
				}
		}
	},
	BR() {
		/**
		 * Encodes the different types of br operations
		 */
		public String encode(String[] line) {
			//gets the corresponding br code using the start of the string param
			HashMap<String, String> conds = new HashMap<String, String>();
			conds.put("BRC", "000");
			conds.put("BRNC", "001");
			conds.put("BRO", "010");
			conds.put("BRNO", "011");
			conds.put("BRZ", "100");
			conds.put("BRNZ", "101");
			conds.put("BRS", "110");
			conds.put("BRNS", "111");
			return getResult(String.format("11110 %s %s",
					conds.get(line[0]),
					EncodeThings.encodeNumber(line[1])));
		}
	}
	;

	Encoders() {
	}
	
	/**
	 * Calls the specific encode for each operation
	 * @param line
	 * @return
	 */
	public abstract String encode(String[] line);

	/**
	 * Uses the String param to get a clear result
	 * @param result
	 * @return
	 */
	private static String getResult(String result) {
		//I will erase it in future versions
		return result;
	}
	;
}
