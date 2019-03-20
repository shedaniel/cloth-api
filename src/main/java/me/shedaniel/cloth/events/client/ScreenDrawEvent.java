package me.shedaniel.cloth.events.client;

import me.shedaniel.cloth.api.CancellableEvent;
import me.shedaniel.cloth.hooks.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.List;

public class ScreenDrawEvent {
    
    private MinecraftClient client;
    private Screen screen;
    private int mouseX, mouseY;
    private float delta;
    
    protected ScreenDrawEvent(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta) {
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
    
    public List<AbstractButtonWidget> getButtonWidgets() {
        return ((ScreenHooks) screen).cloth_getButtonWidgets();
    }
    
    public List<InputListener> getInputListeners() {
        return ((ScreenHooks) screen).cloth_getInputListeners();
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
    
    public static class Pre extends ScreenDrawEvent implements CancellableEvent {
        private boolean cancelled = false;
        
        public Pre(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta) {
            super(client, screen, mouseX, mouseY, delta);
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
    
    public static class Post extends ScreenDrawEvent {
        public Post(MinecraftClient client, Screen screen, int mouseX, int mouseY, float delta) {
            super(client, screen, mouseX, mouseY, delta);
        }
    }
    
}
