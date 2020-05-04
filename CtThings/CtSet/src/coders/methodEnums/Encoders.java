package coders.methodEnums;

import java.util.HashMap;
import util.EncodeThings;

public enum Encoders {
	NOP() {
		public String encode(String[] line) {
			return getResult("00000 00000000000");
		}
	},
	MOV() {
		public String encode(String[] line) {
			switch (line[0]) {
				case "MOV": {
					switch (line[1].charAt(0)) {
						case 'R': {
							switch (line[2].charAt(0)) {
								case '[': {
									return getResult(String.format("00010 %s 00000",
											EncodeThings.encodeRegisters(line[1].charAt(1),line[2].charAt(2))));
								}
								case 'R': {
									return getResult(String.format("00001 %s 00000",EncodeThings.encodeRegisters(line[1].charAt(1),line[2].charAt(1))));
								}
								default:
									throw new IllegalArgumentException("Unexpected value: " + line[2].charAt(0));
								}
						}
						case '[': {
							return getResult(String.format("00011 %s 00000",EncodeThings.encodeRegisters(line[1].charAt(2),line[2].charAt(1))));
						}
						default:
							throw new IllegalArgumentException("Unexpected value: " + line[1].charAt(0));
						}
				}
				case "MOVL": {
					return getResult(String.format("00100 %s %s",EncodeThings.encodeRegister(line[1].charAt(1)), EncodeThings.encodeNumber(line[2])));
				}
				case "MOVH": {
					return getResult(String.format("00101 %s %s",EncodeThings.encodeRegister(line[1].charAt(1)), EncodeThings.encodeNumber(line[2])));
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + line[0]);
				}		
		}
	},
	Add() {
		public String encode(String[] line) {
			// 01000 Rd Rs1 Rs2 00
			return getResult(String.format("01000 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	Sub() {
		public String encode(String[] line) {
			// 01001 Rd Rs1 Rs2 00
			return getResult(String.format("01001 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	Or() {
		public String encode(String[] line) {
			// 01010 Rd Rs1 Rs2 00 
			return getResult(String.format("01010 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	And() {
		public String encode(String[] line) {
			// 01011 Rd Rs1 Rs2 00 
			return getResult(String.format("01011 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	Xor() {
		public String encode(String[] line) {
			// 01100 Rd Rs1 Rs2 00
			return getResult(String.format("01100 %s %s 00",
					EncodeThings.encodeRegister(line[1].charAt(1)),			//Rd
					EncodeThings.encodeRegisters(line[2].charAt(1),line[3].charAt(1)))); //other
		}
	},
	Cmp() {
		public String encode(String[] line) {
			// 01101 Rs1 Rs2 000 00
			return getResult(String.format("01101 %s 000 00",
					EncodeThings.encodeRegisters(line[1].charAt(1),line[2].charAt(1)))); 
		}
	},
	Not() {
		public String encode(String[] line) {
			// 10000 Rd/s 00000000
			return getResult(String.format("10000 %s 00000000",
					EncodeThings.encodeRegister(line[1].charAt(1)))); 
		}
	},
	Inc() {
		public String encode(String[] line) {
			// 10001 Rd/s 00000000 I
			return getResult(String.format("10001 %s 00000000",
					EncodeThings.encodeRegister(line[1].charAt(1))));
		}
	},
	Dec() {
		public String encode(String[] line) {
			// 10010 Rd/s 00000000
			return getResult(String.format("10010 %s 00000000",
					EncodeThings.encodeRegister(line[1].charAt(1))));
		}
	},
	Neg() {
		public String encode(String[] line) {
			// 10011 Rd/s 00000000
			return getResult(String.format("10011 %s 00000000",
					EncodeThings.encodeRegister(line[1].charAt(1))));
		}
	},
	Cli() {
		public String encode(String[] line) {
			return getResult("10100 00000000000");
		}
	},
	Sti() {
		public String encode(String[] line) {
			return getResult("10101 00000000000");
		}
	},
	Int() {
		public String encode(String[] line) {
			// 10110 000 Inm8 
			return getResult(String.format("10011 %s 00000000",
					EncodeThings.encodeNumber(line[1])));
		}
	},
	Iret() {
		public String encode(String[] line) {
			return getResult("10111 00000000000");
		}
	},
	Ret() {
		public String encode(String[] line) {
			return getResult("11100 00000000000");
		}
	},
	Jmp() {
		public String encode(String[] line) {
			switch (line[1].charAt(0)) {
				case 'R': {
					return getResult(String.format("11001 %s 00000000",
							EncodeThings.encodeRegister(line[1].charAt(1))));
				}
				default:
					return getResult(String.format("11000 000 %s",
							EncodeThings.encodeNumber(line[1])));
			}
		}
	},
	Call() {
		public String encode(String[] line) {
			switch (line[1].charAt(0)) {
			case 'R': {
				return getResult(String.format("11011 %s 00000000",
						EncodeThings.encodeRegister(line[1].charAt(1))));
			}
			default:
				return getResult(String.format("11010 000 %s",
						EncodeThings.encodeNumber(line[1])));
			}
		}
	},
	BR() {
		public String encode(String[] line) {
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
	
	public abstract String encode(String[] line);

	private static String getResult(String result) {
		return result;
	}
	;
}
