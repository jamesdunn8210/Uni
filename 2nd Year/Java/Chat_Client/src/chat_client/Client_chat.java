/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author James
 */
public class Client_chat extends javax.swing.JFrame implements ActionListener{ 

    static ObjectInputStream fromServer;		// to read from the socket
    static ObjectOutputStream toServer;		// to write on the socket
    Socket socket;
	
    // the server, the port and the username
    private final String server = "localhost";
    private final int port = 1500;
    private String user;
    private String friend;
    
    
    public Client_chat(String User, String Friend) {
        this.user = User;
        this.friend = Friend;
        initComponents();
        btn_Send.addActionListener(this);
        this.start();
        this.setVisible(true);
    }
    
    public void start() {
        lbl_title.setText(lbl_title.getText()+friend);
	try {
		socket = new Socket(server, port);
                toServer = new ObjectOutputStream(socket.getOutputStream());
                fromServer  = new ObjectInputStream(socket.getInputStream());
                toServer.writeObject(user);
            } 
	catch(Exception ec) {
		System.out.println("Error connectiong to server:" + ec);
            }
            new ListenFromServer().start();	
	}

    private void display(String msg) {
        txtarea_output.append(msg + '\n');
    }

    void sendMessage(ChatMessage msg) {
        try {
            toServer.writeObject(msg); //sends message 
            txt_message.setText(" "); //clears message area            
            txtarea_output.append(msg.getUser() + " : " + msg.getMessage() + '\n');

        } catch (IOException e) {
            display("Exception writing to server: " + e);
            }
    }

    private void disconnect() {
        try { 
            if(fromServer != null) fromServer.close();
	}
	catch(Exception e) {} 
	try {
            if(toServer != null) toServer.close();
    	}
	catch(Exception e) {} 
        try{
            if(socket != null) socket.close();
	}
	catch(Exception e) {} 		
	}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtarea_output = new javax.swing.JTextArea();
        txt_message = new javax.swing.JTextField();
        btn_Send = new javax.swing.JButton();
        lbl_title = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txtarea_output.setColumns(20);
        txtarea_output.setRows(5);
        jScrollPane1.setViewportView(txtarea_output);

        btn_Send.setText("Send");

        lbl_title.setText("Chatting with: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_Send)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addComponent(txt_message, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_message, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Send)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.disconnect();
    }//GEN-LAST:event_formWindowClosing
    
    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();
        if (o == btn_Send) {
            String msg = txt_message.getText(); //get message

            sendMessage(new ChatMessage(this.user,this.friend, msg));                    
            }
    }

    class ListenFromServer extends Thread {

        public void run() {
            while (true) {
                try {
                    String msg = (String) fromServer.readObject();
                    txtarea_output.append(msg + '\n');

                } catch (IOException e) {
                    display("Server has close the connection: " + e);
                } 
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Send;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JTextField txt_message;
    private javax.swing.JTextArea txtarea_output;
    // End of variables declaration//GEN-END:variables
}