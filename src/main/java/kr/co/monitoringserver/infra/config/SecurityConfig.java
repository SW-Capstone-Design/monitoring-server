package kr.co.monitoringserver.infra.config;

import kr.co.monitoringserver.infra.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private static final String[] AUTH_WHITELIST = {
        "/", "/auth/**", "/js/**", "/css/**", "/image/**"
    };

    @Bean
    BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    private void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
            .shouldFilterAllDispatcherTypes(false)
            .requestMatchers(AUTH_WHITELIST)
            .permitAll()
            .requestMatchers("/api/v1/attendance").permitAll()
            .requestMatchers("/api/v1/attendanceStatus").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest()
            .authenticated())
                .formLogin(login -> login
                .loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")
                .usernameParameter("identity")
                .passwordParameter("password")
                .defaultSuccessUrl("/", false)
                .permitAll())
                .logout(Customizer.withDefaults());

        return http.build();
    }
}
