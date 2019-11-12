package com.miscot.springmvc.configuration;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
 




@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.miscot.springmvc")
@PropertySource(value = { "classpath:application.properties" })
public class SpringConfiguration  extends WebMvcConfigurerAdapter{
	 @Autowired
	    private Environment env;
	 	@Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
	        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
	        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
	        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
	        return dataSource;
	    }
	 
	    @Bean(name="jdbcTemplate")
	    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        jdbcTemplate.setResultsMapCaseInsensitive(true);
	        return jdbcTemplate;
	    }
	    ///View Resolver
	    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	        configurer.ignoreAcceptHeader(true).defaultContentType(
	                MediaType.TEXT_HTML);
	    }
	 
	    /*
	     * Configure ContentNegotiatingViewResolver
	     */
	    @Bean
	    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
	        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
	        resolver.setContentNegotiationManager(manager);
	 
	        // Define all possible view resolvers
	        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
	 
	        //resolvers.add(jaxb2MarshallingXmlViewResolver());
	        
	        resolvers.add(jspViewResolver());
	       
	         
	        resolver.setViewResolvers(resolvers);
	        return resolver;
	    }
	    
	    
	    @Bean
	    public PropertiesPath getPropertiesPath() {
		 PropertiesPath propertyPath=new PropertiesPath();
		 propertyPath.setDomain(env.getRequiredProperty("domain"));
		 propertyPath.setLdapHost(env.getRequiredProperty("ldapHost"));
		 propertyPath.setSearchBase(env.getRequiredProperty("searchBase"));
		 propertyPath.setLdapUsername(env.getRequiredProperty("ldapUsername"));
		 propertyPath.setLdapPassword(env.getRequiredProperty("ldapPassword"));
		 propertyPath.setAdhVlt_userID(env.getRequiredProperty("adhVlt_userID"));
		 
		 propertyPath.setAdhVlt_password(env.getRequiredProperty("adhVlt_password"));
		 propertyPath.setAdhVlt_lk(env.getRequiredProperty("adhVlt_lk"));
		 propertyPath.setAdhVlt_version(env.getRequiredProperty("adhVlt_version"));
		 propertyPath.setIsadauth(env.getRequiredProperty("isadauth"));
		 propertyPath.setStoreadhaaruserid(env.getRequiredProperty("storeadhaaruserid"));
		 propertyPath.setStoreadhaarpasswd(env.getRequiredProperty("storeadhaarpasswd"));
		 propertyPath.setAadharPublicCertificate(env.getRequiredProperty("aadharPublicCertificate"));
		 propertyPath.setStoreAadhar(env.getRequiredProperty("storeAadhar"));
		 propertyPath.setRetrieveAadhar(env.getRequiredProperty("retrieveAadhar"));
		 propertyPath.setCertpath(env.getRequiredProperty("certpath"));
		 propertyPath.setDatatype(env.getRequiredProperty("datatype"));
		 return propertyPath;
	    }
		
	    @Bean
	    public ViewResolver jspViewResolver() {
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setViewClass(JstlView.class);
	        viewResolver.setPrefix("/WEB-INF/views/");
	        viewResolver.setSuffix(".jsp");
	        return viewResolver;
	    }
	    
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
	    }
	 

}