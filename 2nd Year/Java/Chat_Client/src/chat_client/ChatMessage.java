/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_client;
import java.io.Serializable;
/**
 *
 * @author James
 */
public class ChatMessage implements Serializable {
    
    private String message, fromUser, toUser;
    
    ChatMessage(String fromUser, String toUser, String message) {
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.message = message;

    }
    
    String getUser(){
        return fromUser;
    }
    
    String getFriend(){
        return toUser;
    }
    
    String getMessage() {
        return message;
    }
    
    
}
