package com.squeezedlemon.esquina.service.config;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableJpaRepositories("com.squeezedlemon.esquina.service.repository")
@ComponentScan(basePackages = {"com.squeezedlemon.esquina.service"})
@EnableWebMvc  
@EnableTransactionManagement
@PropertySource("classpath:app.properties")
public class AppConfig extends WebMvcConfigurerAdapter {

	private static final String HIBERNATE_CACHE_REGION_FACTORY_CLASS = "hibernate.cache.region.factory_class";
	private static final String HIBERNATE_MAX_FETCH_DEPTH = "hibernate.max_fetch_depth";
	private static final String HIBERNATE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
	private static final String HIBERNATE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
	private static final String DATABASE_DRIVER = "db.driver";
	private static final String DATABASE_NAME = "db.name";
    
    private static final String DATABASE_USERNAME = "OPENSHIFT_POSTGRESQL_DB_USERNAME";
    private static final String DATABASE_PASSWORD = "OPENSHIFT_POSTGRESQL_DB_PASSWORD";
    private static final String DATABASE_HOST = "OPENSHIFT_POSTGRESQL_DB_HOST";
    private static final String DATABASE_PORT = "OPENSHIFT_POSTGRESQL_DB_PORT";
 
    private static final String HIBERNATE_DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packagesToScan";
    
    private static final String DATABASE_PREFIX = "jdbc:postgresql://";
    
    @Resource
    private Environment environment;
 
    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        
        dataSource.setDriverClass(environment.getRequiredProperty(DATABASE_DRIVER));
        dataSource.setJdbcUrl(getDatabaseUrl());
        dataSource.setUsername(environment.getRequiredProperty(DATABASE_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(DATABASE_PASSWORD));
 
        return dataSource;
    }
 
    @Bean
    public JpaTransactionManager transactionManager() throws ClassNotFoundException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
 
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject()); 
        return transactionManager;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
 
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(environment.getRequiredProperty(ENTITYMANAGER_PACKAGES_TO_SCAN));
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        Properties jpaProperties = new Properties();
        jpaProperties.put(HIBERNATE_DIALECT, environment.getRequiredProperty(HIBERNATE_DIALECT));
        jpaProperties.put(HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(HIBERNATE_FORMAT_SQL));
        jpaProperties.put(HIBERNATE_NAMING_STRATEGY, environment.getRequiredProperty(HIBERNATE_NAMING_STRATEGY));
        jpaProperties.put(HIBERNATE_SHOW_SQL, environment.getRequiredProperty(HIBERNATE_SHOW_SQL));
        jpaProperties.put(HIBERNATE_DDL_AUTO, environment.getRequiredProperty(HIBERNATE_DDL_AUTO));
        
        jpaProperties.put(HIBERNATE_USE_SECOND_LEVEL_CACHE, environment.getRequiredProperty(HIBERNATE_USE_SECOND_LEVEL_CACHE));
        jpaProperties.put(HIBERNATE_USE_QUERY_CACHE, environment.getRequiredProperty(HIBERNATE_USE_QUERY_CACHE));
        jpaProperties.put(HIBERNATE_MAX_FETCH_DEPTH, environment.getRequiredProperty(HIBERNATE_MAX_FETCH_DEPTH));
        jpaProperties.put(HIBERNATE_CACHE_REGION_FACTORY_CLASS, environment.getRequiredProperty(HIBERNATE_CACHE_REGION_FACTORY_CLASS));
 
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
 
        return entityManagerFactoryBean;
    }
    
    @Bean
	public MessageSource messageSource() {
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("locale/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setFallbackToSystemLocale(false);
		messageSource.setCacheSeconds(60);
		return messageSource;
	}
    
    @Bean
    public LocaleResolver localeResolver() {
    	AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
    	return resolver;
    }
    
    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate4Module());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }
    
    private String getDatabaseUrl() {
    	StringBuilder str = new StringBuilder();
    	str.append(DATABASE_PREFIX)
    		.append(environment.getRequiredProperty(DATABASE_HOST))
    		.append(":")
    		.append(environment.getRequiredProperty(DATABASE_PORT))
    		.append("/")
    		.append(environment.getRequiredProperty(DATABASE_NAME));
    	return str.toString();
    }
}
