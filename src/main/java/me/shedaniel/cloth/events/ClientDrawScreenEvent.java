package me.shedaniel.cloth.events;

import me.shedaniel.cloth.api.Cancelable;
import me.shedaniel.cloth.api.CancelableEvent;
import me.shedaniel.cloth.api.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;

public class ClientDrawScreenEvent {
    
    private MinecraftClient client;
    private Screen screen;
    private int mouseX, mouseY;
    private float delta;
    
    protected ClientDrawScreenEvent(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta) {
        this.client = client;
        this.screen = screen;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.delta = delta;
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
    
    public int getMouseX() {
        return mouseX;
    }
    
    public int getMouseY() {
        return mouseY;
    }
    
    public float getDelta() {
        return delta;
    }
    
    public static class Pre extends ClientDrawScreenEvent implements CancelableEvent {
        private Cancelable cancelable;
        
        public Pre(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta, Cancelable cancelable) {
            super(client, screen, mouseX, mouseY, delta);
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
    
    public static class Post extends ClientDrawScreenEvent {
        public Post(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta) {
            super(client, screen, mouseX, mouseY, delta);
        }
    }
    
}
