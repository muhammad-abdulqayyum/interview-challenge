package com.homedepot.interview.challenge.ouinterviewchallenge.service;

import com.homedepot.interview.challenge.ouinterviewchallenge.entity.CryptoExchangeRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CoincapService {

    private final RestTemplate coincapRestTemplate;

    private final String coincapBaseUrl;
    private final String coincapToken;

    public CoincapService(RestTemplate restTemplate,
                            @Value("${coincap.api.url}") String coincapBaseUrl,
                            @Value("${coincap.api.token}") String coincapToken) {
        this.coincapBaseUrl = coincapBaseUrl;
        this.coincapToken = coincapToken;
        this.coincapRestTemplate = restTemplate;
    }


    public CryptoExchangeRate getCryptoExchangeRate(Double input, String from, String to) {

        // TODO: Implement the necessary logic and calls to
        //  Coincap that might be needed to satisfy requirements.
        return null;
    }

    /**
     *  Used for Authorization to Coincap since an API key is required.
     *
     */
    private HttpEntity<HttpHeaders> getCoincapHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + coincapToken);
        headers.add("Accept-Encoding", "deflate");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
