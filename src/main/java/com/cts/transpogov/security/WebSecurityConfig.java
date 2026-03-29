package com.cts.transpogov.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	//stateless APIs
    	//Enabling it would add unnecessary overhead
    	//CSRF attacks mainly target cookie-based sessions where the browser automatically appends credentials.
        http.csrf(csrf -> csrf.disable())
        //tells Spring not to create an HttpSession
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            //Handles 401 Unauthorized errors (when a token is missing or invalid).
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint)
            		//Handles 403 Forbidden errors (when a user is logged in but doesn't have the right Role).
                    .accessDeniedHandler(accessDeniedHandler))
            .authorizeHttpRequests(auth -> auth
                // Public & Swagger Endpoints
                .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // Existing Program & Ticket Endpoints
                .requestMatchers(HttpMethod.GET, "/programs/{programId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/tickets/**").hasAnyRole("PASSENGER", "TRANSPORT_OFFICER")
                .requestMatchers(HttpMethod.POST, "/tickets/*/check").hasRole("TRANSPORT_OFFICER")
                .requestMatchers(HttpMethod.GET, "/programs").hasAnyRole("PASSENGER", "TRANSPORT_OFFICER", "PROGRAM_MANAGER", "ADMIN", "COMPLIANCE_OFFICER")
                .requestMatchers(HttpMethod.POST, "/programs").hasRole("PROGRAM_MANAGER")
                .requestMatchers(HttpMethod.POST, "/programs/*/submit").hasRole("PROGRAM_MANAGER")
                .requestMatchers(HttpMethod.POST, "/programs/*/approve").hasAnyRole("PROGRAM_MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/resources").hasAnyRole("PROGRAM_MANAGER", "ADMIN", "COMPLIANCE_OFFICER")
                .requestMatchers(HttpMethod.POST, "/resources/*/allocate").hasAnyRole("PROGRAM_MANAGER", "COMPLIANCE_OFFICER")
                .requestMatchers(HttpMethod.POST, "/resources/*/utilizations").hasAnyRole("PROGRAM_MANAGER", "COMPLIANCE_OFFICER")

                // --- NEW ENDPOINTS (38 - 43) ---

                // 38: Operational Dashboard
                .requestMatchers(HttpMethod.GET, "/reports/operations").hasAnyRole("PROGRAM_MANAGER", "ADMIN", "COMPLIANCE_OFFICER", "GOVERNMENT_AUDITOR")

                // 39: Generate new report
                .requestMatchers(HttpMethod.POST, "/reports/custom/run").hasAnyRole("PROGRAM_MANAGER", "ADMIN", "COMPLIANCE_OFFICER", "GOVERNMENT_AUDITOR")

                // 40: Check report status
                .requestMatchers(HttpMethod.GET, "/reports/custom/jobs/**").hasAnyRole("PROGRAM_MANAGER", "ADMIN", "COMPLIANCE_OFFICER", "GOVERNMENT_AUDITOR")

                // 41 & 42: Notifications (All Roles - Note: "Own" logic should be in Service layer)
                .requestMatchers(HttpMethod.GET, "/notifications").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/notifications/**").authenticated()

                // 43: Push notification
                .requestMatchers(HttpMethod.POST, "/notifications/save").hasAnyRole("TRANSPORT_OFFICER", "PROGRAM_MANAGER", "ADMIN", "COMPLIANCE_OFFICER")

                // Admin specific
                .requestMatchers("/user/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )//The Filter Injection
            //This is the most critical line. By default, Spring looks for a username/password in a form. You are telling Spring:
            //"Before you try the standard login, run my jwtAuthFilter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}