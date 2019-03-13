package com.hbc.sms.cognitiveservices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentsPayload extends Documents {

    private String  language = "en";

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public DocumentsPayload(String id, String text, String language) {
        super(id, text);
        this.language = language;
    }

    @Override
    public String toString() {
        return "DocumentsPayload{" +
                "language='" + language + '\'' +
                super.toString() +
                '}';
    }
}
