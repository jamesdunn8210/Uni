/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author James
 */
public class Client_Home extends javax.swing.JFrame {
  
    ArrayList<User> FriendsList = new ArrayList();
    ArrayList<String> OnlineUsers = new ArrayList();    
    
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    Socket sock;
    
    static String address = "localhost";
    String username;
    static int port = 2222;   
    
    public Client_Home(String Username) {
        initComponents();
        this.username = Username;
        this.start();
        this.setVisible(true);
    }
     
    private void start() {
		try {
			sock = new Socket(address, port);
		} 
		catch(Exception ec) {
			System.out.println("Error connectiong to server:" + ec);
		}
		try
		{
			fromServer  = new ObjectInputStream(sock.getInputStream());
			toServer = new ObjectOutputStream(sock.getOutputStream());
		}
		catch (IOException eIO) {
			System.out.println("Exception creating new Input/output Streams: " + eIO);
		}
		new ListenFromServer().start();
		try
		{
			toServer.writeObject(username);
		}
		catch (IOException eIO) {
			System.out.println("Exception doing login : " + eIO);
			disconnect();
		}
	}
   
    private void disconnect() {
        try {
            String d = "Disconnect";
            toServer.writeObject(d);
            toServer.close();
            fromServer.close();
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(Client_Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    class ListenFromServer extends Thread {
        
    public void run() {
            
            loadData();
            while (true) {
                try {
                        Object t = fromServer.readObject();
                        
                        if(t.toString().startsWith("privateChat"))
                        {
                            String a = t.toString().substring(11);
                            startChat(a);
                        }
                        else if(t.toString().startsWith("publicChat"))
                        {
                            String a = t.toString().substring(10);
                            txtarea_public_chat.append(a);
                        }
                        else if(t.toString().startsWith("sendRequest"))
                        {
                            String a = t.toString().substring(11);
                            friendRequest(a);
                        }
                        else if(t.toString().contains(username) & t.toString().startsWith("[")) //arraylist of string 
                        {
                            System.out.println("reading online users");
                            OnlineUsers = (ArrayList<String>) t;// fromServer.readObject();
                            loadOnlineUsers();
                        }
                        else if(t instanceof ArrayList ) //arraylist of users
                        {
                            System.out.println("adding new friends");
                            FriendsList = (ArrayList<User>) t; 
                            loadFriends();
                        }
                        
                } catch (IOException e) {
                    System.out.println("Server has close the connection: " + e);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client_Home.class.getName()).log(Level.SEVERE, null, ex);
                } 
  
            }
        }

    private void friendRequest(String a) {
            
            DefaultListModel model = new DefaultListModel();
            model.addElement(a);       
            lst_Requests.setModel(model);
            
        }
    }
    
    public void loadData()
    {
        lbl_Welcome_Message.setText("Welcome " + username + "!");

        loadFriends();
    
    }
     
    private void loadOnlineUsers()
    {      
        DefaultListModel model = new DefaultListModel();
   
        for (String OnlineUser : OnlineUsers) {
            model.addElement(OnlineUser);
        }

        lst_Connected_Users.setModel(model);
    }
    
    private boolean isFriend(String Username)
    {
        //friendslist empty
        for (User FriendsList1 : FriendsList) {
            if (FriendsList1.getUsername().equals(Username)) {
                return true;
            }
        }
        return false;        
    }
   
    private void loadFriends() {
        
        //ArrayList<ArrayList<User>> Friends = new ArrayList<>(Arrays.asList(FriendsList)); //can delete?
        
        DefaultListModel model = new DefaultListModel();
        
        for (int i = 0; FriendsList.size() > i; i++) {
            
            model.addElement(FriendsList.get(i).getUsername());
        }
        
        lst_Friends_List.setModel(model);
 
    }
         
    private Object getListItem(JList S)
    {
        Object sel = null;
        
        int[] selectedIx = S.getSelectedIndices();
        int selected = 0;
        for (int i = 0; i < selectedIx.length; i++) {
            sel = S.getModel().getElementAt(selectedIx[i]);
            selected = selectedIx[i];
        }
        
        return sel;
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lst_Friends_List = new javax.swing.JList<>();
        lbl_Friends = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_Info = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        lst_SharedSongs = new javax.swing.JList<>();
        lbl_Songs = new javax.swing.JLabel();
        lbl_Information = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        lbl_Connected_Users = new javax.swing.JLabel();
        btn_Add_Friend = new javax.swing.JButton();
        btn_Chat = new javax.swing.JButton();
        lbl_Welcome_Message = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lst_Connected_Users = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        lst_Requests = new javax.swing.JList<>();
        lbl_Requests = new javax.swing.JLabel();
        btn_Confirm = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtarea_public_chat = new javax.swing.JTextArea();
        txt_public_message = new javax.swing.JTextField();
        btn_Send = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lst_Friends_List.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lst_Friends_List.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lst_Friends_List.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lst_Friends_ListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lst_Friends_List);

        lbl_Friends.setText("Friends");

        txt_Info.setColumns(20);
        txt_Info.setRows(5);
        jScrollPane2.setViewportView(txt_Info);

        lst_SharedSongs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(lst_SharedSongs);

        lbl_Songs.setText("Shared Songs");

        lbl_Information.setText("Information");

        lbl_Connected_Users.setText("Connected Users");

        btn_Add_Friend.setText("Add Friend");
        btn_Add_Friend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Add_FriendActionPerformed(evt);
            }
        });

        btn_Chat.setText("Chat");
        btn_Chat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChatActionPerformed(evt);
            }
        });

        lst_Connected_Users.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(lst_Connected_Users);

        lst_Requests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(lst_Requests);

        lbl_Requests.setText("Requests from");

        btn_Confirm.setText("Confirm");
        btn_Confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ConfirmActionPerformed(evt);
            }
        });

        txtarea_public_chat.setColumns(20);
        txtarea_public_chat.setRows(5);
        jScrollPane4.setViewportView(txtarea_public_chat);

        btn_Send.setText("Send");
        btn_Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Welcome_Message, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_Send)
                        .addComponent(txt_public_message, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Connected_Users, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lbl_Requests, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(148, 148, 148))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_Add_Friend)
                                .addComponent(btn_Chat, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_Confirm))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                .addComponent(lbl_Friends, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_Information, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_Songs, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lbl_Welcome_Message, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Songs, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_Friends)
                        .addComponent(lbl_Information)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_public_message, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Send)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Connected_Users)
                    .addComponent(lbl_Requests, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(jScrollPane5)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_Add_Friend)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_Chat))
                            .addComponent(btn_Confirm))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    //in progress
    private void btn_Add_FriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Add_FriendActionPerformed
        
        Object sel= getListItem(lst_Connected_Users);
                        
        DefaultListModel model = new DefaultListModel();
        if(isFriend((String) sel))
        {
            System.out.println("Alredy friend");
        }
        else
        {
            sendRequest(sel.toString());
        }
        
    }//GEN-LAST:event_btn_Add_FriendActionPerformed

    private void sendRequest(String data)
    {
        data = "sendRequest,"+data + ","+ this.username; //add,toadd,from
        try {
            toServer.writeObject(data);
        } catch (IOException ex) {
            Logger.getLogger(Client_Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void lst_Friends_ListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_Friends_ListMouseClicked
        
        Object sel= null; //getListItem(lst_Friends_List);
        
        int[] selectedIx = lst_Friends_List.getSelectedIndices();
        int selected = 0;
        for (int i = 0; i < selectedIx.length; i++) {
            sel = lst_Friends_List.getModel().getElementAt(selectedIx[i]);
            selected = selectedIx[i];
        }
        
        txt_Info.setText("");
        txt_Info.append(FriendsList.get(selected).getPOB() + '\n' + FriendsList.get(selected).getDOB());
        
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < FriendsList.get(selected).Songs.size(); i++) {
            model.addElement(FriendsList.get(selected).getSongs().get(i));
        }
        //model.addElement(FriendsList.get(selected).getSongs());
        lst_SharedSongs.setModel(model);
        

    }//GEN-LAST:event_lst_Friends_ListMouseClicked

    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.disconnect();
    }//GEN-LAST:event_formWindowClosing

    //Next functions relate to chat server
    
    public void startChat(String friend)
    {
        Client_chat a = new Client_chat(username, friend);
    }
 
    private void btn_ConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ConfirmActionPerformed
            
        Object sel = getListItem(lst_Requests);
        String f = "addFriend," + username + "," + sel;
        
        try {
            toServer.writeObject(f); //send new friend
        } catch (IOException ex) {
            Logger.getLogger(Client_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int[] selectedIx = lst_Requests.getSelectedIndices();
        DefaultListModel lm1=(DefaultListModel) lst_Requests.getModel();
        int selected = 0;
        for (int i = 0; i < selectedIx.length; i++) { 
            if(lst_Requests.getModel().getElementAt(selectedIx[i]).equals(sel.toString()))
            {
                lm1.remove(i);
            }
            
        }
 
    }//GEN-LAST:event_btn_ConfirmActionPerformed

    private void btn_ChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChatActionPerformed
                
        Object sel = getListItem(lst_Connected_Users);
        if(!sel.toString().equals(username))
        {
            startChat(sel.toString());
        }
        
        sel = "privateChat," + sel + "," + username ; //user to chat with 
        
        try {
            toServer.writeObject(sel);
        } catch (IOException ex) {
            Logger.getLogger(Client_Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btn_ChatActionPerformed

    private void btn_SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SendActionPerformed
        

        Object t = "publicChat" + username + ": "+ txt_public_message.getText()+ '\n';
        txt_public_message.setText(" ");
        try {
            toServer.writeObject(t);
        } catch (IOException ex) {
            Logger.getLogger(Client_Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btn_SendActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add_Friend;
    private javax.swing.JButton btn_Chat;
    private javax.swing.JButton btn_Confirm;
    private javax.swing.JButton btn_Send;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbl_Connected_Users;
    private javax.swing.JLabel lbl_Friends;
    private javax.swing.JLabel lbl_Information;
    private javax.swing.JLabel lbl_Requests;
    private javax.swing.JLabel lbl_Songs;
    private javax.swing.JLabel lbl_Welcome_Message;
    private javax.swing.JList<String> lst_Connected_Users;
    private javax.swing.JList<String> lst_Friends_List;
    private javax.swing.JList<String> lst_Requests;
    private javax.swing.JList<String> lst_SharedSongs;
    private javax.swing.JTextArea txt_Info;
    private javax.swing.JTextField txt_public_message;
    private javax.swing.JTextArea txtarea_public_chat;
    // End of variables declaration//GEN-END:variables
}

