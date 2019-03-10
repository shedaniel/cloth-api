package me.shedaniel.cloth.hooks;

import me.shedaniel.cloth.api.EventManager;
import me.shedaniel.cloth.events.modmenu.ConfigButtonEvent;
import net.fabricmc.loader.api.FabricLoader;

public class ClothModMenuHooks {
    
    public static final EventManager<ConfigButtonEvent> CONFIG_BUTTON_EVENT = EventManager.create(ConfigButtonEvent.class);
    public static boolean isModMenuLoaded = false;
    
    public static void loadHooks() {
        isModMenuLoaded = FabricLoader.getInstance().isModLoaded("modmenu");
        if (isModMenuLoaded)
            try {
                Class.forName("me.shedaniel.cloth.hooks.ModMenuHooks").getDeclaredMethod("registerEvents").invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
}
