package com.vir.config;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.vir.service.UserInfoUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	
	  @Bean 
	  public PasswordEncoder passwordEncoder() { 
		  return new BCryptPasswordEncoder(); 
		  }
	  
	  @Bean 
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
			
			  http
			  .authorizeHttpRequests((requests) -> requests
			  .requestMatchers("/employees/home", "/employees/new").permitAll()
		 .anyRequest().authenticated())
				 /*.sessionManagement((sessionManagement) ->
				sessionManagement
					.sessionConcurrency((sessionConcurrency) ->
						sessionConcurrency
							.maximumSessions(1)
							.expiredUrl("/login?expired")))*/

			  .formLogin((form) -> form
					  .permitAll() )
			  .logout((logout) -> logout
					  .permitAll());
	  
	  return http.build(); 
	  }
	  
	  @Bean 
	  public UserDetailsService userDetailsService() { 
		  /*UserDetails user = User
				  .withUsername("user")
				  .password(encoder.encode("password"))
				  .roles("USER") 
				  .build();
	  
	  UserDetails admin = User.withUsername("admin") 
			  .password(encoder.encode("admin"))
	  .roles("ADMIN") 
	  .build();
	  
	  return new InMemoryUserDetailsManager(user, admin); 
	 */
		  return new UserInfoUserDetailsService();
	  }
	  
	  
		
		  @Bean public AuthenticationProvider authenticationProvider() {
		  DaoAuthenticationProvider authenticationProvider = new
		  DaoAuthenticationProvider();
		  authenticationProvider.setUserDetailsService(userDetailsService());
		  authenticationProvider.setPasswordEncoder(passwordEncoder()); return
		  authenticationProvider; }
		 
}