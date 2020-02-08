package com.abdel.gethubrest.service;




import com.abdel.gethubrest.domain.Repository;

import java.util.List;
import java.util.Optional;


public interface GitHubService {


     Optional<List<Repository>> getAllRepositories();
     Optional<List<Repository>> getRepositoriesByLanguage(String language);
}
