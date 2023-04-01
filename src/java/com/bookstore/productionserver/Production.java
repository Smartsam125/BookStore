
package com.bookstore.productionserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Production extends HttpServlet {
          @Resource(lookup = "jms/BookStoreFactory")
          private ConnectionFactory connectionFactory;
          @Resource(lookup = "jm/BookStoreTopic")
          private Topic books;
          
         public void SendMessage()  {
             int count=0;
            
             try{
                  Connection connection =connectionFactory.createConnection();
             
             Session session =connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
             MessageProducer producer = session.createProducer(books);
             TextMessage message =session.createTextMessage();
              long startTime = System.nanoTime();
              long delayTime = 10000000000L; 
             while (count<=10) {     
                 long currentTime = System.nanoTime();
            if (currentTime - startTime >= delayTime) {
        // do something
        //System.out.println("Hello World!");
       // Date date=new Date();
       Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        message.setText(timestamp.toString());
        producer.send(message);
        
        startTime = System.nanoTime();
        count++;
    }

    }//close while true

//          message.setText("Hello sam");
//
//           producer.send(message);
             }
                         catch(JMSException e){

         }
                 
             /*finally{
                 if(connection != null){
                     try{
                         connection.close();
                        // System.out.println("hello");
                     }catch(JMSException e){
                     }
                 }
             }*/
             
           
             
         }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet Production</title>");
        out.println("</head>");
        out.println("<body>");
        //out.println();
        out.println("Sending Messages......!");
        SendMessage();
        out.println("</body>");
        out.println("</html>");
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
