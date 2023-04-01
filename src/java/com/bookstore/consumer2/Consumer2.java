
package com.bookstore.consumer2;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Consumer2 extends HttpServlet {
    List <String>Messagess=new ArrayList<>();
    @Resource(lookup = "jms/BookStoreFactory")
          private ConnectionFactory connectionFactory;
          @Resource(lookup = "jm/BookStoreTopic")
          private Topic books;
          public String Reciever(){
              String Value ="";
              try{
                  Connection connection =connectionFactory.createConnection();
                  Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
                  connection.start();
                  MessageConsumer consumer =session.createConsumer(books);
                  Message message=consumer.receive(10000);
                  if(message instanceof TextMessage){
                      TextMessage msg=(TextMessage)message;
                      Value=msg.getText();
                      
                      
                  }
//                 Litsener listener = new Litsener();
//                 consumer.setMessageListener(listener);
//                 //listener.onMessage(message);
//                 Value=listener.getValue();
//                 
//                 
                  
                  
                  
              }catch(JMSException e){
              }
              
              return Value;
                      
          }



    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Consumer1</title>");            
            out.println("</head>");
            out.println("<body>");
           String value=Reciever();
           if(value.equals("")){
               out.println("No message so far");
           }else{
               Messagess.add(value);
           }
             
            out.println("<br>");
            for (String Mess : Messagess) {
                
                out.println("Book has been produced on=== "+Mess+"===");
                 out.println("<br>");
            }
            out.println("<br>");
            out.println("Number of productions is --------"+Messagess.size());
          
           
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

