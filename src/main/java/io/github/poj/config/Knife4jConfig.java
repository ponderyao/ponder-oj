package io.github.poj.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4jConfig: 接口文档配置
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Knife4jConfig {

    @Resource
    Environment environment;

    @Bean
    public Docket defaultDocumentationPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfoBuilder()
                .title("接口文档")
                .description(environment.getProperty("spring.application.name"))
                .version(environment.getProperty("application.version"))
                .build())
            .select()
            // 指定 Controller 扫描包路径
            .apis(RequestHandlerSelectors.basePackage("io.github.poj.controller"))
            .paths(PathSelectors.any())
            .build();
    }
    
}
