package me.shedaniel.cloth.config;

import me.shedaniel.cloth.api.ConfigScreenBuilder;
import me.shedaniel.cloth.gui.entries.BooleanListEntry;
import me.shedaniel.cloth.gui.entries.EnumListEntry;
import me.shedaniel.cloth.gui.entries.IntegerListEntry;
import me.shedaniel.cloth.gui.entries.IntegerSliderEntry;
import me.shedaniel.cloth.gui.entries.StringListEntry;
import me.shedaniel.cloth.utils.ClientUtils;
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
        new ClientUtils();
        AtomicBoolean test = new AtomicBoolean(false);
        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
            try {
                Class<?> clazz = Class.forName("io.github.prospector.modmenu.api.ModMenuApi");
                Method method = clazz.getMethod("addConfigOverride", String.class, Runnable.class);
                method.invoke(null, "cloth-config", (Runnable) () -> {
                    ConfigScreenBuilder builder = ConfigScreenBuilder.create(MinecraftClient.getInstance().currentScreen, "Cloth Mod Config Demo", null);
                    builder.addCategory("Boolean Zone").addOption(new BooleanListEntry("Simple Boolean", false, null));
                    ConfigScreenBuilder.CategoryBuilder numberZone = builder.addCategory("Numbers Zone");
                    numberZone.addOption(new StringListEntry("String Field", "ab", "text.cloth-config.reset_value", () -> "ab", null));
                    numberZone.addOption(new IntegerListEntry("Integer Field", 2, "text.cloth-config.reset_value", () -> 2, null).setMaximum(99).setMinimum(2));
                    numberZone.addOption(new IntegerSliderEntry("Integer Slider", 2, 99, 99, "text.cloth-config.reset_value", () -> 2, null));
                    ConfigScreenBuilder.CategoryBuilder enumZone = builder.addCategory("Enum Zone");
                    enumZone.addOption(new EnumListEntry<DemoEnum>("Enum Field", DemoEnum.class, DemoEnum.CONSTANT_2, "text.cloth-config.reset_value", () -> DemoEnum.CONSTANT_1, null));
                    MinecraftClient.getInstance().openScreen(builder.build());
                });
            } catch (Exception e) {
                ClothInitializer.LOGGER.error("[Cloth-Config] Failed to add test config override for ModMenu!", e);
            }
        }
    }

    private static enum DemoEnum implements EnumListEntry.Translatable {
        CONSTANT_1("Constant 1"), CONSTANT_2("Constant 2"), CONSTANT_3("Constant 3");

        private final String key;

        private DemoEnum(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return this.key;
        }
    }
    
}
