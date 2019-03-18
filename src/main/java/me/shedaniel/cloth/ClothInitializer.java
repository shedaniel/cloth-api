package me.shedaniel.cloth;

import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.api.EventPriority;
import me.shedaniel.cloth.hooks.ClothHooks;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClothInitializer implements ClientModInitializer {
    
    public static final Logger LOGGER = LogManager.getFormatterLogger("Cloth");
    public static ClientUtils clientUtils;
    
    @Override
    public void onInitializeClient() {
        clientUtils = new me.shedaniel.cloth.utils.ClientUtils();
        if (true) // Test Codes
            return;
        ClothHooks.CLIENT_SYNC_RECIPES.registerListener(event -> {
            System.out.println("HAI");
        });
        ClothHooks.CLIENT_SYNC_RECIPES.registerListener(event -> {
            System.out.println("BAD I AM FIRST");
        }, EventPriority.HIGHEST);
    }
    
}
