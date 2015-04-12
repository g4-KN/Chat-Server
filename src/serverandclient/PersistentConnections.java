package serverandclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



// //////////////////ignore this implementation for now

public class PersistentConnections implements Runnable { 




	
	// this is one loop
	// per
	// connection

	// can only lock on

	// try to use default constructor for the executor object -> regular obj
	/*
	 * private final ServerSocket serverConnection; private ServerSocket
	 * serverSocket;
	 * 
	 * public Connections(ServerSocket serverSocket2) { this.serverSocket =
	 * serverSocket2; }
	 */
	
	private Socket clientSocket = null; // init the var first -> and then
	// modify it later, this way the
	// scope of the var is wider

	public PersistentConnections(Socket clientSocket) {
		this.clientSocket = clientSocket;

	}

	@Override
	public synchronized void run() {
		System.out.println("worker thread started");
		if (EchoServer.args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		// only one service can be reading from the port at a time (server),
		// but
		// any number of clients plus the server can write to it.

		int portNumber = Integer.parseInt(EchoServer.args[0]);
		String tempBuffer = "";

		// EchoServer echoServer = new EchoServer(); // this is just a dummy
		// object

		// this will be TCP connection. for UDP use dump

		try {

			PrintWriter out = new PrintWriter(
					this.clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					this.clientSocket.getInputStream()));

			String inputLine;
			String[] nameMessage; // entry 0 = username, entry 1 = message

			System.out
					.println("client has joined the server and connection is now up and running");
			// inputLine = in.readLine();
			/***************
			 * without embedding this in the while, need to explicitly call it
			 ************/
			while ((inputLine = in.readLine()) != null) {

				/**
				 * this is executed // once the user // press enter // ... //
				 * won't just // keep // running the // inside loop // :D - //
				 * it is like // interrupts. // This // model is not // ideal as
				 * it // would // only allow // one // client socket // to //
				 * write to it. // For // multi // connections, // use // event
				 * loop // mode, // which creates // a // connectionHandler //
				 * class to run // on // its separate // thread.
				 **/

				nameMessage = inputLine.split(ServerConstants.ESCCHAR);
				/**
				 * escape char - if the // message is null // then // that would
				 * return // an // array of length 0 // to // the second entry
				 * // of // the array = no // second // entry. If you // read
				 * that, it // would // be // an index out of // bound //
				 * error... the // proper // way to handle it // is // to handle
				 * it on // both // the client and // the // server, since for
				 * // commercial // product we // cannot rely on // the //
				 * client to do the // correct things
				 **/
				// System.out.println(nameMessage.length);
				if (nameMessage.length == 4) { // this catches the array of
				// len
				// 0

					if (EchoServer.history.size() == ServerConstants.MSGBATCHED) { // batch up 3
						// messages before
						// storing it in the HDD
						FileIO.outWrite(EchoServer.history, true, ServerConstants.HISTORYLOG);
						EchoServer.history.clear();// this is not part of the IO, just
						// include it here instead
					}
					tempBuffer = "<" + nameMessage[0] + ">" + " sent: ("
							+ nameMessage[1] + ") to " + "<" + nameMessage[2]
							+ ">";
					EchoServer.history.add(tempBuffer); // this is the history
					out.println(tempBuffer); // writing to sockets is just
					// like stdout
					out.println(EchoServer.history); // second line for history.
					// protocol
					// specify client to receive 2
					// lines
					EchoServer.count++;

				} else { // need to include else so the server will know how
				// to
				// handle when there is a null message -> do
				// nothing
					System.out.println("received null from " + nameMessage[0]);
					out.println("\n" + ""); // return two lines of blank

				}

				/*************
				 * without the client creating a new connection everytime, we
				 * would have to restore back to the while loop and we cannot
				 * close the socket and reader everytime anymore
				 *****/
				/*
				 * in.close(); out.close(); this.clientSocket.close();
				 * System.out.println("closing current tcp");
				 */
				// should close everything after each tcp connection, with
				// the
				// while loop implementation it uses persistent connection
				/*****************************/
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static class Sending implements Runnable { // can only lock on

		@Override
		public synchronized void run() {

		}

		

	}
	
	//public static void keyPressed(KeyEvent keyEvent) {
		// int keyCode = keyEvent.getKeyCode();
		// switch (keyCode) {
		// case KeyEvent.VK_UP:
		// System.out.println("// handle up ");
		// break;
		// case KeyEvent.VK_DOWN:
		// // handle down
		// break;
		// case KeyEvent.VK_LEFT:
		// // handle left
		// break;
		// case KeyEvent.VK_RIGHT:
		// // handle right
		// break;
		// }
		// }
}




