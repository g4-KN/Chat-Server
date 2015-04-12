package serverandclient;

import java.io.*;
import java.net.*;

public class SimpleClient {
	public static void startClient(String[] nameMessage, String address, Integer port)
			throws IOException {
		// config file. eg: mod.json


		try {
			Socket miniSocket = new Socket(address, port);
			PrintWriter out = new PrintWriter(miniSocket.getOutputStream(),
					true);
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					miniSocket.getInputStream()));
//			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
//					System.in));
			
			out.println(nameMessage[0] + ServerConstants.ESCCHAR + nameMessage[1] );
			System.out.println("message delivered to reception");
			miniSocket.close(); // need to close this or it will remain open forever until the program closes - then I can only send one message since this is a static method

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + address);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to "
					+ address);
			System.exit(1);
		}
	}
}