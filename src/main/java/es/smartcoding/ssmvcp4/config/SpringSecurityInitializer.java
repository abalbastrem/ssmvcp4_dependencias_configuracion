package es.smartcoding.ssmvcp4.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	/*
	 * Crea el bean springSecurityFilterChain de tipo
	 * org.springframework.web.filter.DelegatingFilterProxy automáticamente.
	 * 
	 * Esta clase implementa javax.servlet.Fitler, por lo tanto es un filtro,
	 * mejor dicho un Proxy de un filtro Servlet estándar que delega en un bean
	 * gestionado por Spring (springSecurityFilterChain) el cual implementa la
	 * interfaz javax.servlet.Filter
	 * 
	 * Este filtro intercepta todas las peticiones y verifica que cumplan los
	 * requerimientos de seguridad, si no es así interrumpe la petición y la
	 * redirige a un formulario creado automáticamente donde solicita las
	 * credenciales.
	 * 
	 * Una vez autenticado comprueba que el usuario tenga autorización para
	 * acceder al recurso que solicita, si es así, continúa con la petición
	 * inicial.
	 */
}