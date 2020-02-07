package com.abdel.gethubrest.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public interface GitHubService {


    public JsonNode getAllRepositoriesAsJSONNODE();
}
