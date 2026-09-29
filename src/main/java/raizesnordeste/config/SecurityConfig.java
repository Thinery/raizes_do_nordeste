package raizesnordeste.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import raizesnordeste.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final ApiAuthEntryPoint apiAuthEntryPoint;
    private final ApiAccessDeniedHandler apiAccessDeniedHandler;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter,
                           ApiAuthEntryPoint apiAuthEntryPoint,
                           ApiAccessDeniedHandler apiAccessDeniedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.apiAuthEntryPoint = apiAuthEntryPoint;
        this.apiAccessDeniedHandler = apiAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(eh -> eh
                    .authenticationEntryPoint(apiAuthEntryPoint)
                    .accessDeniedHandler(apiAccessDeniedHandler))
            .authorizeHttpRequests(auth -> auth
                    // Publico: login, cadastro de usuario/cliente, documentacao e consultas (GET)
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                    .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/produtos/**", "/unidades/**", "/estoques/**").permitAll()

                    // Movimentar estoque exige login + perfil ADMIN ou GERENTE
                    .requestMatchers(HttpMethod.POST, "/estoques/movimentacoes").hasAnyRole("ADMIN", "GERENTE")

                    // Auditoria e so para ADMIN
                    .requestMatchers("/auditoria/**").hasRole("ADMIN")

                    // Demais escritas (clientes, produtos, unidades) exigem apenas login
                    .anyRequest().authenticated())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
