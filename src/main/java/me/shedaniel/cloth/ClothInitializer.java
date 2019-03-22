package me.shedaniel.cloth;

import me.shedaniel.cloth.api.ConfigScreenBuilder;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import me.shedaniel.cloth.gui.entries.BooleanListEntry;
import me.shedaniel.cloth.gui.entries.IntegerListEntry;
import me.shedaniel.cloth.gui.entries.IntegerSliderEntry;
import me.shedaniel.cloth.gui.entries.StringListEntry;
import me.shedaniel.cloth.hooks.ClothClientHooks;
import me.shedaniel.cloth.utils.ClientUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.MainMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClothInitializer implements ClientModInitializer {
    
    public static final Logger LOGGER = LogManager.getFormatterLogger("Cloth");
    
    @Override
    public void onInitializeClient() {
        new ClientUtils();
        AtomicBoolean test = new AtomicBoolean(false);
        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
            try {
                Class<?> clazz = Class.forName("io.github.prospector.modmenu.api.ModMenuApi");
                Method method = clazz.getMethod("addConfigOverride", String.class, Runnable.class);
                method.invoke(null, "cloth", (Runnable) () -> {
                    ClothConfigScreen.Builder builder = new ClothConfigScreen.Builder(MinecraftClient.getInstance().currentScreen, "Cloth Mod Config Demo", null);
                    builder.addCategories("Boolean Zone", "Number Zone");
                    builder.getCategory("Boolean Zone").addOption(new BooleanListEntry("Savable Boolean", test.get(), "text.cloth.reset_value", () -> false, bool -> test.set(bool)));
                    ConfigScreenBuilder.CategoryBuilder numberZone = builder.getCategory("Number Zone");
                    numberZone.addOption(new StringListEntry("String Field", "ab", "text.cloth.reset_value", () -> "ab", null));
                    numberZone.addOption(new IntegerListEntry("Integer Field", 2, "text.cloth.reset_value", () -> 2, null).setMaximum(99).setMinimum(2));
                    numberZone.addOption(new IntegerSliderEntry("Integer Slider", 2, 99, 99, "text.cloth.reset_value", () -> 2, null));
                    MinecraftClient.getInstance().openScreen(builder.build());
                });
            } catch (Exception e) {
                ClothInitializer.LOGGER.error("[Cloth] Failed to add test config override for ModMenu!", e);
            }
        }
        if (true) // Test Codes
            return;
        ClothClientHooks.SYNC_RECIPES.register((client, manager, packet) -> {
            System.out.println("HAI");
        });
    }
    
}
