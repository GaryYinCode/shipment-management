package club.showx.interview.shipment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket customDocket() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        //
        return new Docket(DocumentationType.SWAGGER_2).
                globalOperationParameters(parameters).
                apiInfo(apiInfo()).
                select().
                apis(RequestHandlerSelectors.basePackage("club.showx.interview.shipment.controller")).
                build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("WebService Shipment APIs").description("WebService Shipment APIs Docs").version("2.0").build();
    }
}