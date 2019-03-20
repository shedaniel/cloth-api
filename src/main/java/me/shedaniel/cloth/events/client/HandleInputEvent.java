package me.shedaniel.cloth.events.client;

import net.minecraft.client.MinecraftClient;

public class HandleInputEvent {
    
    private MinecraftClient client;
    
    public HandleInputEvent(MinecraftClient client) {
        this.client = client;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
}
