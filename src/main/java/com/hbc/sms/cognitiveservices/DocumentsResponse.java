package com.hbc.sms.cognitiveservices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentsResponse {
    private List<DocumentsScore> documents;
    private List<Errors> errors;

    public List<DocumentsScore> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentsScore> documents) {
        this.documents = documents;
    }

    public List<Errors> getErrors() {
        return errors;
    }

    public void setErrors(List<Errors> errors) {
        this.errors = errors;
    }

    public DocumentsResponse(List<DocumentsScore> documents, List<Errors> errors) {
        this.documents = documents;
        this.errors = errors;
    }

    public DocumentsResponse() {
    }
}
