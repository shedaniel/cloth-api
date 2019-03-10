package me.shedaniel.cloth.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.recipe.RecipeManager;

public class ClientSyncRecipesEvent {
    
    private MinecraftClient client;
    private RecipeManager manager;
    
    public ClientSyncRecipesEvent(MinecraftClient client, RecipeManager manager) {
        this.client = client;
        this.manager = manager;
    }
    
    public MinecraftClient getClient() {
        return client;
    }
    
    public RecipeManager getManager() {
        return manager;
    }
    
}
