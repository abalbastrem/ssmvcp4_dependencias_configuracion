package es.smartcoding.ssmvcp4.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
// Enables Method level security in Spring Security with @PreAuthorize,
// @PostAuthorize, @Secured and Spring EL expressions.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
	
		// 2 //
//		 http.authorizeRequests()
//		 	.anyRequest()
//		 	.authenticated()
//		 	.and()
//		 .formLogin()
//		 	.loginPage("/login")
//		 	.permitAll()
//		 	.and()
//		 .logout()
//		 	.permitAll()
//		 	.and()
//		 .csrf().disable();
		
		// 3. amb csrf token //
		http
		.authorizeRequests()
			.anyRequest().authenticated().and()
			.formLogin().loginPage("/login").permitAll().and()
			.logout().permitAll().and()
			.csrf().csrfTokenRepository(csrfTokenRepository());
			
	 }
	 
	@Autowired
	public void configureGoblal(AuthenticationManagerBuilder auth) throws Exception {
		// Autenticación em memoria
//		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		
		// Autenticación conectando a BBDD MySQL
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
		.authoritiesByUsernameQuery(
				"SELECT u.username AS username, r.name AS authority FROM users u, roles r, roles_users ru WHERE u.id=ru.user_id AND r.id=ru.role_id AND username=?");
	}
	
	/*
	 * Este método guarda el token CRSF (Cross-site Request Forgery)
	 * con el que está asociado la petición en la sesión
	 */
	private CsrfTokenRepository csrfTokenRepository() {
		// Implementación de la interfaz CsrfTokenRepository que guarda un CrsfToken en la HttpSession
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setSessionAttributeName("_csrf");
		return repository;
	}
	 
}