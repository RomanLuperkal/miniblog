package org.testconfiguration;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Profile("test")
@Import({H2Configuration.class})
@ComponentScan("org.blog")
public class WebConfiguration {
}
