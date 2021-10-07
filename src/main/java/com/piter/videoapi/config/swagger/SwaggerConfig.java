package com.piter.videoapi.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.piter.videoapi.model.Usuario;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	@Bean //Para o SPRING saber que estamos exportando um objeto Docket
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.piter.videoapi.controller"))
				.paths(PathSelectors.any())
				.build()
				.ignoredParameterTypes(Usuario.class) //Ignorar tipo Usuario para nao aparecer a senha lá
				
				//PARAMETRO GLOBAL Para Token das Requisições
				.globalOperationParameters(Arrays.asList(
					new ParameterBuilder()
					.name("Authorization")
					.description("Header para token JWT")
					.modelRef(new ModelRef("string"))
					.required(false)
					.build()
				))
				
				.apiInfo(apiInfo())
				.tags(new Tag("Videos", "Métodos para gerenciamento dos cadastros dos vídeos"));
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
                .title("Videos API")
                .description("Api para cadastro, listagens e gerencimanto de links de videos.")
                .version("1.0.0")
                .build();
	}

}
