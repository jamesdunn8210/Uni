package chat_client;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * The server that can be run both as a console application or a GUI
 */
public class Chat_Server {

	// an ArrayList to keep the the Clients
	private ArrayList<ClientThread> al;

	private int port = 1500;
	private boolean keepGoing;
	
	
	public Chat_Server() {
            al = new ArrayList<>();
	}
        
        public static void main(String[] args) {
            
            Chat_Server server = new Chat_Server();
            server.start();
        }

	public void start() {
		keepGoing = true;
		/* create socket server and wait for connection requests */
		try 
		{
			ServerSocket serverSocket = new ServerSocket(port);
			//wait for connections
			while(keepGoing) 
			{
				display("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();
                               
				ClientThread t = new ClientThread(socket);  // make a thread of it
				al.add(t);									// save it in the ArrayList
				t.start();
			}
	
		}
		catch (IOException e) {
            
		}
	}		
        
	private void display(String msg) { //to catch errors
                System.out.println(msg);
	}
	
	private synchronized void broadcast(String toUser, String message) {

        for (int i = 0; i < al.size(); i++) {

            ClientThread ct = al.get(i);

            if (ct.username.equals(toUser)) {
                ct.writeMsg(message);
            }
        }

    }
        

	/* new thread will run for each client */
	class ClientThread extends Thread {
		Socket sock;
		ObjectInputStream fromClient;
		ObjectOutputStream toClient;
                
		String username;
		ChatMessage cm;

            public ClientThread(Socket socket) {

                this.sock = socket;

                display("Thread trying to create Object Input/Output Streams");
                try {
                    toClient = new ObjectOutputStream(socket.getOutputStream());
                    fromClient = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    display("Exception creating new Input/output Streams: " + e);
                    return;
                }
                try {
                    this.username = (String) fromClient.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Chat_Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

		public void run() {
			// to loop until LOGOUT
			boolean keepGoing = true;
                        
			while(keepGoing) {
				// read a String (which is an object)
				try {
					cm = (ChatMessage) fromClient.readObject();
				}
				catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;
				} catch (ClassNotFoundException ex) {
                                Logger.getLogger(Chat_Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
				
				// the messaage part of the ChatMessage
				String message = cm.getMessage();
                                String fromUser = cm.getUser();
                                String toUser = cm.getFriend();

				broadcast(toUser, fromUser + ": " + message);

			}	

		}
		
		private boolean writeMsg(String msg) {
			if(!sock.isConnected()) {
				return false;
			}
			try {
				toClient.writeObject(msg);
			}
			catch(IOException e) {
				display("Error sending message to " + username);
				display(e.toString());
			}
			return true;
		}
	}
}

