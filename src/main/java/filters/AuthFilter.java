package filters;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author caiot
 */
@WebFilter(urlPatterns = {"/ChatPrincipal"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session;
        session = ((HttpServletRequest) (request)).getSession();

        if (session.getAttribute("logado") == null || session.getAttribute("logado").equals(false) || session == null) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("Login");
            return;
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
