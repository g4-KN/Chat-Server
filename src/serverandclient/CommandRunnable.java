package serverandclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//TODO connect the var like history used inbetween CMDRunnable and IndividualConnections: including usersMap, history,

public class CommandRunnable implements Runnable {

	/**
	 * 
	 * 
	 * This is having the function of CC or SM - the admin - control users
	 * 
	 * TODO: add in system message support to all the users connected
	 * (broadcast)
	 * 
	 * 
	 * can only lock on object and execute an object that implements the
	 * RUnnable interface private final EchoServer echoServer;
	 * 
	 * public MyRunable(EchoServer echoServer) { this.echoServer = echoServer; }
	 **/

	// try to use default constructor for the executor object -> regular obj

	private BufferedReader stdIn = new BufferedReader(new InputStreamReader(
			System.in));

	// Authentication is like the whitelist of proxy

	public CommandRunnable() {

	}

	@Override
	public synchronized void run() {

		try {
			Authentication.getList("userList.txt");
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		System.out.println("type in help for list of cmds");

		// make a cmd to retrieve history from the server side with a
		// separate thread
		/*
		 * Frame frame = new Frame(); TextField text = new TextField();
		 * frame.add(text); frame.pack(); frame.setVisible(true); KeyEvent
		 * keyEvent = new KeyEvent(frame, KeyEvent.KEY_TYPED, 0, 0,
		 * KeyEvent.VK_UNDEFINED, 'H');
		 */
		for (;;) {
			String userInput = null;
			try {
				userInput = stdIn.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// different cmd's
			switch (userInput) {
			case "help":

				// active= with the users who have active session ... will
				// implement that soon
				System.out
						.println("monitor: history, count, all, clear, vfile \r\n"
								+ "admin: active, add, remove, list, map, kick, kill, save, change, sysmsg \r\n");
				break;
			case "restart": // dont know how to get it to work yet
				try {
					EchoServer.serverSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// dont know the proper way to restart it - can create a new
				// EchoServer object.
				System.out.println("");
				break;
			case "count":
				System.out.println("received " + EchoServer.count
						+ " messages since the server is up");
				break;
			case "history":
				System.out.println(EchoServer.history);
				break;
			case "active":

				break;
			case "all":
				try {
					String temp = FileIO.inRead(ServerConstants.HISTORYLOG);

					// int i = 0;
					// System.out.println(temp.length());
					// passing back a String that is contains all of the
					// buffer and with the escape chars.
					// pause the printing when it hits the escape chars.
					// just a fun way to do things... hehehe but not
					// efficient

					// for (i = 0; i < temp.length(); i++) {
					// if (temp.charAt(i) == 'z'
					// && temp.charAt(i + 1) == '#'
					// && temp.charAt(i + 2) == '$') {// my escape
					// // string is
					// // "z#$"
					// System.out.println("break at i = " + i);
					// break; // this skip the iteration once?...no
					//
					// }
					// System.out.print(temp.charAt(i));
					//
					// }
					//
					// System.out.println("after loop at i = " + i);

					System.out.println(temp);

					System.out.println("\r\n" + temp.length());

				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			case "raw":
				String raw;
				try {
					raw = FileIO.inRead(ServerConstants.RAWLOG);

					System.out.println(raw);
					System.out.println("\r\n" + raw.length());
				} catch (Exception e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				break;
			case "vfile":
				try {
					System.out
							.println("Please enter the name of the file that you want to lookup (eg: XXX.txt)");
					String fileName = stdIn.readLine();
					String temp = FileIO.inRead(fileName);
					System.out.println(temp);
					System.out.println("\r\n" + temp.length());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
				}
				break;
			case "user":
				// TODO: users that are in the currently connected.... need to
				// implement the add or remove of user when they join or exit
				// session; also single sign out
				System.out.println(EchoServer.addrMap.keySet());
				break;
			case "clear":

				ArrayList<String> clear = new ArrayList<>(); // if u set it
				// to null
				// instead of new
				// ArrayList<>(), you would
				// get clear can only be
				// null at this location
				clear.add("");
				try {
					FileIO.outWrite(clear, false, ServerConstants.HISTORYLOG);
					System.out.println("history log cleared");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "add":
				try {
					System.out.println("please enter username to add:");
					String userName = stdIn.readLine();
					System.out.println("please enter password:");
					String rawPassword = stdIn.readLine();
					Authentication.setUser(userName, rawPassword);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "remove":
				// Implement that in authentication
				try {
					System.out.println("please enter username to remove:");
					String userName = stdIn.readLine();
					Authentication.removeUser(userName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				break;
			case "list":
				try {
					Authentication.getList("userList.txt");
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				break;
			case "map":
				Authentication.viewMap();
				break;
			case "change":
				// to change hash type
				if (Authentication.hashType == 0) {
					Authentication.hashType = 2;
					System.out.println("change to hard hash");
				} else if (Authentication.hashType == 2) {
					Authentication.hashType = 0;
					System.out.println("change to easy hash");
				}
				break;
			case "kick":
				// kick all users off the map - run time
				break;
			case "kill":
				// destroy the database?
				// try {
				// EchoServer.serverSocket.close();
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				break;
			case "save":
				try {
					Authentication.saveList();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			default:
				System.out.println("no such cmd");

			}
			// this is to get the last cmd

			// keyPressed(keyEvent);

			// the print statement proves that the sleep is useless, since it is
			// waiting for input

			// try {
			// System.out.println("per second");
			// Thread.sleep(1000); // polls once per second
			// } catch (InterruptedException e) {
			//
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}

	}
}
