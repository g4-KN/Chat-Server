package serverandclient;

import java.io.IOException;
import java.net.*;

public class UDPClient {
	private DatagramSocket datagramSocket = null;
	private UDPClient() {

	}

	public void start() throws IOException, InterruptedException {
		datagramSocket = new DatagramSocket(9999);
		Integer count = 0;
		while (true) {

			// byte[] buffer = "0123456789".getBytes(); //
			// "48 49 50 51 52 53 54 55 56 57"
			// this is the ascii
			// byte representation
			// of the chars - ascii
			// uses 7 bits, one byte
			// but the first bit is useless
			byte[] buffer = count.toString().getBytes();
			byte[] data = "Kenneth OK 012345678901234567890123456789012345678901234567890123456789"
					.getBytes();

			for (int i = 0; i < buffer.length; i++) {
				 System.out.print(buffer[i] + " ");
			}
			// UDPServer.main(argsV); // running everything in here. initiating
			// the server
			System.out.println();
			InetAddress receiverAddress = InetAddress.getLocalHost();
			// InetAddress receiverAddress = InetAddress.getByName("google.ca");
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
					receiverAddress, 9999);
			DatagramPacket packet2 = new DatagramPacket(data, data.length,
					receiverAddress, 9999);

			// datagramSocket.send(packet);
			datagramSocket.send(packet2);
			System.out.println("UDP packet sent");
			count++;

			Thread.sleep(5000);

		}
	}
	
	public void stop(){
		datagramSocket.close();
	}


	public static void main(String[] argsV) throws IOException,
			InterruptedException {

	}
}
