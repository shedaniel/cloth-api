package me.shedaniel.cloth.events.client;

import me.shedaniel.cloth.api.CancellableEvent;
import me.shedaniel.cloth.hooks.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.List;

public class ScreenInitEvent {
    
    private MinecraftClient client;
    private Screen screen;
    
    protected ScreenInitEvent(MinecraftClient client, Screen screen) {
        this.client = client;
        this.screen = screen;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
    public Screen getScreen() {
        return screen;
    }
    
    public List<AbstractButtonWidget> getButtonWidgets() {
        return ((ScreenHooks) getScreen()).cloth_getButtonWidgets();
    }
    
    public AbstractButtonWidget addButton(AbstractButtonWidget buttonWidget) {
        return ((ScreenHooks) getScreen()).cloth_addButton(buttonWidget);
    }
    
    public List<InputListener> getInputListeners() {
        return ((ScreenHooks) getScreen()).cloth_getInputListeners();
    }
    
    public static class Pre extends ScreenInitEvent implements CancellableEvent {
        private boolean cancelled = false;
        
        public Pre(MinecraftClient client, Screen screen) {
            super(client, screen);
        }
        
        @Override
        public boolean isCancelled() {
            return cancelled;
        }
        
        @Override
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
    
    public static class Post extends ScreenInitEvent {
        public Post(MinecraftClient client, Screen screen) {
            super(client, screen);
        }
    }
    
}
