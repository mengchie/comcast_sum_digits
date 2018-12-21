package oa_comcast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Comcast {
	static String fileName;
	static long MAX = Long.MAX_VALUE;
	public static void main(String[] args) throws Exception {
		long val = 0;
		String mode = checkMode(args);
		if(mode.equals("Standard")) {
			val = standard(val);
		} else if(mode.equals("StandardFile")) {
			ArrayList<String> lines = readFile(fileName);
			val = standardFile(val, lines);
		} else if(mode.equals("StandardHex")) {
			val = standardHex(val);
		} else { //HexFile
			ArrayList<String> lines = readFile(fileName);
			val = hexFile(val, lines);
		}
        System.out.println("Final value: " + val);
	}
	
	public static ArrayList<String> readFile(String file) {
		BufferedReader reader;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static long standardHex(long val) throws Exception {
		Scanner in = new Scanner(System.in);
		long finalVal = val;
        while(in.hasNextLine()) {
        	String line = in.nextLine();
        	finalVal = hexAndSum(finalVal, line);
        }
        return finalVal;
	}
	
	public static long standardFile(long val, ArrayList<String> lines) throws Exception {
		long finalVal = val;
		for(int i = 0; i < lines.size(); i++) {
			finalVal = sum(finalVal, lines.get(i));
		}
		return finalVal;
	}
	
	public static long hexFile(long val, ArrayList<String> lines) throws Exception {
		long finalVal = val;
		for(int i = 0; i < lines.size(); i++) {
			finalVal = hexAndSum(finalVal, lines.get(i));
		}
		return finalVal;
	}
	
	public static long standard(long val) throws Exception {
		Scanner in = new Scanner(System.in);
		long finalVal = val;
        while(in.hasNextLine()) {
        	String line = in.nextLine();
        	finalVal = sum(finalVal, line);
        }
        return finalVal;
	}
	
	public static long hexAndSum(long val, String line) throws Exception {
		long hexSum = 0;
		long digitSum = 0;
		long finalSum = 0;
		int start = 0;
		int end = 0;
		String hex;
		for(int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			end = i;
			if(Character.isDigit(c)) {
				if(MAX-Integer.parseInt(String.valueOf(c)) < digitSum) {
					throw new Exception("The number of sum is bigger then Long.MAX_VALUE");
				}
				digitSum += Integer.parseInt(String.valueOf(c));
			}
			if((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) {
				end = i;
				hex = new String(line.substring(start, end));
				hexSum = sum(hexSum, HexToDec(hex));
				start = i+1;
			}
		}
		end++;
		hex = new String(line.substring(start, end));
		hexSum = sum(hexSum, HexToDec(hex));
		finalSum = val;
		if(MAX-hexSum < finalSum) {
			throw new Exception("The number of sum is bigger then Long.MAX_VALUE");
		}
		finalSum += hexSum;
		if(MAX-digitSum < finalSum) {
			throw new Exception("The number of sum is bigger then Long.MAX_VALUE");
		}
		finalSum += digitSum;
		return finalSum;
	}
	
	public static String HexToDec(String hex) throws Exception {
		String digits = "0123456789ABCDEF";  
        hex = hex.toUpperCase();
        long val = 0;  
        for (int i = 0; i < hex.length(); i++) {  
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            if((MAX - d)/16 < val) {
            	throw new Exception("The number of sum is bigger then Long.MAX_VALUE");
            }
            val = 16*val + d;
        }
        return Long.toString(val);
	}
	
	public static long sum(long val, String line) throws Exception {
		for(int i = 0; i < line.length(); i++) {
			if(Character.isDigit(line.charAt(i))) {
				if(MAX-Integer.parseInt(String.valueOf(line.charAt(i))) < val) {
					throw new Exception("The number of sum is bigger then Long.MAX_VALUE");
				}
				val += Integer.parseInt(String.valueOf(line.charAt(i)));
			}
		}
		return val;
	}
	
	public static String checkMode(String[] args) {
		boolean hasFile = false;
		boolean hasHex = false;
		String mode = "Standard";
		int fIndex = -1;
		for(int i = 0; i < args.length; i++) {
			if(args[i].length() == 2 && args[i].charAt(0) == '-') {
				if(args[i].charAt(1) == 'f') {
					hasFile = true;
					fIndex = i;
				} else if(args[i].charAt(1) == 'x') {
					hasHex = true;
				} else {
					System.out.println(args[i] + " with wrong format ...");
				}
			} else {
				if(hasFile && i == fIndex+1) {
					fileName = args[i];
				} else {
					System.out.println(args[i] + " with wrong format ...");
				}
			}
		}
		
		if(hasFile && hasHex) {
			mode = "HexFile";
		} else if(hasFile) {
			mode = "StandardFile";
		} else if(hasHex) {
			mode = "StandardHex";
		} else {
			mode = "Standard";
		}
		return mode;
	}

}
