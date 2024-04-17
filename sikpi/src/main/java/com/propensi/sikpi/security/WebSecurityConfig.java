package com.propensi.sikpi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.propensi.sikpi.security.jwt.JwtTokenFilter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain jwtfilFilterChain(HttpSecurity http) throws Exception {

        http.securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/user/add").hasAuthority("Admin")
                        .requestMatchers("/api/penerbit", "/api/buku", "/api/buku/create").hasAuthority("Pustakawan")
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
     http
         .csrf(Customizer.withDefaults())
         .authorizeHttpRequests(requests -> requests
             .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
             .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
             .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
             .anyRequest().authenticated()
         )
         .formLogin((form) -> form
             .loginPage("/login")
             .permitAll()
             .defaultSuccessUrl("/")
         )
         .logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
             .logoutSuccessUrl("/login"))
         .exceptionHandling(exceptionHandling -> exceptionHandling
             .accessDeniedHandler((request, response, accessDeniedException) -> {
                 // Handle access denied (forbidden) situation
                 response.sendRedirect("/access-denied"); // You can customize the URL
             }))
     ;
     return http.build();
    }
    
    

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                System.out.println("CORS Configuration Applied");
                registry.addMapping("/api/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    
    
}
