package com.wolves.mainproject.domain.word.storage.answer;

public interface WrongAnswerMapping {
    Long getId();
    String getTestType();
    Long getTotalWords();
    Long getWrongWords();
    Long getTime();
}