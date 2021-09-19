package backvoteschallenge.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .tags(
                        new Tag("Associado", "Endpoints relacionados à este recurso"),
                        new Tag("Pauta", "Endpoints relacionados à este recurso"),
                        new Tag("Sessão", "Endpoints relacionados à este recurso")
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("backvoteschallenge.entities"))
//                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Back Votes Challenge")
                .description("Api for Challenge at South System")
                .version("v1")
                .contact(new Contact("Matheus Carvalho", "https://www.linkedin.com/in/matheus-carvalho69", "matheus9126@gmail.com"))
                .build();
    }


}
