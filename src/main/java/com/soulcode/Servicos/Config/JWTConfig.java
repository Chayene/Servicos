package com.soulcode.Servicos.Config;

import com.soulcode.Servicos.Security.JWTAuthenticationFilter;
import com.soulcode.Servicos.Security.JWTAuthorizationFilter;
import com.soulcode.Servicos.Services.AuthUserDetailService;
import com.soulcode.Servicos.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
// essa classe serve para unificar as operações do ambiente, agrega todas as informações http e gerencia do user

@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //userDetailService carregar o usuário do banco
        //BCrypt gerar o hash de senhas
        //comparar as senhas
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable(); //habilita o cors e desabilita o csrf(proteção p ataque csrf)
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils)); //adiciona o filtro de autenticação
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtils)); //adiciona o filtro de autorização

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll() //permitir acesso ao endpoint de autenticação
                //           .antMatchers(HttpMethod.GET, "/servicos/**").permitAll() //permitir acesso ao endpoint de autorização
                .anyRequest().authenticated(); //todos os outros endpoints precisam estar autenticados

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //não cria sessão de usuário

    }

    @Bean // CROSS ORIGIN RESOURCE SHARING
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }
    // "/servicos/funcionario"-> "/**" --> todos os endpoints


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}