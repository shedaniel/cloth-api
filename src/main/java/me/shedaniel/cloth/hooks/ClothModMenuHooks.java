package me.shedaniel.cloth.hooks;

import me.shedaniel.cloth.api.EventManager;
import me.shedaniel.cloth.events.modmenu.ConfigButtonEvent;
import net.fabricmc.loader.api.FabricLoader;

// TODO: Remove this class in the next snapshot
public class ClothModMenuHooks {
    
    // Keeping this here to prevent api issues, removing in next snapshot
    public static final EventManager<ConfigButtonEvent> CONFIG_BUTTON_EVENT = EventManager.create(ConfigButtonEvent.class);
    
}
