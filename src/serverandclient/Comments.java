package serverandclient;

public class Comments {

	/***************************************** DONE **************************************/

	// TODO: bug fix- when I go fast, I get a bunch of race condition with IO

	//
	//
	// <<tt>> sseenntt:: ((llooaadd) to <)t >t
	// o
	// < g<>t >s
	// e
	// <ngt: (>l osaedn)t :t o( l<oga>d
	// )
	// < tt>o s<egn>t
	// :
	// <(tl>o asde)n tt:o (<lto>a
	// d
	// )< gt>o s<etn>t
	// :
	// <(gl>o asde)n tt:o (<lgo>a
	// d
	// ) to <g>
	//
	// sent: (load) to <g>

	// after the fix
	// <t> sent: (load) to <t>
	// <t> sent: (load) to <t>
	// <k> sent: (load) to <k>
	// <t> sent: (load) to <t>
	// <k> sent: (load) to <k>
	// <t> sent: (load) to <t>
	// <k> sent: (load) to <k>
	// <t> sent: (load) to <t>
	/**************************************** TO_GET_DONE ***************************************************/

	/**
	 * Right now we have no concept of who is online and who is offline. modify
	 * the addrMap to take in another flag and the session (this is for
	 * security) on the value side to indicate that or just switch entirely to
	 * Persistent TCP so if we have an active connection or session then we know
	 * who is on and who is off
	 */
	// Function pass return value back is just the slow way to do thing
	// You make 2 allocations
	// if you just change the value of the reference then the copy in your main
	// program will get mod too
	// 1 alloc vs 2 alloc

	// debug log is to override the ordinary sysout method and then can set
	// flags or tags there

	// idle for long time and i get :
	// client has established a connection with the real server
	// received: GET / HTTP/1.0
	// Exception in thread "Thread-1" java.lang.ArrayIndexOutOfBoundsException:
	// 2
	// at SimpleServer.processClientRequest(SimpleServer.java:91)
	// at SimpleServer.run(SimpleServer.java:54)
	// at java.lang.Thread.run(Thread.java:722)

	// DANGEROUS to leave port open

	// Socket[addr=/10.5.5.10,port=38014,localport=9990]
	// worker thread starting
	// worker thread started
	// client from 10.5.6.195 has joined the server and connection is now up and
	// running
	// java.lang.ArrayIndexOutOfBoundsException: 3
	// at testCode.EchoServer$Connections.run(EchoServer.java:440)
	// at
	// java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1110)
	// at
	// java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:603)
	// at java.lang.Thread.run(Thread.java:722)
	// 71 69 84 32 47 32 72 84 84 80 47 49 46 48 received null from GET /
	// HTTP/1.0
	//
	//
	// Socket[addr=/10.5.5.10,port=38233,localport=9990]
	// worker thread starting
	// worker thread started
	// client from 10.5.6.195 has joined the server and connection is now up and
	// running
	// 72 69 76 80 received null from HELP
	//
	//
	// java.lang.ArrayIndexOutOfBoundsException: 3
	// at testCode.EchoServer$Connections.run(EchoServer.java:440)
	// at
	// java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1110)
	// at
	// java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:603)
	// at java.lang.Thread.run(Thread.java:722)

	// TODO: add a hash map to store pair of user name and its return address
	// (DONE)
	// CLEAN UP THE CODE AND CLIENT (client send out a dummy message to get its
	// name in the map when it first join)
	// modularize the components - one (big/small) responsibility per
	// class/method
	// eliminate the first message to get the user into the hashmap. after they
	// type in the user name automatically send it.
	// server echo to the sender after it sends out the message instead of
	// before

	// send a response back to the sender after the message has been
	// successfully delivered to the receiver - this custom ack will be done in
	// a way that it holds onto the tcp connection and put a time out if not ack
	// receive after message sent?
	// use HTTP
	// create a text file that stores the raw message sent - it will be HTTP
	// format in the future

	// add a cmd users to dump all the users in the database
	// flags and tags are good tools to organize and modularize stuff. (can trun
	// on or off flags to select the level of printing we have)
	// clean up and remove un-neccessary sysout - client type message display
	// everytime after entered is annoying.
	// learn the OSI model

	// create a git hub for this
	// make a simple data base to store the user info.
	// add in java simple gui for my client

	// HOW COME GR CODE HAS VERY LITTLE COMMENTS - opposite from school?
	// production code should be clean and self-explanatory
	// how can I become a better programmer? From what you can see, what are my
	// weaknesses? the private,public,static.... set the types right
	// I think I implement functional programs, but to make it secure and giving
	// structure to it is what I am lacking
	// learn the structure to do things right, the structure that have been
	// developed for decades.
	// That's right!!!!
	// scared to lose code, so i just keep commenting them out. Is that bad
	// practice? when commit clean it up. while working it is fine

}
