package com.seplag.serverseplag.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FallbackSecurityFilter extends OncePerRequestFilter{
  @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // Verifica se é endpoint público
        boolean isPublic = path.startsWith("/auth/") || 
                          (path.equals("/artistas") && request.getMethod().equals("GET")) ||
                          (path.equals("/albuns") && request.getMethod().equals("GET"));
        
        if (!isPublic) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            // Se não tiver autenticação ou for anônimo, bloqueia
            if (auth == null || !auth.isAuthenticated() || 
                auth.getPrincipal().equals("anonymousUser")) {
                
                System.out.println("🚫 Acesso negado - sem autenticação para: " + path);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Acesso negado\"}");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
