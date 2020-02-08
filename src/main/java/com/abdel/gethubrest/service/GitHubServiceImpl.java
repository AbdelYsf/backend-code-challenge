package com.abdel.gethubrest.service;

import com.abdel.gethubrest.domain.Repository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GitHubServiceImpl implements GitHubService {
    Logger logger = LoggerFactory.getLogger(GitHubServiceImpl.class);
    private static final String GITHUB_ENDPOINT_URI = "https://api.github.com/search/repositories?q=created:%3E2020-01-07&sort=stars&order=desc";


    @Override
    public Optional<List<Repository>> getAllRepositories() {
        HttpUriRequest httpUriRequest = new HttpGet(GITHUB_ENDPOINT_URI);
        HttpResponse httpResponse =null;
        List<Repository> repositories=new ArrayList<>();

        try {
            httpResponse = HttpClientBuilder.create().build().execute(httpUriRequest);
            HttpEntity content = httpResponse.getEntity();
            String responseBody = EntityUtils.toString(content);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseBody);
            JsonNode items = node.path("items");
            if (items.isArray()){
                Repository repository= null;
                for (final JsonNode repo : items) {
                    // temp repo
                    repository= new Repository();

                    // matching

                    repository.setId(repo.path("id").asText());
                    repository.setName(repo.path("name").asText());
                    repository.setFullName(repo.path("full_name").asText());
                    repository.setDateOfCreation(repo.path("created_at").asText());
                    repository.setDateOfLastModification(repo.path("updated_at").asText());
                    repository.setLanguage(repo.path("language").asText());
                    repository.setOwener(repo.path("owner").path("login").asText());
                    repository.setOwenerType(repo.path("owner").path("type").asText());
                    repository.setDescription(repo.path("description").asText());
                    repository.setUrl(repo.path("html_url").asText());

                    // feeding the list
                    repositories.add(repository);

                }
            }

            return Optional.ofNullable(repositories);
        } catch (IOException e) {
            logger.error(" Exception happened! while getting all repos",e);
            return Optional.empty();
        }
    }
}
