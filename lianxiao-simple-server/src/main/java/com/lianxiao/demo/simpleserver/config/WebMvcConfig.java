package com.lianxiao.demo.simpleserver.config;

import com.lianxiao.demo.simpleserver.Interceptor.AuthenticationInterceptor;
import com.lianxiao.demo.simpleserver.Interceptor.IpLimitInterceptor;
import com.lianxiao.demo.simpleserver.Interceptor.LoginArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Bean
    public AuthenticationInterceptor createAuthenticationInterceptor(){
        return new AuthenticationInterceptor();
    }

    @Bean
    public IpLimitInterceptor createIpLimitInterceptor(){
        return new IpLimitInterceptor();
    }

    @Bean
    public LoginArgumentResolver createLoginArgumentResolver(){
        return new LoginArgumentResolver();
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(
                StandardCharsets.UTF_8);
    }

    //  参数解析器
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createLoginArgumentResolver());
    }


    //  资源处理器
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    //  拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createAuthenticationInterceptor())
                .addPathPatterns("/student/**","/chat/**","/friends/**")
                .excludePathPatterns("/student/open/**","/student/login/*","/student/register_or_login/*");
        registry.addInterceptor(createIpLimitInterceptor()).addPathPatterns("/**").excludePathPatterns("/swagger-ui.html/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
}