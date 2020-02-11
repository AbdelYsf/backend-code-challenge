package com.abdel.gethubrest.utilities;

import org.springframework.stereotype.Component;


public class GitHubEndpoints {

    /* for all repositories i had to use his https://api.github.com/repositories
    * but i did not pay attention at the start
    *so i used this endpoint 'https://api.github.com/search/repositories?q=created'
    * which returns some repos (the functionality demanded is applied on these repos )
    *
    * */
    public static final String BASE_ENDPOINT_URI = "https://api.github.com/search/repositories?q=created";
    public static final String TRENDING_ENDPOINT_URI = "https://api.github.com/search/repositories?q=created:%3E2020-01-07&sort=stars&order=desc";
}
