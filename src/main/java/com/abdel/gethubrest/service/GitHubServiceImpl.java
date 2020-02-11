package com.abdel.gethubrest.service;

import com.abdel.gethubrest.domain.Repository;
import com.abdel.gethubrest.utilities.GitHubEndpoints;
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
import java.util.*;


@Service
public class GitHubServiceImpl implements GitHubService {
    Logger logger = LoggerFactory.getLogger(GitHubServiceImpl.class);


    @Override
    public Optional<List<Repository>> getTrendingRepositories() {
        
        return repositoriesHandler(GitHubEndpoints.TRENDING_ENDPOINT_URI);
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
        ObjectMapper mapper = new ObjectMapper();
        try {



           String responseBody =getResponseAsString(endpointUri);
            JsonNode node = mapper.readTree(responseBody);

            JsonNode items;
            if(GitHubEndpoints.ALLREPOS_ENDPOINT_URI ==endpointUri){
                items =node;
            }else {
                items =node.path("items");
            }

            if (items.isArray()){
                Repository repository;
                for (final JsonNode repo : items) {
                    // temp repo
                    repository= new Repository();

                    // matching values

                    repository.setId(repo.path("id").asText());
                    repository.setName(repo.path("name").asText());
                    repository.setFullName(repo.path("full_name").asText());
                    repository.setOwener(repo.path("owner").path("login").asText());
                    repository.setOwenerType(repo.path("owner").path("type").asText());
                    repository.setDescription(repo.path("description").asText());
                    repository.setUrl(repo.path("html_url").asText());

                    //
                    String languages_url=repo.path("languages_url").asText();
                    // fetching the language used
                    responseBody = getResponseAsString(languages_url);

                    Map<String,String> langs= mapper.readValue(responseBody, Map.class);
                        if(!langs.isEmpty()){
                            repository.setLanguages(new ArrayList<>(langs.keySet()));
                        }else{
                            repository.setLanguages(new ArrayList<>());
                        }

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
    private String getResponseAsString(String uri ){
        String responseBody ="";
        try{
            HttpUriRequest httpUriRequest = new HttpGet(uri);
            HttpResponse httpResponse ;
            // sending get request and getting the response
            httpResponse = HttpClientBuilder.create().build().execute(httpUriRequest);
            HttpEntity content = httpResponse.getEntity();
            // reading the response body as the a string
            responseBody= EntityUtils.toString(content);
        }catch (Exception e){
            logger.error("Exception happend! while reading the respons as string ");

        }

        return responseBody;
    }

    // returning a hashMap that contains the name of language as key and a list of its repos as values
    private HashMap<String,List<Repository>> reposByLanguage(){

        Optional<List<Repository>> result = repositoriesHandler(GitHubEndpoints.ALLREPOS_ENDPOINT_URI);
        HashMap<String, List<Repository>> reposByLanguage = new HashMap<>();

        if (result.isPresent()){
            List<Repository> repositories = result.get();
            // calculate language per repo
            for(Repository repository : repositories)
            {
                 List<String> languages= repository.getLanguages();
                 if(!languages.isEmpty()){
                     languages.forEach(lang->{
                         if(!reposByLanguage.containsKey(lang.toLowerCase())) {
                             reposByLanguage.put(lang.toLowerCase(), new ArrayList<>());
                         }
                         reposByLanguage.get(lang.toLowerCase()).add(repository);
                     });
                 }
            }
        }
             return reposByLanguage;
    }
}
