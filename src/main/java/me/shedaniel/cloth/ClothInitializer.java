package me.shedaniel.cloth;

import me.shedaniel.cloth.api.EventPriority;
import me.shedaniel.cloth.hooks.ClothHooks;
import me.shedaniel.cloth.hooks.ClothModMenuHooks;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.menu.ErrorScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClothInitializer implements ClientModInitializer {
    
    public static final Logger LOGGER = LogManager.getFormatterLogger("Cloth");
    
    @Override
    public void onInitializeClient() {
        ClothModMenuHooks.loadHooks();
        
        Runnable configRunnable = () -> MinecraftClient.getInstance().openScreen(new ErrorScreen("Random", "Good Test"));
        ClothModMenuHooks.CONFIG_BUTTON_EVENT.registerListener(event -> {
            if (event.getModId().equalsIgnoreCase("cloth")) {
                event.setEnabled(true);
                event.setClickedRunnable(configRunnable);
                event.setCancelled(true);
            }
        }, EventPriority.LOWEST);
        
        // Test codes
        if (true)
            return;
        ClothHooks.CLIENT_SYNC_RECIPES.registerListener(event -> {
            System.out.println("HAI");
        });
        ClothHooks.CLIENT_SYNC_RECIPES.registerListener(event -> {
            System.out.println("BAD I AM FIRST");
        }, EventPriority.HIGHEST);
    }
    
}
