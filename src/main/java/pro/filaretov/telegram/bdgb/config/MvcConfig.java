package pro.filaretov.telegram.bdgb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Wev MVC configuration.
 */
@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String REACT_BUILD_DIR = "/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(REACT_BUILD_DIR + "static/");
        registry.addResourceHandler("/*.js").addResourceLocations(REACT_BUILD_DIR);
        registry.addResourceHandler("/*.json").addResourceLocations(REACT_BUILD_DIR);
        registry.addResourceHandler("/*.ico").addResourceLocations(REACT_BUILD_DIR);
        registry.addResourceHandler("/index.html").addResourceLocations(REACT_BUILD_DIR + "index.html");
    }
}
