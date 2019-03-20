package me.shedaniel.cloth.events.client;

import me.shedaniel.cloth.api.CancellableEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;

public class ScreenKeyTypedEvent implements CancellableEvent {
    
    private MinecraftClient client;
    private Screen screen;
    private char character;
    private int keyCode;
    private boolean cancelled = false;
    
    public ScreenKeyTypedEvent(MinecraftClient client, Screen screen, char character, int keyCode) {
        this.client = client;
        this.screen = screen;
        this.character = character;
        this.keyCode = keyCode;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
    public Screen getScreen() {
        return screen;
    }
    
    public char getCharacter() {
        return character;
    }
    
    public int getKeyCode() {
        return keyCode;
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
