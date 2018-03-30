package com.kdkj.caijin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 该注解就是告诉Spring这个是一个配置文件类，这里配置的Bean要交给Spring去管理。这个就是用来取代Beans.xml这种文件的。
@EnableSwagger2 // 启用 Swagger
@ComponentScan(basePackages = {"com.kdkj.caijin.controller"})
@ConditionalOnExpression("${swagger.enable:true}")    //	开关
public class SwaggerConfig {

    // 访问地址：  http://localhost:8080/swagger-ui.html    //页面上红色的ERROR不用管，那是效验若是本地地址访问就会出现这个信息

    @Value("${server.servlet.context-path}")
    private String pathMapping;

    @Bean
    public Docket createRestApi() {
        // FIXME 通过指定性注解 过滤失败，待调整
//		Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
//			public boolean apply(RequestHandler requestHandler) {
//				Class<?> declaringClass  = requestHandler.getClass();
//				if (declaringClass == BasicErrorController.class)// 排除
//					return false;
//				if(declaringClass.isAnnotationPresent(RestController.class)) // 被注解的类
//					return true;
//				if(declaringClass.isAssignableFrom(ResponseBody.class)) // 被注解的方法
//					return true;
//				return false;
//			}
//		};
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .groupName("caijin") //组名
//               .enable(false)   //禁止使用
//				.securitySchemes(newArrayList(new ApiKey[]{this.apiKey()}))//安全钥匙
//               .securitySchemes(newArrayList(new BasicAuth("test"))) //账号密码登录
//				 .genericModelSubstitutes(DeferredResult.class)//通用的模板替代
                .genericModelSubstitutes(ResponseEntity.class)//通用的模板替代
                .useDefaultResponseMessages(true)//是否使用默认响应信息
                .forCodeGeneration(true)//通用编码
                .pathMapping(pathMapping)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kdkj.caijin.controller"))// 路径性过滤 [这样处理就是扫描controller层的所有方法]
//				.apis(predicate)// 指定性过滤控制只有@RestController和@ResponseBody的才会显示
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("财金APP")//标题
                .description("这是一个财金app")//描述
                .termsOfServiceUrl("www.kaidikeji.com")
                .contact("ＸＸＸ")
                .version("1.0")//版本
//				.license("XXX")//XX
//				.licenseUrl("XXX.html")//XX跳转地址
                .build();
    }

//	ApiKey apiKey() {
//		return new ApiKey("sessionId", "sessionId", "header");
//	}

}