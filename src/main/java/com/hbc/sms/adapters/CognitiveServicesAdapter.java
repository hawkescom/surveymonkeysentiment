package com.hbc.sms.adapters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hbc.sms.cognitiveservices.DocumentsRequest;
import com.hbc.sms.cognitiveservices.DocumentsResponse;
import com.hbc.sms.common.APIClient;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

public class CognitiveServicesAdapter  {

    private APIClient client;
    protected ObjectMapper mapper;
    protected String key;
    protected String token;
    protected String url;

    public CognitiveServicesAdapter(String token, String url) {
        this.key = key;
        this.token = token;
        this.url = url;
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.client = new APIClient(token, url);
    }

    public APIClient getClient() {
        return client;
    }

    public void setClient(APIClient client) {
        this.client = client;
    }


    public DocumentsResponse getSentiment(String resource, DocumentsRequest requestPayload) throws IOException {

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mapper.writeValueAsString(requestPayload));
        Request request = new Request.Builder()
                .url(client.getPhoenixUrl(resource))
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key",  client.getAccessToken() )
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        return mapper.readValue(client.newCall(request).execute().body().string(), DocumentsResponse.class);

    }
}
