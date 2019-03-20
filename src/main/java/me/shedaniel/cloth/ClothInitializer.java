package me.shedaniel.cloth;

import me.shedaniel.cloth.api.EventPriority;
import me.shedaniel.cloth.gui.ClothConfigScreen;
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
        ClothClientHooks.SCREEN_INIT_POST.registerListener(post -> {
            if (post.getScreen() instanceof MainMenuScreen) {
                post.addButton(new ButtonWidget(10, 30, 80, 20, "test button gui") {
                    @Override
                    public void onPressed() {
                        ClothConfigScreen.Builder builder = new ClothConfigScreen.Builder(post.getScreen(), "Test Mod Config", stringListMap -> {
                            stringListMap.get("General").stream().filter(pair -> pair.getKey().equals("text.dawdawdaw")).findFirst().ifPresent(pair -> test.set((Boolean) pair.getValue()));
                        });
                        builder.addCategories("General", "Test Empty 1", "Test Empty 2", "Test Empty 3", "Test Empty 4", "Test Empty 5", "Test Empty 6", "Not Sure");
                        builder.addOption("General", "text.dawdawdaw", test.get());
                        builder.addOption("General", "dawdwad", "dwajdhawi");
                        builder.addOption("Not Sure", "text.dawd", "dawd");
                        post.getClient().openScreen(builder.build());
                    }
                });
            }
        });
        ClothClientHooks.SYNC_RECIPES.registerListener(event -> {
            System.out.println("HAI");
        });
        ClothClientHooks.SYNC_RECIPES.registerListener(event -> {
            System.out.println("BAD I AM FIRST");
        }, EventPriority.HIGHEST);
    }
    
}
