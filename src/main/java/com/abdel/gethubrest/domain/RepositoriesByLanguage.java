package com.abdel.gethubrest.domain;

import java.util.List;

public class RepositoriesByLanguage {

    private int count;
    private List<Repository> repositories;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }
}
