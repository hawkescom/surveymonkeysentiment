package com.hbc.sms.surveymonkey;

import java.util.List;

public class SurveyResponses {
    private String id;
    private List<String> questions;
    private List<SurveyQuestion> responses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<SurveyQuestion> getResponses() {
        return responses;
    }

    public void setResponses(List<SurveyQuestion> responses) {
        this.responses = responses;
    }

    public SurveyResponses(String id, List<String> questions, List<SurveyQuestion> responses) {
        this.id = id;
        this.questions = questions;
        this.responses = responses;
    }
}
