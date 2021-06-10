package com.example.to_do_listbyesya;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Task {
    private String name;
    private String description;
    private String image_uri;

    public Task(String name, String description, String image_uri)
    {
        this.name = name;
        this.description = description;
        this.image_uri = image_uri;
    }

    public String get_name()
    {
        return this.name;
    }
    public String get_description()
    {
        return this.description;
    }
    public String get_image_uri()
    {
        return this.image_uri;
    }

    public void set_name(String name)
    {
        this.name = name;
    }
    public void set_description(String description)
    {
        this.description = description;
    }
    public void set_image_uri(String image_url)
    {
        this.image_uri = image_url;
    }
}
