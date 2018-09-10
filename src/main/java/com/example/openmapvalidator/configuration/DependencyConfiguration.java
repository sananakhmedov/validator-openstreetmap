package com.example.openmapvalidator.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.DocumentBuilderFactory;

@Configuration
public class DependencyConfiguration {
    @Bean
    public ObjectMapper objectMapper() { return new ObjectMapper(); }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DocumentBuilderFactory documentBuilderFactory() {
        return DocumentBuilderFactory.newInstance();
    }

    @Bean
    public JaroWinkler jaroWinkler() {
        return new JaroWinkler();
    }

    @Bean
    public Levenshtein levenshtein() { return new Levenshtein(); }

    @Bean
    public Jaccard jaccard() { return new Jaccard(); }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
