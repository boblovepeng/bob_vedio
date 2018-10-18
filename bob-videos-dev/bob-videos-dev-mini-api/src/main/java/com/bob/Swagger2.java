package com.bob;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggeR2 的一些基本配置
 * @author Administrator
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
   //定义接口的扫描路径
   @Bean 
   public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.bob.controller"))
				.paths(PathSelectors.any()).build();
   }

/**
 * 构建的API文档的信息
 * @return
 */
private ApiInfo apiInfo() {
	
	return new ApiInfoBuilder().title("Swagger2构建的bob短视频API文档")
			.contact(new Contact("bob","wwww","qq.com")).description("welecome to this api").version("1.0").build();
}
   
}
