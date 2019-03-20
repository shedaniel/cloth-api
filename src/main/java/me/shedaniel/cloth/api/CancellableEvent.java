package me.shedaniel.cloth.api;

public interface CancellableEvent {
    
    boolean isCancelled();
    
    void setCancelled(boolean cancelled);
    
}
