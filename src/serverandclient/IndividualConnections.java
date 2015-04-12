package serverandclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IndividualConnections implements Runnable { // can only lock
															// on

	// try to use default constructor for the executor object -> regular obj
	/*
	 * private final ServerSocket serverConnection; private ServerSocket
	 * serverSocket;
	 * 
	 * public Connections(ServerSocket serverSocket2) { this.serverSocket =
	 * serverSocket2; }
	 */

	private Authentication checkUser = new Authentication();
	private Long uuid = UUID.randomUUID().getMostSignificantBits();
	private Socket clientSocket = null; // init the var first -> and then
										// modify it later, this way the
										// scope of the var is wider

	public IndividualConnections(Socket clientSocket) {
		this.clientSocket = clientSocket;

	}

	@Override
	public synchronized void run() {

		System.out.println("worker thread started with Thread: "
				+ Thread.currentThread().getName());
		// syserr will color code it to red to make the output easier to be
		// identified

		// make Authentication so that you cannot instantiate it.
		// each new connection, need to load the users into the map

		try {
			Authentication.getList("userList.txt");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (EchoServer.args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		// only one service can be reading from the port at a time (server),
		// but
		// any number of clients plus the server can write to it.

		int portNumber = Integer.parseInt(EchoServer.args[0]);
		String tempBuffer = "";
		List<String> addrPortTo = new ArrayList<String>();

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

			// Runnable sendRunnable = new Sending();
			// Thread sending = new Thread(sendRunnable);

			System.out
					.println("client from "
							+ this.clientSocket.getLocalAddress()
									.getHostAddress()
							+ " has joined the server and connection is now up and running");
			inputLine = in.readLine();
			/***************
			 * without embedding this in the while, need to explicitly call it
			 ************/

			// the raw dump of what my server received
			FileIO.outWrite(inputLine, true, ServerConstants.RAWLOG);

			// while ((inputLine = in.readLine()) != null) {

			/**
			 * this is executed // once the user // press enter // ... // won't
			 * just // keep // running the // inside loop // :D - // it is like
			 * // interrupts. // This // model is not // ideal as it // would //
			 * only allow // one // client socket // to // write to it. // For
			 * // multi // connections, // use // event loop // mode, // which
			 * creates // a // connectionHandler // class to run // on // its
			 * separate // thread.
			 **/

			nameMessage = inputLine.split(ServerConstants.ESCCHAR);

			// whitelist or authentication check before doing anything with the
			// message

			tempBuffer = (parsingCheck(nameMessage));
			System.out.println(tempBuffer);
			if (tempBuffer.equals("????ADDINGUSER????")) {
				if (checkUser.checkPassword(nameMessage[0], nameMessage[2])) {
				// replace with a uuid later
					System.out.println(nameMessage[0]
							+ " is Authenticated and session " + uuid
							+ " assigned to him");
					out.println(nameMessage[0]
							+ "$$true$$");
					out.println(uuid);
					// save that to a hash map of active users or set a flag to
					// indicate that the user is online
				}
				//add in rate limited feature???
				else{
					out.println("wrong password please try again");
					out.println("0");
				}
			} else if (appliances(nameMessage)){
				EchoServer.history.add(tempBuffer);
				out.println(tempBuffer+" SUCCESS");
				out.println("SUCCESS"); // second line mandatory for "my client"
													// protocol
				EchoServer.count++;
			}else {
				EchoServer.history.add(tempBuffer); // this is the history
				out.println(tempBuffer);
				out.println(EchoServer.history); // second line for history.
													// protocol
				// specify client to receive 2 lines

				EchoServer.count++;
			}

			/******************* sending mechanism *******************/
			
			/*			
			// sending.start();

			addrPortTo
					.add(this.clientSocket.getLocalAddress().getHostAddress());
			// this would only keep one instance of the receiver return
			// addr:port
			// only the last instance of the user who last use the service
			// will receive msg's now if there are multi session

			// TODO if i want multi session. I would need to use the old
			// hashMap
			// I made before it have duplicate user instances

			// remove users once they close their session?
			addrPortTo.add(nameMessage[3]); // this is their miniserver:port
			// if (usersMap.containsKey(nameMessage[2]) == false) {
			// this can be used to implement multi sessions
			// } else
			// System.out.println("sender already in the map");

			List<String> receiverAddrPort = new ArrayList<String>();
			EchoServer.addrMap.put(nameMessage[0], addrPortTo);
			if (EchoServer.addrMap.containsKey(nameMessage[2])) {
				System.out.println("receiver found");
				receiverAddrPort = EchoServer.addrMap.get(nameMessage[2]);
				System.out.println("getting values from hash map: "
						+ receiverAddrPort.get(0) + " "
						+ receiverAddrPort.get(1));
				// forwarding message to receiver
				if (tempBuffer.equals("????ADDINGUSER????")) {
					System.out
							.println("not relaying msg, it is an attempt for authentication");
				} else {
					SimpleClient.startClient(nameMessage,
							receiverAddrPort.get(0),
							Integer.parseInt(receiverAddrPort.get(1)));
				}

			} else
				System.out.println("receiver not there");*/

			/*************
			 * without the client creating a new connection everytime, we would
			 * have to restore back to the while loop and we cannot close the
			 * socket and reader everytime anymore
			 *****/
//			in.close();
//			out.close();
//			this.clientSocket.close(); // if i close the client socket here,
//										// does it mean my client needs to
//										// open new connection to send new
//										// message? do i need to close the
//										// client socket on the client side
//										// as well?
//			System.out.println("closing current tcp");
			// should close everything after each tcp connection, with the
			// while loop implementation it uses persistent connection
			/*****************************/
			// }
			
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

	public static String parsingCheck(String[] nameMessage) throws Exception {
		String tempBuffer = "";

		/**
		 * escape char - if the // message is null // then // that would return
		 * // an // array of length 0 // to // the second entry // of // the
		 * array = no // second // entry. If you // read that, it // would // be
		 * // an index out of // bound // error... the // proper // way to
		 * handle it // is // to handle it on // both // the client and // the
		 * // server, since for // commercial // product we // cannot rely on //
		 * the // client to do the // correct things
		 **/
		// System.out.println(nameMessage.length);
		/****************** receiving mechanism *****************************/
		if (nameMessage.length == 4) { // this catches the array of
										// len
										// 0

			if (EchoServer.history.size() >= ServerConstants.MSGBATCHED) { // batch
																			// up
																			// 3
				// messages before ... just to
				// be safe use >= a more
				// generous bound
				// storing it in the HDD
				FileIO.outWrite(EchoServer.history, true,
						ServerConstants.HISTORYLOG);
				EchoServer.history.clear();// this is not part of the IO, just
				// include it here instead
			}

			if (nameMessage[1].equals("????ADDINGUSER????")) {
				// this is done for each new client
				System.out.println("GOT user " + nameMessage[0]
						+ " into the Map");
				tempBuffer = "????ADDINGUSER????";
				return (tempBuffer);
			} else {
			
				tempBuffer = "<" + nameMessage[0] + ">" + " sent: (" // this is
																		// the
																		// payload
						+ nameMessage[1] + ") to " + "<" + nameMessage[2] + ">";

				return (tempBuffer); // writing to sockets is just
				// like stdout
			}

		}
		// if (nameMessage.length == 3){ // if the client sends me 3 arguments
		// it means that is just to get the user into the Map
		// }
		else { // need to include else so the server will know how
				// to
				// handle when there is a null message -> do
				// nothing
			System.out.println("received null from " + nameMessage[0]);
			return ("\n" + ""); // return two lines of blank

		}
	}
	
	public static boolean appliances(String[] nameMessage) throws Exception {
		
		if (nameMessage[1].equals("Livingroom light")){
			//carry out the action here
			return true;
		}else if (nameMessage[1].equals("Bathroom light")){
			return true;
		}else if (nameMessage[1].equals("Kitchen light")){
			return true;
		}else if (nameMessage[1].equals("Bedroom light")){
			return true;
		}else if (nameMessage[1].equals("light")){
			return true;
		}
		else {
		
			return false;
		}
		
	}
}
