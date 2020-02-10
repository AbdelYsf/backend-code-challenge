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
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class GitHubServiceImpl implements GitHubService {
    Logger logger = LoggerFactory.getLogger(GitHubServiceImpl.class);
    private static final String BASE_ENDPOINT_URI = "https://api.github.com/search/repositories?q=created";
    private static final String TRENDING_ENDPOINT_URI = "https://api.github.com/search/repositories?q=created:%3E2020-01-07&sort=stars&order=desc";

    @Override
    public Optional<List<Repository>> getTrendingRepositories() {
        
        return repositoriesHandler(TRENDING_ENDPOINT_URI);
    }

    @Override
    public Optional<List<Repository>> getRepositoriesByLanguage(String language) {
        language = language.toLowerCase();
        HashMap<String,List<Repository>>  reposByLanguage=reposByLanguage();

            if(!reposByLanguage.isEmpty()){
                List<Repository> langRepos = reposByLanguage.get(language);
                return Optional.ofNullable(langRepos);
            }
        return Optional.empty();
    }

    private Optional<List<Repository>> repositoriesHandler(String endpointUri){

        List<Repository> repositories=new ArrayList<>();
        HttpUriRequest httpUriRequest = new HttpGet(endpointUri);
        HttpResponse httpResponse ;
        try {
            // sending get request and getting the response
            httpResponse = HttpClientBuilder.create().build().execute(httpUriRequest);
            HttpEntity content = httpResponse.getEntity();
            // reading the response body as the a string
            String responseBody = EntityUtils.toString(content);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseBody);
            // fetching the list of repositories
            JsonNode items = node.path("items");
            if (items.isArray()){
                Repository repository;
                for (final JsonNode repo : items) {
                    // temp repo
                    repository= new Repository();

                    // matching values

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

    // returning a hashMap that contains the name of language as key and a list of its repos as values
    private HashMap<String,List<Repository>> reposByLanguage(){

        Optional<List<Repository>> result = repositoriesHandler(BASE_ENDPOINT_URI);
        HashMap<String, List<Repository>> reposByLanguage = new HashMap<>();

        if (result.isPresent()){
            List<Repository> repositories = result.get();
            // calculate language per repo
            repositories.forEach(repository ->
            {
                String lang = repository.getLanguage().toLowerCase();
                if(!reposByLanguage.containsKey(lang)) {
                    reposByLanguage.put(lang, new ArrayList<>());
                }
                reposByLanguage.get(lang).add(repository);
            });
        }
             return reposByLanguage;
    }
}
