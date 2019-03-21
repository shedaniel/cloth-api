package me.shedaniel.cloth;

import me.shedaniel.cloth.api.ConfigScreenBuilder;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import me.shedaniel.cloth.gui.entries.BooleanListEntry;
import me.shedaniel.cloth.gui.entries.IntegerListEntry;
import me.shedaniel.cloth.hooks.ClothClientHooks;
import me.shedaniel.cloth.utils.ClientUtils;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.MainMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

public class ClothInitializer implements ClientModInitializer {
    
    public static final Logger LOGGER = LogManager.getFormatterLogger("Cloth");
    
    @Override
    public void onInitializeClient() {
        new ClientUtils();
        if (true) // Test Codes
            return;
        AtomicBoolean test = new AtomicBoolean(false);
        ClothClientHooks.SCREEN_INIT_POST.register((client, screen, screenHooks) -> {
            if (screen instanceof MainMenuScreen) {
                screenHooks.cloth_addButton(new ButtonWidget(10, 30, 80, 20, "test button gui") {
                    @Override
                    public void onPressed() {
                        ClothConfigScreen.Builder builder = new ClothConfigScreen.Builder(screen, "Test Mod Config", null);
                        builder.addCategories("Boolean Zone", "Number Zone");
                        builder.getCategory("Boolean Zone").addOption(new BooleanListEntry("Basic Boolean", test.get(), aBoolean -> {
                            test.set(aBoolean);
                        })).addOption("Boolean 2", true);
                        ConfigScreenBuilder.CategoryBuilder numberZone = builder.getCategory("Number Zone");
                        numberZone.addOption(new IntegerListEntry("Integer", 2, null).setMaximum(99).setMinimum(2));
                        numberZone.addOption("Long", 1l);
                        numberZone.addOption("Float", 1f);
                        numberZone.addOption("Double", 1d);
                        client.openScreen(builder.build());
                    }
                });
            }
        });
        ClothClientHooks.SYNC_RECIPES.register((client, manager, packet) -> {
            System.out.println("HAI");
        });
    }
    
}
