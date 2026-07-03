package com.seplag.serverseplag.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.seplag.serverseplag.service.TokenService;
import com.seplag.serverseplag.usuarios.IUsuarioRepository;

// @Component
public class CustomAuthenticationProvider  implements AuthenticationProvider  {
   @Autowired
    private TokenService tokenService;
    
    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        
        try {
            // Valida o token e extrai o login (ID do usuário)
            String login = tokenService.validateToken(token);
            
            if (login != null) {
                Long userId = Long.parseLong(login);
                UserDetails user = usuarioRepository.findById(userId).orElse(null);
                
                if (user != null) {
                    // Retorna a autenticação com as authorities do usuário
                    return new UsernamePasswordAuthenticationToken(
                        user, 
                        null,  // credentials = null (já que é token)
                        user.getAuthorities()
                    );
                }
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Token inválido: " + e.getMessage());
        }
        
        throw new BadCredentialsException("Token inválido");
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
