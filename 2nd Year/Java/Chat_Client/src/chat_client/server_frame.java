/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */
public class server_frame
{
    ArrayList<String> OnlineUsers = new ArrayList<>();   
    ArrayList<ClientHandler> clientThreads = new ArrayList<>();
    boolean keepGoing;
   
    public static void main(String args[]) 
    {
        server_frame mainserver = new server_frame();
        mainserver.start();    
        
    }
    
    ArrayList setupUser(String Username) {
        dbConnect a = new dbConnect();
        ArrayList b;
        b = a.GetUserDetails(Username);
        

        return b;
    }

   public class ClientHandler extends Thread	
   {
        Socket sock;
        ObjectInputStream fromClient;
        ObjectOutputStream toClient;		
        String username;

       public ClientHandler(Socket clientSocket) 
       {   
           this.sock = clientSocket;
           
           System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				toClient = new ObjectOutputStream(clientSocket.getOutputStream());
				fromClient  = new ObjectInputStream(clientSocket.getInputStream());
				// read the username
				username = (String) fromClient.readObject();
                                OnlineUsers.add(username);    
			}
			catch (IOException e) {
				System.out.println("Exception creating new Input/output Streams: " + e);
			} catch (ClassNotFoundException ex) {
                Logger.getLogger(server_frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() //displays online users
       {
           boolean running = true;
           try {
               toClient.writeObject(setupUser(username));
               broadcast();
               String c;
               while (running) {

                    c = fromClient.readObject().toString();
                    if(c.startsWith("privateChat"))
                    {
                        privateChat(c);
                    }
                    else if (c.startsWith("publicChat"))
                    {
                        publicChat(c);
                    }
                    else if(c.startsWith("sendRequest"))
                    {
                        sendRequest(c);
                    }
                    else if(c.startsWith("addFriend"))
                    {
                        addFriend(c);
                    }
                    else if(c.startsWith("Disconnect"))
                    {
                        remove(this.username);
                        broadcast();
                    }

               }

           } catch (IOException | ClassNotFoundException ex) {
               Logger.getLogger(server_frame.class.getName()).log(Level.SEVERE, null, ex);
           }

       }

        void UpdateOnlineMembers()
        {
            try {
                toClient.writeObject(OnlineUsers);
                toClient.flush();
                toClient.reset();
                System.out.println("Sent online users to " + this.username + " " + OnlineUsers.toString());
            } catch (IOException ex) {
                Logger.getLogger(server_frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private boolean writeMsg(String msg) {
			// if Client is still connected send the message to it
			if(!sock.isConnected()) {
				//close();
				return false;
			}
			// write the message to the stream
			try {
				toClient.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				System.out.println("Error sending message to " + username);
				System.out.println(e.toString());
			}
			return true;
        }

        private void sendRequest(String c) {

           String[] users = c.split(","); //split into 3
           String checkType = users[0];
           String to = users[1];
           String from = users[2];
           String msg = checkType + from;

           for (int i = 0; i < clientThreads.size(); i++) {
               ClientHandler t = clientThreads.get(i);

               if (t.username.equals(to)) {
                   t.writeMsg(msg);

               }

           }

       }

        private void addFriend(String c) {
            
            String[] users = c.split(","); //split into 3
            String checkType = users[0];
            String to = users[1];
            String from = users[2]; //comma

            dbConnect b = new dbConnect();
            b.addFriend(to,from);
            
            ArrayList friends;
            
            
            for (int i = 0; i < clientThreads.size(); i++) {
                ClientHandler t = clientThreads.get(i);

                if (t.username.equals(to)) {
                    
                    try {
                        friends = b.GetUserDetails(to);
                        t.toClient.writeObject(friends);
                    } catch (IOException ex) {
                        Logger.getLogger(server_frame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                else if (t.username.equals(from))
                {
                    try {
                        friends = b.GetUserDetails(from);
                        t.toClient.writeObject(friends);
                    } catch (IOException ex) {
                        Logger.getLogger(server_frame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            
            
           
            
            
            
        }
        
        
   }

    private void privateChat(String u) {
        String[] users = u.split(","); //split into 3
        String checkType = users[0];
        String to = users[1];
        String from = users[2];
        String msg = checkType + from;

        for (int i = 0; i < clientThreads.size(); i++) {
            ClientHandler t = clientThreads.get(i);

            if (t.username.equals(to)) {
                //send name of who to start chat with
                t.writeMsg(msg);
                //t.privateChat(to);
            }

        }
        
    }
    
    public void start() {
        keepGoing = true;
        /* create socket server and wait for connection requests */
        
        try {
            ServerSocket serverSocket = new ServerSocket(2222);

            while (keepGoing) {
                System.out.println("Server waiting for Clients on port " + 2222 + ".");

                Socket socket = serverSocket.accept();  	// accept connection

                ClientHandler t = new ClientHandler(socket);  // make a thread of it
                clientThreads.add(t);                      // save it in the ArrayList
                t.start();
            }
            // I was asked to stop
            try {
                serverSocket.close();
                for (int i = 0; i < clientThreads.size(); ++i) {
                    ClientHandler tc = clientThreads.get(i);
                    tc.fromClient.close();
                    tc.toClient.close();
                    tc.sock.close();
                }
            } catch (Exception e) {
                System.out.println("Exception closing the server and clients: " + e);
            }
        } // something went bad
        catch (IOException e) {

        }
    }	
    
    synchronized void broadcast() throws IOException {

        for (int i = clientThreads.size(); --i >= 0;) {
            ClientHandler ct = clientThreads.get(i);
            ct.UpdateOnlineMembers();

        }

    }
    
    synchronized void publicChat(String msg) throws IOException {

        for (int i = clientThreads.size(); --i >= 0;) {
            ClientHandler ct = clientThreads.get(i);
            ct.writeMsg(msg);
        }

    }
        
    public synchronized void remove(String Username) throws IOException {
        // scan the array list until we found the Id
        for (int i = 0; i < clientThreads.size(); ++i) {
            ClientHandler ct = clientThreads.get(i);
            // found it
            if (ct.username.equals(Username)) {
                System.out.println("Found " + ct.username);
                clientThreads.remove(i);
                OnlineUsers.remove(i);
                return;
            }
        }
    }
}