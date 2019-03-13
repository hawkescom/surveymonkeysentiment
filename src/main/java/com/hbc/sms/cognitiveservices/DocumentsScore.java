package com.hbc.sms.cognitiveservices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentsScore {
    private String id;
    private String score;

    public DocumentsScore(String id, String score) {
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public DocumentsScore() {
    }
}
