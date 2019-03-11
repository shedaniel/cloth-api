package me.shedaniel.cloth.api;

import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.util.List;
import java.util.function.Consumer;

public abstract class EventManager<T> {
    
    private List<Pair<Consumer<T>, Double>> listeners;
    
    private EventManager() {
        listeners = Lists.newArrayList();
    }
    
    public static <T> EventManager<T> create(Class<T> eventClass) {
        return new EventManager<T>() {};
    }
    
    public List<Pair<Consumer<T>, Double>> getListeners() {
        return listeners;
    }
    
    public List<Pair<Consumer<T>, Double>> getSortedListenerPairs() {
        List<Pair<Consumer<T>, Double>> list = Lists.newArrayList(listeners);
        list.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        return list;
    }
    
    public List<Consumer<T>> getSortedListeners() {
        List<Pair<Consumer<T>, Double>> pairs = getSortedListenerPairs();
        List<Consumer<T>> list = Lists.newArrayList();
        pairs.forEach(pair -> list.add(pair.getKey()));
        return list;
    }
    
    public void invoke(T event) {
        getSortedListeners().forEach(consumer -> consumer.accept(event));
    }
    
    public void registerListener(Consumer<T> o, Double p) {
        listeners.add(new Pair<>(o, p));
    }
    
    public void registerListener(Consumer<T> o, EventPriority p) {
        listeners.add(new Pair<>(o, p.getPriorityDouble()));
    }
    
    public void registerListener(Consumer<T> o) {
        registerListener(o, EventPriority.NORMAL.getPriorityDouble());
    }
    
}
