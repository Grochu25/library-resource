package com.grochu.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                //.csrf(CsrfConfigurer::disable)//csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/books/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/authors/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/copies/**/available/number")).permitAll()
                        .requestMatchers("/api/users/**").hasAnyAuthority("SCOPE_customerStuff")
                        //.requestMatchers("api/**").authenticated()//.hasAnyAuthority("SCOPE_customerStuff")
                        .anyRequest().authenticated()
                )
                //.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                //.oauth2Login(login->
                //       login.loginPage("/oauth2/authorization/library-client"))
                //.oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth->oauth.jwt(Customizer.withDefaults()))
                .build();
    }
    //password is 'test'

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration conf = new CorsConfiguration();
        conf.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        conf.setAllowedMethods(List.of("GET", "POST", "UPDATE", "DELETE"));
        conf.setAllowedOrigins(List.of("http://localhost:5173"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);
        return source;
    }
}

