package me.shedaniel.cloth.config;

import me.shedaniel.cloth.config.api.ConfigScreenBuilder;
import me.shedaniel.cloth.config.gui.ClothConfigScreen;
import me.shedaniel.cloth.config.gui.entries.BooleanListEntry;
import me.shedaniel.cloth.config.gui.entries.IntegerListEntry;
import me.shedaniel.cloth.config.gui.entries.IntegerSliderEntry;
import me.shedaniel.cloth.config.gui.entries.StringListEntry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClothInitializer implements ClientModInitializer {
    
    public static final Logger LOGGER = LogManager.getFormatterLogger("Cloth-Config");
    
    @Override
    public void onInitializeClient() {
        AtomicBoolean test = new AtomicBoolean(false);
        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
            try {
                Class<?> clazz = Class.forName("io.github.prospector.modmenu.api.ModMenuApi");
                Method method = clazz.getMethod("addConfigOverride", String.class, Runnable.class);
                method.invoke(null, "cloth-config", (Runnable) () -> {
                    ConfigScreenBuilder builder = ClothConfigScreen.Builder.create(MinecraftClient.getInstance().currentScreen, "Cloth Mod Config Demo", null);
                    builder.addCategory("Boolean Zone").addOption(new BooleanListEntry("Simple Boolean", false, null));
                    ConfigScreenBuilder.CategoryBuilder numberZone = builder.addCategory("Numbers Zone");
                    numberZone.addOption(new StringListEntry("String Field", "ab", "text.cloth-config.reset_value", () -> "ab", null));
                    numberZone.addOption(new IntegerListEntry("Integer Field", 2, "text.cloth-config.reset_value", () -> 2, null).setMaximum(99).setMinimum(2));
                    numberZone.addOption(new IntegerSliderEntry("Integer Slider", 2, 99, 99, "text.cloth-config.reset_value", () -> 2, null));
                    MinecraftClient.getInstance().openScreen(builder.build());
                });
            } catch (Exception e) {
                ClothInitializer.LOGGER.error("[Cloth-Config] Failed to add test config override for ModMenu!", e);
            }
        }
    }
    
}
