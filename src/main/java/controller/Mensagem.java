/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Banco;
import model.MsgObject;

/**
 *
 * @author caiot
 */
@WebServlet(name = "Mensagem", urlPatterns = {"/Mensagem"})
public class Mensagem extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/view/mensagens.jsp").forward(request, response);
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
        //MemcachedClient client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
        
        //System.out.println(request.getParameter("user1")+request.getParameter("user2"));
        
        List <MsgObject> lista = null;
       
        try {
            lista = Banco.buscaMensagens(request.getParameter("user1"), request.getParameter("user2"));
        } catch (SQLException ex) {
            Logger.getLogger(Mensagem.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        JsonElement json = (new Gson()).toJsonTree(lista);
        JsonArray listaJson = json.getAsJsonArray();
        
        request.setAttribute("listajson", listaJson);
        
        
        
        response.setStatus(HttpServletResponse.SC_OK);
        request.getRequestDispatcher("/WEB-INF/view/mensagens.jsp").forward(request, response);
        //client.shutdown();
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
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String msg = request.getParameter("msg");
        
        Date date = new Date();
        Timestamp time = new java.sql.Timestamp(date.getTime());
        	
        try{
            Banco.escreveMensagem(from, msg, to, time);
        } catch (SQLException ex) {
            Logger.getLogger(Mensagem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.sendRedirect("ChatPrincipal?from="+from+"&to="+to);
        //processRequest(request, response);
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
