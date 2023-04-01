
package com.bookstore.litsener;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


public class Litsener implements MessageListener {
    public String value;

    @Override
    public void onMessage(Message message) {
        
        // Generated from nbfs:
        TextMessage msg =null;
        if(message instanceof TextMessage){
           msg=(TextMessage)message;
            try {
                value=msg.getText();
                
            } catch (JMSException ex) {
                
            }
           
            
       
    }
   
}
    public String getValue(){
        return value;
    }
}