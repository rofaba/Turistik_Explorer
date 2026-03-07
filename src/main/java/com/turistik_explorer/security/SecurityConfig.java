package com.turistik_explorer.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the application.
 * This class defines the security filter chain and password encoder.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // 1. ARCHIVOS ESTÁTICOS: Permitimos CSS, JS, imágenes
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // 2. RUTAS PÚBLICAS: Añadimos las vistas de detalle para que los usuarios puedan verlas sin login
                        .requestMatchers("/", "/explore", "/login", "/error", "/poi/**", "/hotel/**", "/restaurant/**").permitAll()

                        // 3. RUTAS PROTEGIDAS: Solo para administradores
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 4. Cualquier otra cosa requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/pois", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Forzamos que escuche en esta ruta
                        .logoutSuccessUrl("/") // Destino final
                        .invalidateHttpSession(true) // Destruye la sesión
                        .deleteCookies("JSESSIONID") // Borra la cookie de seguridad
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/admin/**") // Ignoramos CSRF para rutas admin (si usas POST desde el panel)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}