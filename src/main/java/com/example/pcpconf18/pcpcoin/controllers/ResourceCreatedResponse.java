package com.example.pcpconf18.pcpcoin.controllers;

public class ResourceCreatedResponse {

    private String resource;

    public static ResourceCreatedResponse of(String resource) {
        return new ResourceCreatedResponse(resource);
    }

    private ResourceCreatedResponse(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
