package es.smartcoding.ssmvcp4.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import es.smartcoding.ssmvcp4.RootPackage;
import es.smartcoding.ssmvcp4.controllers.SimpleDemoController;

/**
 * Activa el soporte para el modelo de programación basado en clases
 * 
 * @Controller. Tiene el mismo efecto que el elemento <mvc:annotation-driven />
 *              de XML.
 * 
 */

@Configuration
@ComponentScan(basePackageClasses = { RootPackage.class })
public class DispatcherServletConfig extends WebMvcConfigurerAdapter {
	
	/*
	 * Este método añade un controlador que asocia la URL '/login' con la vista
	 * 'login'.
	 * 
	 * Es conveniente en aquellos casos donde el controlador no necesita hacer
	 * ninguna acción más allá de redirigir la petición a la vista como en el
	 * caso del login.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	/**
	 * Interface que implementan las clases que resuelven nombres de vistas
	 * lógicos a físicos
	 */
//	public @Bean ViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass(JstlView.class);
//		viewResolver.setPrefix("/WEB-INF/views/");
//		viewResolver.setSuffix(".jsp");
//		viewResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
//		return viewResolver;
//	}

	/**
	 * Configura rutas de contenido estático como CSS's, JavaScript, ...
	 * 
	 * desde main/webapp/resources y desde el classpath
	 */
	public @Override void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/")
				.setCacheControl(
						CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
		registry.addResourceHandler("/resources/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/")
				.setCacheControl(
						CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
	}

	/**
	 * Cuando el DispatcherServlet se mapea con la raiz '/'(como en este caso)
	 * se modifica el comportamiento por defecto: servir contenido estático.
	 * 
	 * Como este gestor se configura con la precedencia más baja, permite al
	 * resto de los gestores de mapeo de peticiones actual primero y si ninguno
	 * lo hace, este gestor hará un forward al "default" Servlet.
	 */
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * La interfaz MultipartResolver se utiliza para subir ficheros al servidor
	 * según la Request For Comments (RFC) 1867.
	 * 
	 * Existen dos implementaciones concreta incluídas desde Spring 3.1:
	 * CommonsMultipartResolver de Apache Commons FileUpload
	 * StandardServletMultipartResolver de Servlet 3.0+ Part API
	 * 
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
		return multipartResolver;
	}

	@Bean(name = "/demo/*.do")
	public MultiActionController multiActionController() {
		MultiActionController simpleDemoController = new SimpleDemoController();
		return simpleDemoController;
	}
	
	//start Thymeleaf specific configuration
    @Bean(name ="templateResolver")	
    public ServletContextTemplateResolver getTemplateResolver() {
    	ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    	templateResolver.setPrefix("/WEB-INF/thymeleaf/");
    	templateResolver.setSuffix(".html");
    	// XML, VALIDXML, XHTML, VALIDXHTML, HTML5 and LEGACYHTML5
    	templateResolver.setTemplateMode("HTML5");
	return templateResolver;
    }

    @Bean(name ="templateEngine")	    
    public SpringTemplateEngine getTemplateEngine() {
    	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    	templateEngine.setTemplateResolver(getTemplateResolver());
	return templateEngine;
    }

    @Bean(name="viewResolver")
    public ThymeleafViewResolver getViewResolver(){
    	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver(); 
    	viewResolver.setTemplateEngine(getTemplateEngine());
    	viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
	return viewResolver;
    }
    //end Thymeleaf specific configuration


}














///**
//* HandlerMapping
//* 
//* Interfaz que implementan las clases que definen una correspondencia entre
//* peticiones y métodos gestores (Handlers).
//* 
//* ControllerClassNameHandlerMapping es la implementación por defecto
//* 
//* Se trata de una implementación de HandlerMapping que sigue una convención simple
//* a la hora de generar URL's de los nombres de las clases controladores registrados.
//* 
//*/
//@Bean
//public HandlerMapping controllerClassNameHandlerMapping() {
//	ControllerClassNameHandlerMapping handlerMapping = new ControllerClassNameHandlerMapping();
//	return handlerMapping;
//}

/**
 * Forma alternativa de configuración de un view resolver que traduce nombres de
 * vistas lógicos en nombres de vista físicos
 * 
 */
// @Override
// public void configureViewResolvers(ViewResolverRegistry registry) {
// registry.jsp("/WEB-INF/views/", ".jsp");
//
// }

/*
 * InternalPathMethodNameResolver es la implementación por defecto de
 * MethodNameResolver de la clase InternalPathMethodNameResolver, que
 * usa el patrón Strategy.
 * 
 * Otras implementaciones de MethodNameResolver son:
 * ParameterMethodNameResolver y PropertiesMethodNameResolver
 * 
 * Empareja el nombre de recurso después del último '/' e ignorando la
 * extensión.
 * 
 * E.g. "/foo/bar/baz.html" se corresponde con "baz"
 */
// simpleDemoController.setMethodNameResolver(new InternalPathMethodNameResolver());
