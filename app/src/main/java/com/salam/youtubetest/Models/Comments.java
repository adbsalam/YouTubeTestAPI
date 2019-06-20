package com.salam.youtubetest.Models;

public class Comments {
    String authorDisplayName;
    String textDisplay;
    String authorProfileImageUrl;



    public Comments() {
    }

    public Comments(String authorDisplayName, String textDisplay, String authorProfileImageUrl) {
        this.authorDisplayName = authorDisplayName;
        this.textDisplay = textDisplay;
        this.authorProfileImageUrl = authorProfileImageUrl;

    }


    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public String getTextDisplay() {
        return textDisplay;
    }

    public void setTextDisplay(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public String getAuthorProfileImageUrl() {
        return authorProfileImageUrl;
    }

    public void setAuthorProfileImageUrl(String authorProfileImageUrl) {
        this.authorProfileImageUrl = authorProfileImageUrl;
    }
}
