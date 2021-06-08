package com.example.to_do_listbyesya;

public class Task {
    private String name;
    private String description;
    private String image_url;

    public Task(String name, String description, String image_url)
    {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
    }

    public String get_name()
    {
        return this.name;
    }
    public String get_description()
    {
        return this.description;
    }
    public String get_image_url()
    {
        return this.image_url;
    }

    public void set_name(String name)
    {
        this.name = name;
    }
    public void set_description(String description)
    {
        this.description = description;
    }
    public void set_image_url(String image_url)
    {
        this.image_url = image_url;
    }
}
