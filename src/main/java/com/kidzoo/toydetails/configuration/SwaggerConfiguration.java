package com.kidzoo.toydetails.configuration;
import java.util.List;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fasterxml.classmate.TypeResolver;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfiguration.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    /** The version number. */
    @Value("${info.build.version}")
    private String versionNumber;

    /** The project name. */
    @Value("${info.build.projectName}")
    private String projectName;

    private final TypeResolver typeResolver;

    @Autowired
    public SwaggerConfiguration(final TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    /**
     * Generates documentation for all the services under
     * com.kidzoo.toydetails.controller
     *
     * @return the docket
     */
    @Bean
    public Docket toyDetailsApi() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.kidzoo.toydetails"))
                .paths(PathSelectors.any()).build().useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, defaultMessages())
                .globalResponseMessage(RequestMethod.POST, defaultMessages())
                .globalResponseMessage(RequestMethod.PATCH, defaultMessages())
                .globalResponseMessage(RequestMethod.PUT, defaultMessages())
                .globalResponseMessage(RequestMethod.DELETE, defaultMessages()).forCodeGeneration(true);
    }

    /**
     * Api info.
     *
     * @return the api info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(projectName).description("ToyDetails API service operations")
                .version(versionNumber).build();
    }

    private List<ResponseMessage> defaultMessages() {
        ModelRef errorRef = new ModelRef("ErrorResponse");

        return Lists.newArrayList(
                new ResponseMessageBuilder().code(400).message("Bad Request").responseModel(errorRef).build(),
                new ResponseMessageBuilder().code(401).message("Unauthorized").responseModel(errorRef).build(),
                new ResponseMessageBuilder().code(404).message("Not Found").responseModel(errorRef).build(),
                new ResponseMessageBuilder().code(500).message("Internal Server Error").responseModel(errorRef).build(),
                new ResponseMessageBuilder().code(503).message("Service Unavailable").responseModel(errorRef).build());
    }
}
