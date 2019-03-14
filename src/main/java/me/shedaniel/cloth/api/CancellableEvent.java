package me.shedaniel.cloth.api;

public interface CancellableEvent {
    
    void setCancelled(boolean cancelled);
    
    boolean isCancelled();
    
}
