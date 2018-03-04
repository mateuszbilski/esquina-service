package com.squeezedlemon.esquina.service.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.squeezedlemon.esquina.service.filter.AcceptLanguageFilter;

public class WebAppInitializer implements WebApplicationInitializer {
	public void onStartup(ServletContext servletContext) throws ServletException {  
        
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();  
		rootContext.register(AppConfig.class, SecurityConfig.class);
		
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.setInitParameter("defaultHtmlEscape", "true");

		DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		
		ServletRegistration.Dynamic appServlet = servletContext.addServlet("esquina-service", dispatcherServlet);
        appServlet.addMapping("/");  
        appServlet.setLoadOnStartup(1);
        
        FilterRegistration.Dynamic springSecurity = servletContext.addFilter("springSecurityFilterChain",
    		new DelegatingFilterProxy("springSecurityFilterChain", rootContext));
        springSecurity.addMappingForUrlPatterns(null, true, "/*");

        FilterRegistration.Dynamic languageFilter = servletContext.addFilter("accpetLanguageFilter", new AcceptLanguageFilter());
        languageFilter.addMappingForUrlPatterns(null, true, "/*");
   }  
}
