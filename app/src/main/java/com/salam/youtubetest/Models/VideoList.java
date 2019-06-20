package com.salam.youtubetest.Models;

public class VideoList {
    String title;
    String publishedAt;
    String videoid;
    String url;
    String description;

    public VideoList(String title, String publishedAt, String videoid, String url, String description) {
        this.title = title;
        this.publishedAt = publishedAt;
        this.videoid = videoid;
        this.url  = url;
        this.description = description;

    }


    public VideoList() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }
}
