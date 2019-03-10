package me.shedaniel.cloth.api;

public interface CancelableEvent {
    
    void setCancelled(boolean cancelled);
    
    boolean isCancelled();
    
}
