package serverandclient;

public class MainRunner {
	
	public static void main(String[] argsV) throws Exception {

		EchoServer myServer = new EchoServer(); // this way I can have multiple
												// instances of the server
												// running
		myServer.start();

	}

}
