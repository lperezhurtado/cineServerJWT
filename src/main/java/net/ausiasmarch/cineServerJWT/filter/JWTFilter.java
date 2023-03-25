package net.ausiasmarch.cineServerJWT.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import net.ausiasmarch.cineServerJWT.helper.JWTHelper;

@Component
public class JWTFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String auth = request.getHeader("Authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (auth != null && auth.startsWith("Bearer ")) {
                String token = auth.substring(7);
                try {
                    String nombre = JWTHelper.validateJWT(token);
                    request.setAttribute("usuario", nombre);
                } catch (Exception e) {
                    throw new ServletException("Token invalido");
                }
            }
            filterChain.doFilter(servletRequest, servletResponse); // el filter puede estar fuera de los if else
        }
    }

    @Override
    public void destroy() {
    }
}