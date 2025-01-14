package com.iavtar.gems.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user")
                .password(passwordEncoder().encode("12345"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("12345"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, admin);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.getOrBuild();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/webjars/**",
                                     "/css/**",
                                     "/asset/**",
                                     "/js/**",
                                     "/images/**",
                                     "/login")
                            .permitAll().anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> {
                    formLogin.loginPage("/login")
                            .loginProcessingUrl("/process-login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/dashboard", true)
                            .failureUrl("/login?error=true")
                            .permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout")
                            .permitAll()
                            .clearAuthentication(true)
                            .deleteCookies("JSESSIONID")
                            .invalidateHttpSession(true);
                })
                .build();
    }

}
