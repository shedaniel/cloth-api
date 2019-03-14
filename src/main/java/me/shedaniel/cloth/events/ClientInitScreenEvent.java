package me.shedaniel.cloth.events;

import me.shedaniel.cloth.api.CancellableEvent;
import me.shedaniel.cloth.hooks.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;

public class ClientInitScreenEvent {
    
    private MinecraftClient client;
    private Screen screen;
    
    protected ClientInitScreenEvent(MinecraftClient client, Screen screen) {
        this.client = client;
        this.screen = screen;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
    public Screen getScreen() {
        return screen;
    }
    
    public List<ButtonWidget> getButtonWidgets() {
        return ((ScreenHooks) screen).cloth_getButtonWidgets();
    }
    
    public List<InputListener> getInputListeners() {
        return ((ScreenHooks) screen).cloth_getInputListeners();
    }
    
    public static class Pre extends ClientInitScreenEvent implements CancellableEvent {
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
    
    public static class Post extends ClientInitScreenEvent {
        public Post(MinecraftClient client, Screen screen) {
            super(client, screen);
        }
    }
    
}
