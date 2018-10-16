/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Banco;
import model.CadObject;
import org.postgresql.util.PSQLException;

/**
 *
 * @author caiot
 */
@WebServlet(name = "Usuario", urlPatterns = {"/Usuario"})
public class Usuario extends HttpServlet {

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

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("emailvazio")) {
                    request.setAttribute("emailvazio", true);
                }
                if (c.getName().equals("uservazio")) {
                    request.setAttribute("uservazio", true);
                }
                if (c.getName().equals("senhavazia")) {
                    request.setAttribute("senhavazia", true);
                }
                if (c.getName().equals("senhasdiferentes")) {
                    request.setAttribute("senhasdiferentes", true);
                }
                if (c.getName().equals("errocadastro")) {
                    request.setAttribute("errocadastro", true);
                }
                if (c.getName().equals("cadastroduplicado")) {
                    request.setAttribute("cadastroduplicado", true);
                }
            }
        }

        request.getRequestDispatcher("/WEB-INF/view/usuario.jsp").forward(request, response);
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
        //processRequest(request, response);
        
        String usuario = request.getParameter("userUser");
        String email = request.getParameter("emailUser");
        String senha = request.getParameter("pwUser");
        String senha_conf = request.getParameter("pwConf");
        int flag = 0;
        boolean cadastrou = false;

        if (usuario.equals("")) {
            Cookie c = new Cookie("uservazio", "");
            c.setMaxAge(1);
            response.addCookie(c);
            flag++;
        }
        if (email.equals("")) {
            Cookie c = new Cookie("emailvazio", "");
            c.setMaxAge(1);
            response.addCookie(c);
            flag++;
        }
        if (senha.equals("") || senha_conf.equals("")) {
            Cookie c = new Cookie("senhavazia", "");
            c.setMaxAge(1);
            response.addCookie(c);
            flag++;
        }
        if (!senha.equals(senha_conf)) {
            Cookie c = new Cookie("senhasdiferentes", "");
            c.setMaxAge(1);
            response.addCookie(c);
            flag++;
        }

        if (flag != 0) {
            //do nothing
        } else {
            try {
                boolean cad = Banco.cadastrar(usuario, email, senha);

                if (cad) {
                    cadastrou = true;
                } else {
                    Cookie c = new Cookie("errocadastro", "");
                    c.setMaxAge(1);
                    response.addCookie(c);
                    cadastrou = false;
                }
            } catch (PSQLException ex) {
                Cookie c = new Cookie("cadastroduplicado", "");
                c.setMaxAge(1);
                response.addCookie(c);
                cadastrou = false;

            } catch (SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (cadastrou) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
       
    }
    
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response){
        
        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        String nothing = parts[0];
        String project = parts[1];
        String page = parts[2];
        String user = parts[3];
        
        System.out.println(user);
        try {
            if(Banco.deleteAccount(user)){
                HttpSession sessao = request.getSession();
                sessao.invalidate();
                //System.out.println("sucesso");
                response.setStatus(HttpServletResponse.SC_OK);
                request.getRequestDispatcher("Login").forward(request, response);
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
