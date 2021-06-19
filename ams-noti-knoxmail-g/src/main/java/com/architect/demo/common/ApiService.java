package com.architect.demo.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service 
public class ApiService<T> {
	 
    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private HttpEntity<?> entity;
 
    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
 
    public ResponseEntity<T> get(String url) {
        httpHeaders = new HttpHeaders();
        entity = new HttpEntity<>(httpHeaders);
        return callApiEndpoint(url, HttpMethod.GET, httpHeaders, null, (Class<T>)Object.class);
    }
 
    public ResponseEntity<T> get(String url, Class<T> clazz) {
        return callApiEndpoint(url, HttpMethod.GET, httpHeaders, null, clazz);
    }
 
    public ResponseEntity<T> post(String url, Object body) {
    	httpHeaders = new HttpHeaders();
        entity = new HttpEntity<>(httpHeaders);
        return callApiEndpoint(url, HttpMethod.POST, httpHeaders, body,(Class<T>)Object.class);
    }
 
    public ResponseEntity<T> post(String url, Object body, Class<T> clazz) {
    	httpHeaders = new HttpHeaders();
        entity = new HttpEntity<>(httpHeaders);
        return callApiEndpoint(url, HttpMethod.POST, httpHeaders, body, clazz);
    }
    
    public ResponseEntity<T> patch(String url, Object body) {
        return callApiEndpoint(url, HttpMethod.PATCH, httpHeaders, body,(Class<T>)Object.class);
    }
 
    public ResponseEntity<T> patch(String url,  Object body, Class<T> clazz) {
        return callApiEndpoint(url, HttpMethod.PATCH, httpHeaders, body, clazz);
    }
 
    private ResponseEntity<T> callApiEndpoint(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Object body, Class<T> clazz) {
        return restTemplate.exchange(url, httpMethod, new HttpEntity<>(body, httpHeaders), clazz);
    }
}
