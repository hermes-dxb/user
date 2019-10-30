package com.org.hermes.user.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${spring.queries.users-query}")
	private String userNameQuery;
	@Value("${spring.queries.roles-query}")
	private String userRoleQuery;

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/h2/**").permitAll().antMatchers("/registration").permitAll()
				.antMatchers("/validate/otp").permitAll()
				// .antMatchers("/user").permitAll()
				// .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest().authenticated()
				.and().csrf().disable().logout().logoutUrl("/logout").logoutSuccessHandler(getLogoutAuccessHandler())
				.addLogoutHandler(getLogoutHandler()).permitAll().and().exceptionHandling()
				.accessDeniedPage("/access-denied");
		http.headers().frameOptions().disable();
	}

	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**",
	 * "/js/**", "/images/**", "/bower_components/**", "/dist/**", "/plugins/**"); }
	 */

	@Bean
	public LogoutHandler getLogoutHandler() {
		return new CustomLogoutHandler();
	}

	@Bean
	public LogoutSuccessHandler getLogoutAuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(userNameQuery)
				.authoritiesByUsernameQuery(userRoleQuery);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
