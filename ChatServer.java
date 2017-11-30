package spareTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Vector;

public class ChatServer {
   
    private static HashSet<String> names = new HashSet<String>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    Vector<Handler> vc;
    public ChatServer(int port) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(port);
        vc = new Vector<Handler>();
        try {
            while (true) {
                Handler hd = new Handler(listener.accept());
                hd.start();
                vc.add(hd);
            }
        } finally {
            listener.close();
        }
    }
    public void removeClient(Handler hd){
    	// Remove client to vector.
    	  vc.remove(hd);
    	 }
    private class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            for (PrintWriter writer : writers) {								//if new user come into server,the other users(original users)-
								writer.println("MESSAGE "+"<System> " + name + " in to room"); // - receive to server message that new user into server.
							}
                            break;
                        }
                    }
                }
                out.println("NAMEACCEPTED");
                writers.add(out);

                while (true) {
                	int i;
                    String input = in.readLine();
                    if (input == null) {
                    	System.out.println("1");
                        return;
                    }
                    else if (input.startsWith("<")&&input.contains("/>")) {
                    	System.out.println("2");
                    	i=input.indexOf("/>");
                    	String reader=input.substring(1,i);
                    	Handler rd=findThread(reader);
                    	input=input.substring(i+2);
                    	rd.out.println("WHISPER" + name + ": " + input);
                    	out.println("WHISPER" + name + ": " + input);
                    	continue;
                    }
                    else {
                    	System.out.println(""+input);
                    	for (PrintWriter writer : writers) {
                    		writer.println("MESSAGE " + name + ": " + input);
                    	}
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } 
            finally {
            	for (PrintWriter writer : writers) {									//if user out to server, server send to message that who out the server-
					writer.println("MESSAGE "+"<System> " + name + " out to the room");	
            	}
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                removeClient(this);
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        public Handler findThread(String name){
        	//If client want to whisper, find client to whisper.
        	Handler hd = null;
        	   for (int i = 0; i < vc.size(); i++) {
        		   hd = vc.get(i);
        		   if(hd.name.equals(name)) break;
        	   }
        	   return hd;
        }
    }
    public static void main(String[] args) throws Exception {

    }
}
