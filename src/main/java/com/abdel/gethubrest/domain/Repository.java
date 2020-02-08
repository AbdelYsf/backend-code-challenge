package com.abdel.gethubrest.domain;

import org.springframework.stereotype.Component;

@Component
public class Repository {

    private String id;
    private String name;
    private String fullName;
    private String owener;
    private String owenerType;
    private String url;
    private String description;
    private String language;
    private String dateOfCreation;
    private String dateOfLastModification;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDateOfLastModification() {
        return dateOfLastModification;
    }

    public void setDateOfLastModification(String dateOfLastModification) {
        this.dateOfLastModification = dateOfLastModification;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", owener='" + owener + '\'' +
                ", owenerType='" + owenerType + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                ", dateOfLastModification='" + dateOfLastModification + '\'' +
                '}';
    }
}
