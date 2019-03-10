package me.shedaniel.cloth;

import me.shedaniel.cloth.api.EventPriority;
import me.shedaniel.cloth.hooks.ClothHooks;
import me.shedaniel.cloth.hooks.ClothModMenuHooks;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.menu.ErrorScreen;

public class TestClass implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        if (true)
            return;
        ClothHooks.CLIENT_SYNC_RECIPES.registerEvent(event -> {
            System.out.println("HAI");
        });
        ClothHooks.CLIENT_SYNC_RECIPES.registerEvent(event -> {
            System.out.println("BAD I AM FIRST");
        }, EventPriority.HIGHEST);
        Runnable r = () -> MinecraftClient.getInstance().openScreen(new ErrorScreen("Random", "Good Test"));
        ClothModMenuHooks.CONFIG_BUTTON_EVENT.registerEvent(event -> {
            if (event.getModContainer() != null && event.getModContainer().getMetadata().getId().equalsIgnoreCase("cloth")) {
                event.setEnabled(true);
                event.setClickedRunnable(r);
                event.setCancelled(true);
            }
        }, EventPriority.LOWEST);
    }
    
}
