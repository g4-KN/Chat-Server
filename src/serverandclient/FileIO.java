package serverandclient;

import java.io.*;
import java.util.ArrayList;

public class FileIO {

	// always put modifiers in for my vars

	protected synchronized static void outWrite(ArrayList<String> history,
			boolean append, String fileName) throws Exception {
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(fileName, append); // the append
															// boolean if
															// set to true
															// will appended
															// to the end of
															// the file,
															// rather than
															// over writing
															// what was
															// already there
			Integer c = 0;
			for (int i = 0; i < history.size(); i++) {
				String temp = history.get(i).toString();
				for (int j = 0; j < temp.length(); j++) {
					c = temp.codePointAt(j);
					out.write(c); // the write method
					System.out.print(c + " ");
				}
				// out.write(32); //32 is space
				out.write('\r');
				out.write('\n');
			}
		} finally {
			if (out != null) {
				out.close();
			}

		}

	}

	protected synchronized static void outWrite(String rawDump, boolean append, // this
																				// is
			// the
			// multimorphsim
			String fileName) throws Exception {
		FileOutputStream out = null;

		try {
			out = new FileOutputStream(fileName, append); // the append
															// boolean if
															// set to true
															// will appended
															// to the end of
															// the file,
															// rather than
															// over writing
															// what was
															// already there
			Integer c = 0;
			if (rawDump != null) {

				for (int j = 0; j < rawDump.length(); j++) {
					c = rawDump.codePointAt(j);
					out.write(c); // the write method
					System.out.print(c + " ");
				}
				// out.write(32); //32 is space
				out.write('\r');
				out.write('\n');
			}

		} finally {
			if (out != null) {
				out.close();
			}

		}

	}

	@SuppressWarnings("finally")
	protected static String inRead(String fileName) throws Exception {
		FileInputStream in = null;
		File inputFile = new File(fileName);

		long fileSize = inputFile.length();
		byte[] buffer = new byte[(int) fileSize]; // only reading a finite
													// amount of data(entries)
		String processedData = null;
		if (inputFile.exists() && !inputFile.isDirectory()) {
			// if file not found will throw
			// null
			// pointer exception
			try {

				in = new FileInputStream(fileName); // this is a 8-bits bytes

				Integer c = 0;
				int count = 0;
				while ((c = in.read()) != -1) {

					buffer[count] = c.byteValue();
					count++;
				}
				System.out.println("count = " + count);

				// can concatenate the buffer by sending it into a temp buffer
				// that
				// is the new size
				// to avoid sequentially printing and stopping the printing with
				// escape chars

				// byte[] tempBuffer = new byte[count];
				// tempBuffer = buffer;
				// thought this would concatenate but instead it just assign it
				// to a
				// bigger buffer.

				// this just showcase a few different attempts in solving this
				// problem
				processedData = new String(buffer, "UTF-8");

				// //injecting my escape chars
				//
				// buffer [count] = 122;
				// buffer [count+1] = 35;
				// buffer [count+2] = 36;
				//
				// processedData = new String(buffer, "UTF-8");

			} finally {
				if (in != null) {
					in.close();
				}
				return processedData;
			}
		} else {
			// this is how i handle no file found
			System.err.println("file not found");
			return processedData;
		}

	}

	public static byte[] stringToBytesASCII(String str) {
		char[] buffer = str.toCharArray();
		byte[] b = new byte[buffer.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) buffer[i];
		}
		return b;
	}

	protected void streamRun() throws Exception {
		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream("xanadu.txt"); // this is a 8-bits bytes
													// read
													// read
			out = new FileOutputStream("outagain.txt");
			int c;

			while ((c = in.read()) != -1) { // the read method ... z == 122 ...
											// -1 == end of file
				out.write(c); // the write method
				System.out.print(c + " ");
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}

		}

	}

	protected void readerRun() throws Exception {
		FileReader inChar = null;
		FileWriter outChar = null;

		try {
			inChar = new FileReader("output.txt"); // this should read 2
													// bytets at a time
			outChar = new FileWriter("output2.txt");

			int c;
			System.out.println("");
			outChar.write(168);
			while ((c = inChar.read()) != -1) { // -1 is an invalid char
				outChar.write(c);
				System.out.print(c + " ");
			}
		} finally {
			if (inChar != null) {
				inChar.close();
			}
			if (outChar != null) {
				outChar.close();
			}

		}

		int i = 168;
		String aChar = new Character((char) i).toString();
		System.out.println("");
		System.out.println(aChar);

	}
}
