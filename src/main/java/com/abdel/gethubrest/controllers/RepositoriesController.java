package com.abdel.gethubrest.controllers;

import com.abdel.gethubrest.domain.RepositoriesByLanguage;
import com.abdel.gethubrest.domain.Repository;
import com.abdel.gethubrest.service.GitHubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("repositories")
public class RepositoriesController {

    @Autowired
    GitHubService gitHubService;
    private static final Logger logger = LoggerFactory.getLogger(RepositoriesController.class);

    @GetMapping("repositories/trending")
    public List<Repository> trendingRepositories(){
        Optional<List<Repository>> allRepositories = gitHubService.getTrendingRepositories();
        if (allRepositories.isPresent()){
            logger.info("Trending repositories is called ");
            return allRepositories.get();
        } else{
            logger.warn("Trending repositories sending empty result !!");
            return new ArrayList<>();
        }
    }

    @GetMapping("repositoriesbylang/{language}")
    public RepositoriesByLanguage RepositoriesByLanguage(@PathVariable String language){
        Optional<List<Repository>> allRepositories = gitHubService.getRepositoriesByLanguage(language);
        RepositoriesByLanguage reposByLanguage = new RepositoriesByLanguage();
        if (allRepositories.isPresent()){
            logger.info("RepositoriesByLanguage is called ");
            reposByLanguage.setLanguage(language);
            reposByLanguage.setRepositories(allRepositories.get());
            reposByLanguage.setCount(allRepositories.get().size());
            return reposByLanguage;
        } else{
            logger.warn("RepositoriesByLanguage sending empty result !!");
            return reposByLanguage;
        }

    }

}
