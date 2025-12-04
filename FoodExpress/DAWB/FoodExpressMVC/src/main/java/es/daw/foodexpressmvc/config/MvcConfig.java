package es.daw.foodexpressmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
Cuando alguien acceda a estas rutas, no llames a ningún controlador, simplemente renderiza directamente la vista indicada.
Creas mapeos sin lógica de negocio.
Es ideal para páginas estáticas o de presentación.
NO USAR Cuando la vista necesita datos dinámicos, entonces sí necesitas un @Controller.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
        //registry.addViewController("/dashboard").setViewName("dashboard");


    }
}
