package me.shedaniel.cloth.events;

import me.shedaniel.cloth.api.Cancelable;
import me.shedaniel.cloth.api.CancelableEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

public class ClientScreenAddButtonEvent implements CancelableEvent {
    
    private MinecraftClient client;
    private Screen screen;
    private ButtonWidget buttonWidget;
    private Cancelable cancelable;
    
    public ClientScreenAddButtonEvent(MinecraftClient client, Screen screen, ButtonWidget buttonWidget, Cancelable cancelable) {
        this.client = client;
        this.screen = screen;
        this.buttonWidget = buttonWidget;
        this.cancelable = cancelable;
    }
    
    public ButtonWidget getButtonWidget() {
        return buttonWidget;
    }
    
    @Override
    public boolean isCancelled() {
        return cancelable.isCancelled();
    }
    
    @Override
    public void setCancelled(boolean cancelled) {
        cancelable.setCancelled(cancelled);
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
    public Screen getScreen() {
        return screen;
    }
    
}
