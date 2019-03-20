package me.shedaniel.cloth.events.client;

import me.shedaniel.cloth.api.CancellableEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;

public class ScreenMouseScrolledEvent implements CancellableEvent {
    
    private MinecraftClient client;
    private Screen screen;
    private double mouseX, mouseY, amount;
    private boolean cancelled = false;
    
    public ScreenMouseScrolledEvent(MinecraftClient client, Screen screen, double mouseX, double mouseY, double amount) {
        this.client = client;
        this.screen = screen;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.amount = amount;
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
    
    public double getAmount() {
        return amount;
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
