package com.Ambition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment) {
        //设置要显示的Swagger环境 可以选择多个
        Profiles profiles = Profiles.of("test","dev");

        //通过environment.acceptsProfiles判断是否处在自己设计的环境当中
        environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //关闭Swagger
                //.enable(false)
                .select()
                //RequestHandlerSelectors  配置要扫描接口的方式
                //basePackage() 指定扫描包  .apis(RequestHandlerSelectors.basePackage("com.Ambition.swagger.controller"))
                //any() 都扫描
                //none() 不扫描
                //withClassAnnotation() 扫描类上的注解 参数是一个注解的反射对象  .apis(RequestHandlerSelectors.withClassAnnotation(ResController.class))
                //withMethodAnnotation() 扫描方法上的注解 .apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class))
                .apis(RequestHandlerSelectors.basePackage("com.Ambition"))
                //过滤路径下的东西    .paths(PathSelectors.ant("Ambition/**"))
                //不过滤
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("Ambition","https://zsedcc1535.github.io/","2207750450@qq.com");
        return new ApiInfo(
                "Ambition的Swagger日志",
                "我们必须接受失望，因为它是有限的，但千万不可失去希望，因为它是无穷的。",
                "1.0",
                "https://zsedcc1535.github.io/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}
