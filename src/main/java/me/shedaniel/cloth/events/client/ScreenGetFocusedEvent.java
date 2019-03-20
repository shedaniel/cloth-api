package me.shedaniel.cloth.events.client;

import me.shedaniel.cloth.api.ReturnableEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.Screen;

public class ScreenGetFocusedEvent implements ReturnableEvent<InputListener> {
    
    private MinecraftClient client;
    private Screen screen;
    private boolean cancelled = false;
    private InputListener value;
    
    public ScreenGetFocusedEvent(MinecraftClient client, Screen screen, InputListener value) {
        this.client = client;
        this.screen = screen;
        this.value = value;
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
    
    @Override
    public InputListener getReturningValue() {
        return value;
    }
    
    @Override
    public void setReturningValue(InputListener value) {
        this.value = value;
        setCancelled(true);
    }
    
}
