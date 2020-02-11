package com.abdel.gethubrest;

import com.abdel.gethubrest.domain.Repository;
import com.abdel.gethubrest.service.GitHubService;

import com.abdel.gethubrest.utilities.GitHubEndpoints;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class GitHubEndPointTest {

    private static final Logger logger = LoggerFactory.getLogger(GitHubEndPointTest.class);

    @Autowired
    GitHubService gitHubService;
    @Test     // ensuring that github endpoint is up
    public void gitHubEndPoint(){

        //arrange
        
        HttpUriRequest httpUriRequest = new HttpGet(GitHubEndpoints.TRENDING_ENDPOINT_URI);

        // act
        HttpResponse httpResponse =null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(httpUriRequest);
            HttpEntity content = httpResponse.getEntity();
            String s = EntityUtils.toString(content);
            logger.info(s);
        } catch (IOException e) {

            logger.error(" Exception happened! : in getHubEndPoint Test",e);
        }


        // assert that we get an OK status
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test  // ensuring that we get a json mime type as response type
    public void gitHubEndPointResponseType(){

        //arrange
        HttpUriRequest httpUriRequest = new HttpGet(GitHubEndpoints.TRENDING_ENDPOINT_URI);

        // act
        HttpResponse httpResponse =null;
        try {
            httpResponse = HttpClientBuilder.create().build().execute(httpUriRequest);
        } catch (IOException e) {
            logger.error(" Exception happened! : in gitHubEndPointResponseType Test",e);
        }

        // assert
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals( "application/json", mimeType );
    }

    @Test // ensuring that we get a non empty result result
    public void gitHubServiceTest(){

        Optional<List<Repository>> allRepositories = gitHubService.getTrendingRepositories();

       if (allRepositories.isPresent()){
           List<Repository> repositories = allRepositories.get();

           for(Repository repo :repositories){
               logger.info(repo.toString());
           }
       }
       assertEquals(allRepositories.isPresent(),true);

    }



}
