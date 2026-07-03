package com.seplag.serverseplag.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.seplag.serverseplag.service.TokenService;
import com.seplag.serverseplag.usuarios.IUsuarioRepository;
import com.seplag.serverseplag.usuarios.UsuarioModel;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    private UserDetailsService userDetailsService; 

    // @Autowired
    // private AuthenticationManager authenticationManager;

       // 🔥 REPOSITÓRIO DE CONTEXTO PARA STATELESS
    // private final SecurityContextRepository securityContextRepository = 
    //     new HttpSessionSecurityContextRepository();

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain)
            throws ServletException, IOException {
        
       System.out.println("🔍 ===== INÍCIO FILTRO =====");
        System.out.println("🔍 URI: " + request.getRequestURI());
        System.out.println("🔍 Method: " + request.getMethod());
        
        var token = this.recoverToken(request);
        System.out.println("🔍 Token encontrado: " + (token != null ? "SIM" : "NÃO"));
        System.out.println("🔍 Token: " + (token != null ? token.substring(0, Math.min(token.length(), 20)) + "..." : "null"));
        
        if (token != null) {
            var login = tokenService.validateToken(token);
            System.out.println("🔍 ID extraído do token: " + login);
            
            if (login != null) {
                try {
                    UsuarioModel userM = usuarioRepository.findById(login);
                    UserDetails user = userDetailsService.loadUserByUsername(userM.getLogin());
                    System.out.println("🔍 Usuário encontrado: " + (user != null ? user.getUsername() : "null"));
                    System.out.println("🔍 Authorities do usuário: " + (user != null ? user.getAuthorities() : "null"));
                    
                    if (user != null) {
                        UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        
                        // 🔥 CRIA UM NOVO CONTEXTO
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(authentication);
                        SecurityContextHolder.setContext(context);
                        
                        System.out.println("✅ AUTENTICAÇÃO SETADA!");
                        System.out.println("✅ Nome: " + authentication.getName());
                        System.out.println("✅ Authorities: " + authentication.getAuthorities());
                        System.out.println("✅ Autenticado: " + authentication.isAuthenticated());
                        System.out.println("✅ Contexto atual: " + SecurityContextHolder.getContext().getAuthentication());
                    }
                } catch (Exception e) {
                    System.out.println("❌ Erro ao carregar usuário: " + e.getMessage());
                    e.printStackTrace();
                    SecurityContextHolder.clearContext();
                }
            } else {
                System.out.println("⚠️ Token inválido ou expirado");
                SecurityContextHolder.clearContext();
            }
        } else {
            System.out.println("⚠️ Nenhum token encontrado");
            SecurityContextHolder.clearContext();
        }
        
        System.out.println("🔍 Antes do filterChain: " + SecurityContextHolder.getContext().getAuthentication());
        
        filterChain.doFilter(request, response);
        
        System.out.println("🔍 Depois do filterChain: " + SecurityContextHolder.getContext().getAuthentication());
        System.out.println("🔍 ===== FIM FILTRO =====");
    }
  private String recoverToken(HttpServletRequest request){
    var authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7); // Remove "Bearer " do início do token

    }
    return null;
  }



  

}
