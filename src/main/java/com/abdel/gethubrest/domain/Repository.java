package com.abdel.gethubrest.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Repository {

    private String id;
    private String name;
    private String fullName;
    private String owener;
    private String owenerType;
    private String url;
    private String description;
    private List<String> languages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOwener() {
        return owener;
    }

    public void setOwener(String owener) {
        this.owener = owener;
    }

    public String getOwenerType() {
        return owenerType;
    }

    public void setOwenerType(String owenerType) {
        this.owenerType = owenerType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
}
