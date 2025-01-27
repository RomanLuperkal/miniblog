package org.blog;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Set;

@ComponentScan
public class MiniBlogApplication implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MiniBlogApplication.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        ServletRegistration.Dynamic registration = ctx.addServlet("dispatcher", dispatcherServlet);
        registration.setMultipartConfig(new MultipartConfigElement("/", 5242880,
                20971520, 0));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
