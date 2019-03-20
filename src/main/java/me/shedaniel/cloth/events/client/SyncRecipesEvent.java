package me.shedaniel.cloth.events.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.packet.SynchronizeRecipesS2CPacket;
import net.minecraft.recipe.RecipeManager;

public class SyncRecipesEvent {
    
    private MinecraftClient client;
    private RecipeManager manager;
    private SynchronizeRecipesS2CPacket packet;
    
    public SyncRecipesEvent(MinecraftClient client, RecipeManager manager, SynchronizeRecipesS2CPacket packet) {
        this.client = client;
        this.manager = manager;
        this.packet = packet;
    }
    
    public SynchronizeRecipesS2CPacket getPacket() {
        return packet;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
    public RecipeManager getManager() {
        return manager;
    }
    
}
