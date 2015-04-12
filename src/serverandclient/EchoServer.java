package serverandclient;

import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
import java.io.*;


/**
 * trying to keep my server as stateless as possible, so that my components dont
 * need to depend on each other
 * 
 * 
 * for echo mode.I can create new thread and close it every time i receive a
 * 
 * 
 * client request. no need to keep each thread persistent in a while loop for
 * sender and receiver mode. I need to keep the connection persistent so that I
 * can write stuff back to the client (need to wait for the client to request
 * for a connection and then hang on to it - server cannot make a request to
 * client explicitly [cannot say which IP to go to; that's the client's job]) -
 * keep each connection alive and with separate thread each. - Use singleThread
 * executor and create it constantly(poor solution) - Use a threadpooled
 * executor (specify the max num of client = number of threads in the pool)
 * 
 * 
 * focus on one component at a time. This way I can be focused and productive.
 * worked on all server today - refactoring. Won't be wasting a lot of time
 * jumping around to understand code change, since the change is focused.
 * tomorrow - refactor client
 * 
 * 
 * TODO put client and server back into the same project, but under different path/group???
 * 
 * @author kenneth.ng
 * 
 */

// import testCode.JoinExample.MyRunable;

public class EchoServer {

	static String[] args = { "9990" }; // why cant this be protected

	protected static int count = 0; // only one instance of this var
	// this will be used by all the classes related/(used to be) to EchoServer
	protected volatile static Map<String, List<String>> addrMap = new HashMap<String, List<String>>();

	protected static ServerSocket serverSocket = null;
	// static ExecutorService newConnections = Executors
	// .newFixedThreadPool(MAXCLIENTNUM);
	// this server can support up to
	// MAXCLIENTNUM num of clients, if they each run in the while loop config

	protected static ArrayList<String> history = new ArrayList<>(); // in order

	private ExecutorService newConnection = Executors.newSingleThreadExecutor();
	private ExecutorService voip = Executors.newSingleThreadExecutor();
	private ExecutorService imaging = Executors.newSingleThreadExecutor();
	// Multi threaded model
	private ExecutorService cmdThread = Executors.newSingleThreadExecutor();

	
	//is it ok to keep method's as public, even though they are not being used by other classes?
	public void start() throws IOException {

		// readConfig();

		cmdThread.execute(new CommandRunnable());
		System.out.println("separate thread that checks for cmds is running");

		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		// only one service can be reading from the port at a time (server), but
		// any number of clients plus the server can write to it.

		int portNumber = Integer.parseInt(args[0]);
		System.out.println("starting server, waiting for client request");

		// EchoServer echoServer = new EchoServer(); // this is just a dummy
		// object

		// this will be TCP connection. for UDP use dump

		serverSocket = new ServerSocket(portNumber);
		System.out.println(serverSocket.getLocalSocketAddress());
		Socket clientSocket = null;
		newConnection = Executors.newCachedThreadPool(); 
		voip = Executors.newSingleThreadExecutor(); 
		imaging = Executors.newSingleThreadExecutor(); 
		
		voip.execute(new VUServer());
		imaging.execute(new ImageReceive());

		while ((clientSocket = serverSocket.accept()) != null) { // this is the
																	// connection
																	// handler/accepter

			/*
			 * try {
			 * 
			 * // create a separate thread that runs in a polling fashion to //
			 * accept // new connection
			 * 
			 * 
			 * 
			 * } catch (IOException e) { System.out
			 * .println("Exception caught when trying to listen on port " +
			 * portNumber + " or listening for a connection");
			 * System.out.println(e.getMessage()); }
			 */

			System.out.println(clientSocket);

			/************************
			 * with the thread pool executor we then dont need this new single
			 * thread every time?
			 **************/
			
			//TODO if session key check failed then you would need to be authenticated
			
			

			
			// can use the CacheThreadPool so that don't need to keep recreating Executors vs singleThreadEx
			//- they are expansive to create, much more than normal threads

			/*********************************************/
			
			//before I put my the init of newConnection here and that creates a new thread pool every time
			newConnection.execute(new IndividualConnections(clientSocket));

			// newConnections.execute(new LoopConnection(clientSocket));
			// newConnections.execute(new LoopConnection(clientSocket));
			System.out.println("worker thread starting"); // the more client the
															// more threads I
															// need to create
															// since it just
															// keeps adding to
															// the queue of the
															// singleThreadExecuter
															// and other process
															// cannot join in.
															// especially if i
															// use a while loop
															// in run method, it
															// will keep any
															// single thread
															// thread stalling
															// forever.

		}

	}

	

}
