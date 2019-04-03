/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_client;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author James
 */
public class User implements Serializable{
    
    String Username, POB, DOB;
    //String[] Friends;
    ArrayList<String> Songs;
  
    User(String Username, String POB, String DOB)
    {
        this.Username = Username;
        this.POB = POB;
        this.DOB = DOB;
    }

    String getUsername(){return Username;}
    String getPOB(){return POB;}
    String getDOB(){return DOB;}
    //String[] getFriends(){return Friends;}
    ArrayList<String> getSongs(){return Songs;}
    
    void setUsername(String UName){this.Username = UName;}
    void setSongs (ArrayList <String> songs){this.Songs = songs;}
   
}
