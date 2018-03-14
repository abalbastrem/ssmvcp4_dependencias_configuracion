package es.smartcoding.ssmvcp4.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/");
	}

 @Override
 protected void configure(HttpSecurity http) throws Exception {
// http.authorizeRequests();
// http.authorizeRequests()
// .antMatchers("/", "/home").permitAll()
// 	.anyRequest().authenticated()
// 	.and()
// 		.formLogin()
// .loginPage("/login")
// .permitAll()
// .and()
// .logout()
// .permitAll();
// 	.httpBasic();
	 
	 http.authorizeRequests()
	 	.anyRequest()
	 	.authenticated()
	 	.and()
	 .formLogin()
	 	.loginPage("/login")
	 	.permitAll()
	 	.and()
	 .logout()
	 	.permitAll()
	 	.and()
	 .csrf().disable();
 }
 
 @Autowired
	public void configureGoblal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}
 
}