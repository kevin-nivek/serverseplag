package com.seplag.serverseplag.config;

import java.util.Arrays;

import org.simpleframework.xml.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.seplag.serverseplag.filter.FallbackSecurityFilter;
import com.seplag.serverseplag.filter.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private SecurityFilter securityFilter;
  
  // @Bean
  // public SecurityFilter securityFilter() {
  //   return new SecurityFilter();
  // }

  // @Bean
  // public FilterRegistrationBean<SecurityFilter> securityFilterRegistration(SecurityFilter securityFilter) {
  //   FilterRegistrationBean<SecurityFilter> registration = new FilterRegistrationBean<>();
  //   registration.setFilter(securityFilter);
  //   registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
  //   registration.addUrlPatterns("/*");
  //   registration.setName("securityFilter");
  //   return registration;
  // }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    
    httpSecurity
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .anonymous(anonymous -> anonymous.disable()) 
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
        // .requestMatchers(HttpMethod.GET, "/artistas").permitAll()
        .requestMatchers(HttpMethod.GET, "/albuns").permitAll()
        .anyRequest().authenticated()
        
      ).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    
    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
          throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setExposedHeaders(Arrays.asList("Authorization"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
