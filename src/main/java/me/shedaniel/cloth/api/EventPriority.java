package me.shedaniel.cloth.api;

import java.util.Comparator;

public enum EventPriority implements Comparator<EventPriority> {
    HIGHEST(100), HIGH(50), NORMAL(0), LOW(-50), LOWEST(-100);
    
    private double priority;
    
    EventPriority(double priority) {
        this.priority = priority;
    }
    
    public double getPriority() {
        return priority;
    }
    
    @Override
    public int compare(EventPriority o1, EventPriority o2) {
        if (o1.getPriority() == o2.getPriority())
            return 0;
        return o1.getPriority() > o2.getPriority() ? 1 : -1;
    }
    
}
