package me.shedaniel.cloth;

import me.shedaniel.cloth.hooks.ClothModMenuHooks;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClothInitializer implements ClientModInitializer {
    
    public static final Logger LOGGER = LogManager.getLogger();
    
    @Override
    public void onInitializeClient() {
        ClothModMenuHooks.loadHooks();
    }
    
}
