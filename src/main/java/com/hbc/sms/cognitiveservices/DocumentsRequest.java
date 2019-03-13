package com.hbc.sms.cognitiveservices;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentsRequest {
    private List<DocumentsPayload> documents;

    public List<DocumentsPayload> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentsPayload> documents) {
        this.documents = documents;
    }

    public DocumentsRequest(List<DocumentsPayload> documents) {
        this.documents = documents;
    }

}
