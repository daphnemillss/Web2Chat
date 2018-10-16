/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author caiot
 */

@WebFilter(urlPatterns={"/*"})
public class I18nFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Locale.setDefault(((HttpServletRequest)request).getLocale());
        
        ResourceBundle messages = ResourceBundle.getBundle("resources/Messages_"+((HttpServletRequest)request).getLocale());
        
        Enumeration<String> keys = messages.getKeys();
        
        Map msgs = new HashMap();
        
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            String valor = messages.getString(key);
            msgs.put(key, valor);
        }
        
        request.setAttribute("msgs", msgs);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
    
}
