package org.springdoc.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springdoc.core.Constants.*;

@Configuration
@EnableWebMvc
@ConditionalOnProperty(name = SPRINGDOC_SWAGGER_UI_ENABLED, matchIfMissing = true)
@SuppressWarnings("deprecation")
public class SwaggerConfig extends WebMvcConfigurerAdapter { // NOSONAR

    @Value(SWAGGER_UI_PATH)
    private String swaggerPath;

    @Value(WEB_JARS_PREFIX_URL)
    private String webJarsPrefixUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uiRootPath = "";
        if (swaggerPath.contains("/")) {
            uiRootPath = swaggerPath.substring(0, swaggerPath.lastIndexOf('/'));
        }
        registry.addResourceHandler(uiRootPath + "/**").addResourceLocations(webJarsPrefixUrl + "/")
                .resourceChain(false);
    }

    @Bean
    @ConditionalOnProperty(name = SPRINGDOC_SWAGGER_UI_ENABLED, matchIfMissing = true)
    public SwaggerWelcome swaggerWelcome() {
        return new SwaggerWelcome();
    }
}
