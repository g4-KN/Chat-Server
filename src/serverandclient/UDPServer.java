package serverandclient;

import java.io.IOException;
import java.net.*;

public class UDPServer {
	private DatagramSocket datagramSocket = null;
	private UDPServer() {

	}

	public void start() throws IOException {
		// resource leak warning if detected socket never closed
		datagramSocket = new DatagramSocket(80);
		// TCP and UDP
		// use different
		// sets of
		// ports. so servers of different
		// type can read from the same port
		// number

		byte[] buffer = new byte[100]; // byte array - raw data received; since
										// we dont konw how much data the client
										// is gonna dump at a time. just be
										// safe, I would over allocate and then
										// I can trim out the extra info later.
		for (int i = 0; i < 100; i++) {
			System.out.print(buffer[i]);
		}
		System.out.println();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		while (true) {
			System.out.println("waiting for UDP packet");
			datagramSocket.receive(packet); // this method blocks until a
											// DatagramPacket is received
			System.out.println("got the UDP packet");
			// System.out.println(packet);
			for (int i = 0; i < 100; i++) { // if sender sends more than 10
											// bytes
											// it would then truncate the
											// message so that it would fit in
											// the array. Capture the first 10
											// bytes only
				System.out.print(buffer[i] + " ");
			}
			System.out.println();
			// converting byte array back to ascii chars
			String processedData = new String(buffer, "UTF-8");
			System.out.println(processedData);

		}
	}
	public void stop(){
		datagramSocket.close();
	}

	public static void main(String[] argsV) throws IOException {

	}
}
