package com.example.personsrest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Value
public class GroupApi {

    private static final String BASE_URL = "/api/groups/";

    WebTestClient webTestClient;

    public Flux<Group> all() {
        return webTestClient.get().uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Group.class)
                .getResponseBody();
    }

    public Mono<Group> get(String id) {
        return webTestClient.get().uri(BASE_URL + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Group.class)
                .getResponseBody()
                .single();
    }

    public Mono<Group> create(String name) {
        return webTestClient.post().uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new CreateGroup(name)))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Group.class)
                .getResponseBody()
                .single();
    }

    public Mono<Group> update(String id, String name) {
        return webTestClient.put().uri(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new UpdateGroup(name)))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Group.class)
                .getResponseBody()
                .single();
    }

    @Value
    static class Group {
        String id;
        String name;

        @JsonCreator
        public Group(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }
    }

    public Mono<Void> delete(String id) {
        return webTestClient.delete().uri(BASE_URL + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Void.class)
                .getResponseBody()
                .then();
    }

    @Value
    static class CreateGroup {
        String name;
    }

    @Value
    static class UpdateGroup {
        String name;
    }
}