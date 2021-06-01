package com.example.menu;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration

public class MenuConfiguracion extends WsConfigurerAdapter {
    @Bean
    public XsdSchema menuSchema() {
        return new SimpleXsdSchema(new ClassPathResource("menu.xsd"));
    }

    @Bean

    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext)

    {

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();

        servlet.setApplicationContext(applicationContext);

        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean(servlet, "/ws/*");

    }

    @Bean(name = "menu")

    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema menuSchema)

    {

        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();

        wsdl11Definition.setPortTypeName("menuPort");

        wsdl11Definition.setLocationUri("/ws/menu");

        wsdl11Definition.setTargetNamespace("http://tell.me/menu");

        wsdl11Definition.setSchema(menuSchema);

        return wsdl11Definition;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}