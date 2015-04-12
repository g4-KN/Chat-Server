package serverandclient;

// ======================
// === Constants Classes ===
// ======================

public final class ServerConstants {
	private ServerConstants() {
	};

	public final static int MAXCLIENTNUM = 10;
	public final static int PACKETSIZE = 3; // how many pieces per packet...
											// userName, msg, userTo
	public final static int MSGBATCHED = 5;
	public final static String HISTORYLOG = "history.txt";
	public final static String RAWLOG = "rawLog.txt";
	public final static String ESCCHAR = "//";
	
}
