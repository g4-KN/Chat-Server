package serverandclient;

public class README {

	/***
	 * 
	 * kept the server as stateless as possible. so that if it even goes down,
	 * the service can be resume easily (by restarting the server) and my
	 * components don't need to depend on each other
	 * 
	 * 
	 * To Setup:
	 * 
	 * 1. create a proj with a package named "serverandclient"
	 * 
	 * 2. place the clone repo in there
	 * 
	 * 3. 3. you should be setup and ready to go :D
	 * 
	 ************************************************************************* 
	 * 
	 * To Use:
	 * 
	 * BASIC SUMMARY
	 * 
	 * 1. run the MainRunner class's main method.
	 * 
	 * 2. connect your client to the same IP as the server is using
	 * 
	 * 3. to setup users -> type in "add" after the server is running and then
	 * followed by your username, password ->this user will be ready as it is
	 * added to the hashMap in memory (setup is required for client to sign in
	 * its user)
	 * 
	 * 5. to save the setup users -> type in "save" -> this will create a
	 * userList.txt file in your home dir -> you can retrieve this list every
	 * time running the server by typing in "list"
	 * 
	 * 6. to view the setup users running in the system -> type in "map" ->
	 * since it is stored in a hashMap
	 * 
	 * 7. to delete a user in the system hashMap -> type in "remove" and then
	 * followed by the username to be deleted
	 * 
	 * 8. there are other command lines to monitor traffic and administer over
	 * users and passwords -> type in help for more information -> HAPPY
	 * EXPLORING
	 * 
	 * NOTE: history only display up to the last 5 messages
	 * 
	 * 
	 * 
	 ************************************************************************** 
	 * 
	 * Overview:
	 * 
	 * This is mostly a prove of concept project - the server itself is actually
	 * just relaying messages (with a miniClient in it) to the intended receiver
	 * - this effectively makes it a mini Proxy.
	 * 
	 * 
	 * This project was originally intended to provide simple directional chat
	 * service, but I have included many other features including:
	 * 
	 * # Authentication - an Authentication System (like a mini GAS???): user
	 * has to get authenticated before using the service. - I have simplified
	 * the hashing by using the java hashCode() plus a salt
	 * 
	 * # CommandRunnable - a platform to setup users and to monitor the system
	 * (like a mini Control Center???): this allows the admin of the server to
	 * dynamically monitor and interact with it (eg: add users, delete users,
	 * view history, logs...)
	 * 
	 * # FileIO - to provide logging - (like a mini Archive???): messages and
	 * history are being logged plus saving up pre-configured arguments that can
	 * be fed into my CommandRunnable (admin)
	 * 
	 * # UDPServer/Client - to provide a secondary way to communicate
	 * (functional but not in use yet)
	 * 
	 * # The meat of the chat service lies in the other components of the server
	 * 
	 * + EchoServer - is the starter of the server
	 * 
	 * + IndividualConnections - provides a one time TCP connection (used mostly
	 * for the current solution)
	 * 
	 * + Persistent Connections - provides a persistent TCP connection that
	 * doesn't immediately close the socket (plan to switch to this solution
	 * later)
	 * 
	 * + SimpleClient - provides a way to send message to the realClient (we
	 * currently don't hold onto the TCP connection after we use it that why we
	 * need this simpleClient to send message back to the real client)
	 * 
	 ************************************************************************** 
	 * 
	 * 
	 * 
	 */
}
