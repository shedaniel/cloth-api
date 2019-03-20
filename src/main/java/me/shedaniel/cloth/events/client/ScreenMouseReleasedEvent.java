package me.shedaniel.cloth.events.client;

import me.shedaniel.cloth.api.CancellableEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;

public class ScreenMouseReleasedEvent implements CancellableEvent {
    
    private MinecraftClient client;
    private Screen screen;
    private double mouseX, mouseY;
    private int button;
    private boolean cancelled = false;
    
    public ScreenMouseReleasedEvent(MinecraftClient client, Screen screen, double mouseX, double mouseY, int button) {
        this.client = client;
        this.screen = screen;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.button = button;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
    public Screen getScreen() {
        return screen;
    }
    
    public double getMouseX() {
        return mouseX;
    }
    
    public double getMouseY() {
        return mouseY;
    }
    
    public int getButton() {
        return button;
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
