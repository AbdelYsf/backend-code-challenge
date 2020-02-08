package com.abdel.gethubrest;

import com.abdel.gethubrest.domain.Repository;
import com.abdel.gethubrest.service.GitHubService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class GitHubEndPointTest {


    private static final String GITHUB_ENDPOINT_URI = "https://api.github.com/search/repositories?q=created:%3E2020-01-07&sort=stars&order=desc";

    @Autowired
    GitHubService gitHubService;
    @Test     // testing that the endpoint is up
    public void gitHubEndPoint(){

        //arrange
        
        HttpUriRequest httpUriRequest = new HttpGet(GITHUB_ENDPOINT_URI);

        // act
        HttpResponse httpResponse =null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(httpUriRequest);
            HttpEntity content = httpResponse.getEntity();
            String s = EntityUtils.toString(content);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // assert
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test  // testing that we get a json mime type as response type
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

    @Test
    public void gitHubServiceTest(){

       // failed because of a validation issue from github api
        /*RestTemplate template = new RestTemplate();
       ResponseEntity<String> response = template.getForEntity(GITHUB_ENDPOINT_URI , String.class);
        System.out.println(response.getBody());*/

        Optional<List<Repository>> allRepositories = gitHubService.getAllRepositories();

       if (allRepositories.isPresent()){
           List<Repository> repositories = allRepositories.get();

           for(Repository repo :repositories){
               System.out.println(repo);
           }
       }

       assertEquals(allRepositories.isPresent(),true);



    }

}
