package com.iwcn.master.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Paths that can be visited without authentication
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/loguear").permitAll();
        http.authorizeRequests().antMatchers("/cerrarSesion").permitAll();

        // Paths that cannot be visited without authentication
        http.authorizeRequests().anyRequest().authenticated();

        // Login form
        http.formLogin().loginPage("/loguear");
        http.formLogin().usernameParameter("usuario");
        http.formLogin().passwordParameter("contra");
        http.formLogin().defaultSuccessUrl("/inicio");
        http.formLogin().failureUrl("/loguear?error");

        // Logout
        http.logout().logoutUrl("/cerrarSesion");
        http.logout().logoutSuccessUrl("/cerrarSesion");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // Authorization
    	auth.authenticationProvider(authenticationProvider);

    }

}
