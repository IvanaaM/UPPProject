package com.ftn.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ftn.security.CheckTokenFilter;

@Configuration
@EnableWebSecurity 	
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigWeb extends WebSecurityConfigurerAdapter {

	@Bean
	public CheckTokenFilter authenticationFilter() throws Exception {
	CheckTokenFilter authenticationFilter = new CheckTokenFilter();

    authenticationFilter.setAuthenticationManager(authenticationManagerBean());
    return authenticationFilter;
	}
	
	 @Bean
	 @Override
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	 }
	 
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	@Override
  	protected void configure(HttpSecurity http) throws Exception {
		
		http
				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()
				.antMatchers("/admin/**").permitAll()
				.antMatchers("/editor/**").permitAll()
				.antMatchers("/user/**").permitAll().and()
				
				//.anyRequest().authenticated().and()
				
				
				.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
				

				
		http		.csrf().disable();
		
	}
	
}