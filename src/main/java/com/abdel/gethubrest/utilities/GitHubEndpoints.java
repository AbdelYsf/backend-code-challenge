package com.abdel.gethubrest.utilities;

import org.springframework.stereotype.Component;


public class GitHubEndpoints {


    public static final String ALLREPOS_ENDPOINT_URI = "https://api.github.com/repositories";
    public static final String TRENDING_ENDPOINT_URI = "https://api.github.com/search/repositories?q=created:%3E2020-01-07&sort=stars&order=desc";
}
