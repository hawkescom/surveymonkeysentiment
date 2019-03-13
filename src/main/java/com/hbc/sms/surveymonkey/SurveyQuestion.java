package com.hbc.sms.surveymonkey;


public class SurveyQuestion {
    private Integer questionNumber;
    private String  answer;
    private String sentiment;

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public SurveyQuestion(Integer questionNumber, String answer, String sentiment) {
        this.questionNumber = questionNumber;
        this.answer = answer;
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "SurveyQuestion{" +
                "questionNumber=" + questionNumber +
                ", answer='" + answer + '\'' +
                ", sentiment='" + sentiment + '\'' +
                '}';
    }
}
