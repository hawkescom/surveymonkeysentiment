package com.hbc.sms.cognitiveservices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Documents {

    private String id;
    private String  text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Documents(String id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Documents{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
