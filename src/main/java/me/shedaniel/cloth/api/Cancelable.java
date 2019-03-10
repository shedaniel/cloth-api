package me.shedaniel.cloth.api;

public class Cancelable {
    
    private boolean cancelled = false;
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
}
