package me.shedaniel.cloth.callbacks.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.recipe.RecipeManager;

public interface SyncRecipesCallback {
    void syncRecipes(MinecraftClient client, RecipeManager manager, SynchronizeRecipesS2CPacket packet);
}
