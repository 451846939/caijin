package com.kdkj.caijin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource(locations = {"classpath:applicationContext-shiro.xml"})
public class ShiroConfig {

}
