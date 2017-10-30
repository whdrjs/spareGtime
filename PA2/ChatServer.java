package PA2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * A multithreaded chat room server.  When a client connects the
 * server requests a screen name by sending the client the
 * text "SUBMITNAME", and keeps requesting a name until
 * a unique one is received.  After a client submits a unique
 * name, the server acknowledges with "NAMEACCEPTED".  Then
 * all messages from that client will be broadcast to all other
 * clients that have submitted a unique screen name.  The
 * broadcast messages are prefixed with "MESSAGE ".
 *
 * Because this is just a teaching example to illustrate a simple
 * chat server, there are a few features that have been left out.
 * Two are very useful and belong in production code:
 *
 *     1. The protocol should be enhanced so that the client can
 *        send clean disconnect messages to the server.
 *
 *     2. The server should do some logging.
 */
public class ChatServer {
	/**
	 * The port that the server listens on.
	 */
	private static final int PORT = 9001;	//port number
	/**
	 * The set of all names of clients in the chat room.  Maintained
	 * so that we can check that new clients are not registering name
	 * already in use.
	 */
	private static HashSet<String> names = new HashSet<String>();	//stored names, no duplicated.
	/**
	 * The set of all the print writers for all the clients.  This
	 * set is kept so we can easily broadcast messages.
	 */
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();	//stored each thread outpur stream.
	/**
	 * The appplication main method, which just listens on a port and
	 * spawns handler threads.
	 */	

	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running.");
		ServerSocket listener = new ServerSocket(PORT);	//create socket
		try {
			while (true) {
				new Handler(listener.accept()).start();	//Create new threads, include socket
			}
		} finally {
			listener.close();	//close socket
		}
	}

	/**
	 * A handler thread class.  Handlers are spawned from the listening
	 * loop and are responsible for a dealing with a single client
	 * and broadcasting its messages.
	 */
	private static class Handler extends Thread { //Inherit Thread class
		private String name;
		private Socket socket;
		private BufferedReader in;	//receive to client
		private PrintWriter out;	//respond to client

		/**
		 * Constructs a handler thread, squirreling away the socket.
		 * All the interesting work is done in the run method.
		 */
		public Handler(Socket socket) {
			this.socket = socket;	//Initialization socket in Handler class
		}

		/**
		 * Services this thread's client by repeatedly requesting a
		 * screen name until a unique one has been submitted, then
		 * acknowledges the name and registers the output stream for
		 * the client in a global set, then repeatedly gets inputs and
		 * broadcasts them.
		 */
		public void run() { //overidding from Thread
			try {
				// Create character streams for the socket.
				in = new BufferedReader(new InputStreamReader(		//receive message for coming client using socket
						socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);//send to message for client using socket

				// Request a name from this client.  Keep requesting until
				// a name is submitted that is not already used.  Note that
				// checking for the existence of a name and adding the name
				// must be done while locking the set of names.
				while (true) {
					out.println("SUBMITNAME"); //receive client, in order that submit name
					name = in.readLine();	//read to client
					if (name == null) {
						return;
					}
					synchronized (names) {//To synchronized, prevent to each object(socket) use data same time.
						if (!names.contains(name)) { 	//check name, already have.
							names.add(name);	//store name
							for (PrintWriter writer : writers) {								//if new user come into server,the other users(original users)-
								writer.println("MESSAGE "+"<System> " + name + " in to room"); // - receive to server message that new user into server.
							}
							break;	//be stored name
						}
					}
				}

				// Now that a successful name has been chosen, add the
				// socket's print writer to the set of all writers so
				// this client can receive broadcast messages.
				out.println("NAMEACCEPTED"+"<System> Success in to room"); // when user client success name accessing, send to client 
				writers.add(out);	//thread's writer get output stream.
				// Accept messages from this client and broadcast them.
				// Ignore other clients that cannot be broadcasted to.
				while (true) {
					String input = in.readLine();	//read and stored in 'input' each clients message
					if (input == null) {
						return;
					}
					else if (input.startsWith("<")&&input.contains("/>")) {//if client's message format is whisper message
						int idep;
						idep=input.indexOf("/>");	//find index for finding be sent to whisper user.
						String wname=input.substring(1, idep); //stored that be sent to whisper user name.
						input=input.substring(idep+2);	//split user name(whispered) and message
						out.println("MESSAGE "+"<Priviate> " + name + ": " + input);	//show sender whisper message
						for (PrintWriter writer : writers) {
							writer.println(wname+"<Priviate> " + name + ": " + input);	//send to all client name, be sent to whisper 
						}																//user name and private signal (<Priviate>) with message.
					}
					else {
						for (PrintWriter writer : writers) {	//sent to message( for each loop)
							writer.println("MESSAGE " + name + ": " + input);
						}
					}
				}
			} catch (IOException e) {
				System.out.println(e);               
			} finally {
				// This client is going down!  Remove its name and its print
				// writer from the sets, and close its socket.
				for (PrintWriter writer : writers) {									//if user out to server, server send to message that who out the server-
					writer.println("MESSAGE "+"<System> " + name + " out to the room");	//-for other clients. 
				}
				if (name != null) {
					names.remove(name); //delete out user name(prevent new user doesn't use name)
				}
				if (out != null) {
					writers.remove(out); //delete out user's output stream
				}
				try {
					socket.close();	//close socket in class.
				} catch (IOException e) {
				}
			}
		}
	}
}