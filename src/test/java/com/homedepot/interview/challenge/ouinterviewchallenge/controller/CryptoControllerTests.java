package com.homedepot.interview.challenge.ouinterviewchallenge.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CryptoExchangeRate;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.homedepot.interview.challenge.ouinterviewchallenge.TestUtils.loadResourceAsString;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 9080)
public class CryptoControllerTests {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Test
    void test_BTC_to_DOGE() {

        String btcSearch = "/v2/assets?search=BTC";
        String dogeSearch = "/v2/assets?search=DOGE";

        stubFor(get(urlEqualTo(btcSearch))
            .withHeader("Authorization", equalTo("Bearer foo"))
                .willReturn(
                    aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(loadResourceAsString("/fixtures/fakeBTC.json"))
                ));

        stubFor(get(urlEqualTo(dogeSearch))
            .withHeader("Authorization", equalTo("Bearer foo"))
                .willReturn(
                    aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(loadResourceAsString("/fixtures/fakeDOGE.json"))
                ));

        String testUrl = "/api/crypto?amount=10&from=BTC&to=DOGE";
        ResponseEntity<CryptoExchangeRate> actual = testRestTemplate.getForEntity(testUrl, CryptoExchangeRate.class);

        double expectedAmountDoge = (10 * 100) / (0.15);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(actual.getBody()).getToAmount())
            .isCloseTo(expectedAmountDoge, Offset.offset(0.01));
    }
}
