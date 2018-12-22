package com.example.scoutsapi.controllers;

import com.example.scoutsapi.model.Members;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureWebTestClient(timeout = "36000")
public class MembersIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private String insertedId;


    @Test
    public void createMemberSuccess(){
        final Members memberRecord = new Members( "5c1a9c97c1ae12366891aa97", "TestX UserX2", "testx.userx@x.com", "male", "12", "123-456-7890" );

        webTestClient.post()
                .uri("/members/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(memberRecord), Members.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$._id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("TestX UserX2");

    }

    @Test
    public void getAllMemberSuccess()  {
        webTestClient.get()
                .uri("/members/")
                .exchange().expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(response -> Assertions.assertThat(response.getResponseBody().length).isGreaterThan(0));
    }

    @Test
    public void getMemberByIDSuccess() {
        Members testMember = webTestClient.get()
                .uri("/members/{id}", "5c1a9c97c1ae12366891aa97")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Members.class)
                .getResponseBody()
                .blockFirst();
        assert testMember.getMemberId().equals("5c1a9c97c1ae12366891aa97");
    }

    @Test
    public void memberUpdateSuccess() {
        final Members updatedRecord = new Members( "5c1a9c97c1ae12366891aa97", "TestX UserX3", "testx.userx@x.com", "male", "12", "123-456-7890" );
        webTestClient.put()
                .uri("/members/{id}", "5c1a9c97c1ae12366891aa97")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(updatedRecord), Members.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.name").isEqualTo("TestX UserX3");
    }

    @Test
    public void removeMemberSuccess() {
        webTestClient.delete()
                .uri("/members/{id}", "5c1a9c97c1ae12366891aa97")
                .exchange()
                .expectStatus().isNoContent();
    }

}
