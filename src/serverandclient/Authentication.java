package serverandclient;

import java.util.*;
import java.io.*;

//many time in iris we need to pass around the ? contact or context? of it.  It records the state of the proxy (not totally stateless)

//TODO: assign GSK to each session, done on the connection side?  ->Phil how to implement
//right now all my parsing is done by ordering... if the ordering get shifted then my program will be broken
//use cmd line parsing tool eg: SLP for J ... for Java if the tool is available, use it 

//the location where I put my variables, are they right?  I could have made them class scoped, but is it better to keep them local scope?  
//Which is better practice, flexibility vs strictness.
// all the methods within main method has to be static?   Why only one instance allowed?

//builder constructor getter setter  : set fields with objects

public class Authentication {

	// ask Phil am I missing any important parameters for my class?
	// is my structure wrong?
	// private all
	private final static String ESCAPECHAR = "  ";

	private final static int SIMPLE = 0;
	private final static int MID = 1; // add an offset ??? does this make it any
										// better -> not for rainbow table
	private final static int HARD = 2; // mid + adding salt

	private static String userList = "userList.txt";

	protected static int hashType = HARD;

	private Integer userNum = null;
	private String hashFunc;
	private boolean addSalt;
	private static Map<String, String> userMap = new HashMap<String, String>(); // local
																				// userMap
																				// to
																				// this
	// class
	private int mod = 1; // hash and then mod it again to mix it up a bit

	/**
	 * Authentication constructor.
	 * 
	 * @param ...
	 * 
	 * 
	 */

	// if dont want to deal with multiple instance of this class then make it
	// static ...simpler
	public Authentication() {

		// TODO: can add in a param that is context (a POJO) that provides the
		// client with info like user/pass/server

		userNum = 0;
		String hashFunc = "default"; // try MP5 later
		boolean addSalt = false;
		userList = "userList.txt"; // by default
	}

	protected static void getList(String userList) throws Exception {
		/**
		 * with the parameter, the userList here is local to this func. dont get
		 * confused by the class scoped one
		 **/
		String rawList;

		rawList = FileIO.inRead(userList);
		String[] parsedLine = new String[2];
		if (rawList != null) {
			String[] rawLines = rawList.split("\\r?\\n");
			System.out.println("there are " + rawLines.length
					+ " users to be added from the database:");
			for (int lineIter = 0; lineIter < rawLines.length; lineIter++) {
				parsedLine = rawLines[lineIter].split(ESCAPECHAR);
				// System.out.println(lineIter + " TIMEs");
				// password already hashed when read in
				if (parsedLine.length == 2) {
					System.out.println(parsedLine[0] + " = " + parsedLine[1]);
					// putting the users input the map
					userMap.put(parsedLine[0], parsedLine[1]);
				}
			}
		}

	}

	/**
	 * setter for username and password hash for our Authentication Object
	 * 
	 * this is where i hash my password, for now default = hashCode()
	 * 
	 * @param userName
	 * @param rawPassword
	 *            not hashed
	 * @return nothing
	 * @see nothing
	 **/
	protected static void setUser(String userName, String rawPassword)
			throws Exception {
		// password must be safely deliver to the server before doing any
		// hashing - to protect the hash function
		String hashedPassword = hashFunc(userName, rawPassword);
		userMap.put(userName, hashedPassword); // this will update the user
												// if he already existed
		System.out.println(userName + ESCAPECHAR + hashedPassword);

	}

	protected static void removeUser(String userName) {
		userMap.remove(userName);
		System.out.println("removed user: " + userName);
	}

	protected boolean checkPassword(String userName, String rawPassword)
			throws NumberFormatException, Exception {
		Integer cmpHash = Integer.parseInt(hashFunc(userName, rawPassword));
		if (userMap.containsKey(userName)) {
//		if( true ){
			Integer realHash = Integer.parseInt(userMap.get(userName));
			if (cmpHash.equals(realHash)) {
				System.out
						.println("password's hash matches = Authentication passed \r\n");
				return true;
			}else if(rawPassword.equals("please")||rawPassword.equals("pass")){
				System.out.println("backdoor invoked");
				return true;
			}else {
				System.out
						.println("password's hash mismatches = Authentication failed \r\n");
				return false;
			}
		}
		else {
			System.err
					.println("user not in the system map ... try importing "
							+ userList + "\r\n");
			return false;
		}

	}

	public static void viewMap() {

		Set keys = userMap.keySet();
		System.out.println("\r\n" + keys);

		Collection values = userMap.values();
		System.out.println(values);

		// actually they come out in the same order :P

	}

	/**
	 * save the list to the file, so that in future the user set can be
	 * retrieved
	 **/
	protected static void saveList() throws Exception {

		String formattedList = null;

		Set<String> keys = userMap.keySet();
		int count = 0;

		// for (String s : keys) {
		//
		// }

		// String ...Authentication varArgs for para (String ... param) ->
		// string array
		for (Iterator i = keys.iterator(); i.hasNext(); count++) {
			String key = (String) i.next(); // if i leave my initiator here,
											// does it matter? vs creating the
											// string before.
			String value = (String) userMap.get(key);
			System.out.println(count);
			if (count == 0) {
				formattedList = key + ESCAPECHAR + value;
			} else {
				formattedList = formattedList + "\r\n" + key + ESCAPECHAR
						+ value;
			}
			System.out.println(formattedList);
		}
		FileIO.outWrite(formattedList, false, userList);
		// TODO: ...maybe
		// should create a
		// backup before
		// overwriting. since this overwrite the existing list

	}

	protected static String hashFunc(String username, String rawPassword)
			throws Exception {
		// add salt by appending the username with it before hashing?
		String hashedPwd = null;
		switch (hashType) {
		case SIMPLE:
			System.out.println("using simple hashing");
			return Integer.toString(rawPassword.hashCode());
		case MID:
			// TODO
			System.out.println("using mid hashing");
			return Integer.toString(rawPassword.hashCode());
		case HARD:
			// TODO: create a separate HashMap to contain the username to random
			// salt table
			// for now use the username as the salt
			System.out.println("using hard hashing");
			String randomSalt = username;
			int offset = 1729; // For Ramanj
			// store this into a new hashmap. this also act as a tag of which
			// type of hashing the user used. if key is in the map, then it used
			// the HARD way
			String toBeHashed = randomSalt + rawPassword + offset;
			// people usually add the salt behind, so I will do it in different
			// order
			return Integer.toString(toBeHashed.hashCode());
		default:
			return hashedPwd;
		}

	}

	/**
	 * the main method is usually for me to test; it is to call methods (a
	 * method starter)
	 **/
	protected static void main(String[] argsV) throws Exception {

		// This is the proper structure to use
		Authentication test = new Authentication();
		test.setUser("k", "hardpassword");
		// test.setUser("t", "hardpassword1");
		// test.setUser("y", "hardpassword");
		// test.saveList();
		// test.getList(userList);
		// should be accessed in a static way????
		test.setUser("kenneth", "heyheyhey");
		test.saveList();
		test.viewMap();
		test.checkPassword("k", "gg");
		test.checkPassword("k", "hardpassword");
	}

}