package com.rcs.config;


import com.rcs.util.ReadJsonFileToJsonObject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@OpenAPIDefinition
@Configuration
@AllArgsConstructor
public class OpenApiConfig {
    private final ReadJsonFileToJsonObject readJsonFileToJsonObject;

    @Bean
    public OpenAPI springShopOpenAPI() {

        // read json from file and set to controller response example
    /*ApiResponse vRequestAPI = new ApiResponse().content(
    	new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE ,
    	new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
    	new Example().value(readJsonFileToJsonObject.read().get("vehicleView").toString())))
    ).description("Success Request");*/

        // build global default 400 error response for swagger documentation
        ApiResponse badRequestAPI =
                new ApiResponse()
                        .content(
                                new Content()
                                        .addMediaType(
                                                MediaType.APPLICATION_JSON_VALUE,
                                                new io.swagger.v3.oas.models.media.MediaType()
                                                        .addExamples(
                                                                "default",
                                                                new Example()
                                                                        .value(
                                                                                "{\"code\" : 400, \"Status\" : \"Bad Request\", \"Message\": \"Bad Request\"}"))))
                        .description("Bad Request");

        // buil global default 500 error response for swagger documentation
        ApiResponse internalServerErrorAPI =
                new ApiResponse()
                        .content(
                                new Content()
                                        .addMediaType(
                                                MediaType.APPLICATION_JSON_VALUE,
                                                new io.swagger.v3.oas.models.media.MediaType()
                                                        .addExamples(
                                                                "default",
                                                                new Example()
                                                                        .value(
                                                                                "{\"code\" : 500, \"Status\" : \"Internal Server Error\", \"Message\": \"Internal Server Error\"}"))))
                        .description("Internal Server Error");

        Components components = new Components();
        components.addResponses("badRequestAPI", badRequestAPI);
        components.addResponses("internalServerErrorAPI", internalServerErrorAPI);
        // components.addResponses("VRequestAPI", vRequestAPI);

        return new OpenAPI()
                .components(components)
                .info(
                        new Info()
                                .title("delership API")
                                .description(
                                        "delership application implemented with Spring Boot 3 and Java 17 :) ")
                                .version("v0.0.1")
                        // .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }
}
