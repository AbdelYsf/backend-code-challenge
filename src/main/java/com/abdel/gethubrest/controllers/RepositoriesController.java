package com.abdel.gethubrest.controllers;

import com.abdel.gethubrest.domain.Repository;
import com.abdel.gethubrest.service.GitHubService;
import com.abdel.gethubrest.service.GitHubServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RepositoriesController {

    @Autowired
    GitHubService gitHubService;

    @GetMapping("repositories")
    public List<Repository> getRecentRepositories(){
        Optional<List<Repository>> allRepositories = gitHubService.getAllRepositories();
        if (allRepositories.isPresent()){
            return allRepositories.get();
        } else{
            return new ArrayList<>();
        }

    }
}
