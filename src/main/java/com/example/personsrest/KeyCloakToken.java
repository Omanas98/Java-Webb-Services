package com.example.personsrest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

// Declaring all essential user information for the UserToken
@Data
public class KeyCloakToken {
    String accessToken;
    int expirationIn;
    int newExpirationIn;
    String newToken;
    String tokenType;
    int notBeforePolicy;
    String sessionState;
    String scope;

    // Assigning the variables to the fetched JSON-properties
    @JsonCreator
    public KeyCloakToken(@JsonProperty("access_token") String accessToken,
                         @JsonProperty("expiration_in") int expirationIn,
                         @JsonProperty("new_expiration_in") int newExpirationIn,
                         @JsonProperty("new_token") String newToken,
                         @JsonProperty("token_type") String tokenType,
                         @JsonProperty("not-before-policy") int notBeforePolicy,
                         @JsonProperty("session_state") String sessionState,
                         @JsonProperty("scope") String scope) {
        this.accessToken = accessToken;
        this.expirationIn = expirationIn;
        this.newExpirationIn = newExpirationIn;
        this.newToken = newToken;
        this.tokenType = tokenType;
        this.notBeforePolicy = notBeforePolicy;
        this.sessionState = sessionState;
        this.scope = scope;
    }

    // Here we're obtaining , keyCloakBaseUrl, then trying to distinguish if this Url
    // matches with any from the API, and if it exists we want to pick out different data attributes
    public static Mono<KeyCloakToken> acquire(String keyCloakBaseUrl, String realm, String clientId,
                                              String username, String password) {
        WebClient webClient = WebClient.builder()
                .baseUrl(keyCloakBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        return webClient.post()
                .uri("auth/realms/" + realm + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("client_id", clientId)
                        .with("username", username)
                        .with("password", password)
                        .with("access_token", ""))
                .retrieve()
                .bodyToFlux(KeyCloakToken.class)
                .onErrorMap(e -> new Exception("Failed to aquire token", e))
                .last();
    }

    // Here we're obtaining a specific User with a name and passwordfrom the API on the specified Url
    public static void main(String[] args) {
        KeyCloakToken token = acquire("https://iam.sensera.se/", "test", "group-api",
                "user", "djnJnPf7VCQvp3Fc")
                .block();
        System.out.println(token);
        System.out.println("KeyCloakToken.main");
        System.out.println(token.accessToken);
        System.out.println("KeyCloakToken.main");
    }
}
