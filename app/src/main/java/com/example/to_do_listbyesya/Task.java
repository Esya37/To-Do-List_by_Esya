package com.example.to_do_listbyesya;

public class Task {
    private String name;
    private String description;
    private String imageUri;

    public Task(String name, String description, String imageUri) {
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
