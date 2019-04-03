/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_client;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author James
 */
public class dbConnect {
    
    String DBhost = "jdbc:derby://localhost:1527/Users";
    String DBusername = "Admin1";
    String DBpassword = "Password";

    Connection con;
    Statement stmt;
    ResultSet rs;
    Integer total = 1; //last ID 
    
    public dbConnect()
    {
        DoConnect();
    }
    
    public void DoConnect()
    {
        try{
            con = DriverManager.getConnection(DBhost, DBusername, DBpassword);
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        } 
        
    }
    
    public void addUser(User newUser) throws SQLException 
    {
        rs = stmt.executeQuery("Select ID FROM USERS");
            while(rs.next())
            {
                total+=1;
            }
        
        
        String Username, POB, DOB;
        Username = newUser.getUsername();
        POB = newUser.getPOB();
        DOB = newUser.getDOB();
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO USERS(ID,USERNAME,POB,DOB) VALUES(?,?,?,?)"); //prepared allows variables
            pstmt.setInt(1, total);
            pstmt.setString(2, Username);
            pstmt.setString(3, POB);
            pstmt.setString(4, DOB);
            
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(dbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addSong(String user, String artist, String title)
    {
        
         try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO SONGS(ID,ARTIST, TITLE) VALUES(?,?,?)"); //prepared allows variables
            pstmt.setInt(1, total);
            pstmt.setString(2, artist);
            pstmt.setString(3, title);
            
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(dbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    boolean UsernameTaken(String UName) 
    {
        try {
            rs = stmt.executeQuery("Select USERNAME FROM USERS");
            while(rs.next())
            {
                String Username = rs.getString("Username");
                if(UName.equals(Username))
                {
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(dbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    ArrayList GetUserDetails(String Username)
    {
        ArrayList info = new ArrayList<>();
        
        try {
            
            String Uname,Dob,Pob;
            
            PreparedStatement mUsers = null;
            mUsers = con.prepareStatement("SELECT Username, DOB, POB FROM USERS WHERE ID in (SELECT FRIEND_ID FROM FRIENDS Where USER_ID in (Select ID FROM USERS WHERE USERNAME = '" + Username + "'))");
            rs = mUsers.executeQuery();
            
            while(rs.next()) 
            {
                Uname = rs.getString("USERNAME");
                Dob = rs.getString("Dob");
                Pob = rs.getString("Pob");
                
                User a = new User(Uname,Dob,Pob);
                info.add(a);
                
            }

            //setup songs
            
            for (Object u:info)
            {
                ArrayList<String> s = new ArrayList<>();
                User c = (User) u;
                rs = stmt.executeQuery("Select ARTIST,TITLE from SONGS WHERE ID = (SELECT ID from USERS WHERE USERNAME = " + "'" + c.Username + "'" + ")");
                
                while (rs.next()) {
                    String artist = rs.getString("ARTIST");
                    String title = rs.getString("TITLE");
                    s.add(artist + "-" + title);
                }
                
                c.setSongs(s);
            }
           

        } catch (SQLException ex) {
            Logger.getLogger(dbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return info; 
    }

    void addFriend(String usera, String userb) {
        
        Integer a = null,b = null;
        try {
            rs = stmt.executeQuery("SELECT ID FROM USERS WHERE USERNAME = '" + usera + "'");
            while(rs.next())
            {
                a = rs.getInt("ID");
                
            }
            rs = stmt.executeQuery("SELECT ID FROM USERS WHERE USERNAME = '" + userb + "'");
            while(rs.next())
            {
                b = rs.getInt("ID");
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        try{
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO FRIENDS (USER_ID, FRIEND_ID) VALUES (?,?)");
            pstmt.setInt(1, a);
            pstmt.setInt(2, b);
            
            pstmt.executeUpdate();
            
            pstmt.setInt(1, b);
            pstmt.setInt(2, a);
            
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(dbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}


    
    
