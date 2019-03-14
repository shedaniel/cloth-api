package me.shedaniel.cloth.events;

import me.shedaniel.cloth.api.CancellableEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

public class ClientScreenAddButtonEvent implements CancellableEvent {
    
    private MinecraftClient client;
    private Screen screen;
    private ButtonWidget buttonWidget;
    private boolean cancelled = false;
    
    public ClientScreenAddButtonEvent(MinecraftClient client, Screen screen, ButtonWidget buttonWidget) {
        this.client = client;
        this.screen = screen;
        this.buttonWidget = buttonWidget;
    }
    
    public ButtonWidget getButtonWidget() {
        return buttonWidget;
    }
    
    @Override
    public boolean isCancelled() {
        return cancelled;
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
    public Screen getScreen() {
        return screen;
    }
    
}
