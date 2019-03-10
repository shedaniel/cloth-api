package me.shedaniel.cloth.events;

import me.shedaniel.cloth.api.Cancelable;
import me.shedaniel.cloth.api.CancelableEvent;
import me.shedaniel.cloth.api.ScreenHooks;
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
        return ((ScreenHooks) screen).getButtonWidgets();
    }
    
    public List<InputListener> getInputListeners() {
        return ((ScreenHooks) screen).getInputs();
    }
    
    public static class Pre extends ClientInitScreenEvent implements CancelableEvent {
        private Cancelable cancelable;
        
        public Pre(MinecraftClient client, Screen screen, Cancelable cancelable) {
            super(client, screen);
            this.cancelable = cancelable;
        }
        
        @Override
        public boolean isCancelled() {
            return cancelable.isCancelled();
        }
        
        @Override
        public void setCancelled(boolean cancelled) {
            cancelable.setCancelled(cancelled);
        }
    }
    
    public static class Post extends ClientInitScreenEvent {
        public Post(MinecraftClient client, Screen screen) {
            super(client, screen);
        }
    }
    
}
