package me.shedaniel.cloth.api;

public interface ReturnableEvent<T> extends CancellableEvent {
    
    T getReturningValue();
    
    void setReturningValue(T value);
    
}
