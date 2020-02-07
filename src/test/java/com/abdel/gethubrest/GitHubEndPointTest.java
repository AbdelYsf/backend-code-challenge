package com.abdel.gethubrest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class GitHubEndPointTest {


    public static final String GITHUB_ENDPOINT_URI = "https://api.github.com/search/repositories?q=created:%3E2020-01-07";

    @Test     // testing that the endpoint is up
    public void gitHubEndPoint(){

        //arrange
        
        HttpUriRequest httpUriRequest = new HttpGet(GITHUB_ENDPOINT_URI);

        // act
        HttpResponse httpResponse =null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(httpUriRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // assert
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void gitHubEndPointResponseType(){

        //arrange
        HttpUriRequest httpUriRequest = new HttpGet(GITHUB_ENDPOINT_URI);

        // act
        HttpResponse httpResponse =null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(httpUriRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // assert
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals( "application/json", mimeType );
    }
}
