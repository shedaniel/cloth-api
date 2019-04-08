package me.shedaniel.cloth;

import me.shedaniel.cloth.hooks.ClothClientHooks;
import me.shedaniel.cloth.utils.ClientUtils;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClothInitializer implements ClientModInitializer {
    
    public static final Logger LOGGER = LogManager.getFormatterLogger("Cloth");
    
    @Override
    public void onInitializeClient() {
        new ClientUtils();
        if (true) // Test Codes
            return;
        ClothClientHooks.SYNC_RECIPES.register((client, manager, packet) -> {
            System.out.println("HAI");
        });
    }
    
}
