package com.hbc.sms.cognitiveservices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Errors {

    private String id;
    private String  message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return message;
    }

    public void setText(String text) {
        this.message = text;
    }

    public Errors(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public Errors() {
    }
}
