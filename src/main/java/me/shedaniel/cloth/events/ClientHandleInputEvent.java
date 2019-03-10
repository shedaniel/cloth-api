package me.shedaniel.cloth.events;

import net.minecraft.client.MinecraftClient;

public class ClientHandleInputEvent {
    
    private MinecraftClient client;
    
    public ClientHandleInputEvent(MinecraftClient client) {
        this.client = client;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
}
