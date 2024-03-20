package hr.fer.oprpp1.hw05.crypto;


public class Util {
	
	public static byte[]  hextobyte(String keyText) {
		int length = keyText.length();
	    byte[] ret = new byte[length / 2];
	    for (int i = 0; i < length; i += 2) {
	    	int firstDigit=toDecimal(keyText.charAt(i));
	    	int secondDigit=toDecimal(keyText.charAt(i+1));
	        ret[i / 2] = (byte) ((firstDigit << 4) + secondDigit);
	    }
	    return ret;
	}
	
	private static int toDecimal(char c) {
		String value=c+"";
		value=value.toUpperCase();
		switch(value) {
		case "A":
			return 10;
		case "B":
			return 11;
		case "C":
			return 12;
		case "D":
			return 13;
		case "E":
			return 14;
		case "F":
			return 15;
		default:
			return Integer.parseInt(value);
		}
	}
	
	public static String bytetohex(byte[] bytes) {
		  final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		            'a', 'b', 'c', 'd', 'e', 'f' };

		    char[] result = new char[2 * bytes.length];
		    int j = 0;
		    for (byte B : bytes) {   
		    	int index=240 & B;
		    	result[j] = HEX[index >>> 4];   
		    	j+=1;
		    	index=15 & B;
		    	result[j] = HEX[index];
		    	j+=1;                                            
		    }
		    
		    String ret="";
		    for (char c:result)
		    	ret+=c;
		    return ret;
	}
		

}
