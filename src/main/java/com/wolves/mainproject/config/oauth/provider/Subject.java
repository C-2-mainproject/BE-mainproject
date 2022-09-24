package com.wolves.mainproject.config.oauth.provider;

public interface Subject {
    void registerObserver(Observer o);
    void notifyObservers();
}
