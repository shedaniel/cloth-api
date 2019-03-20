package me.shedaniel.cloth;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.util.Pair;
import me.shedaniel.cloth.api.EventPriority;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import me.shedaniel.cloth.gui.entries.StringListEntry;
import me.shedaniel.cloth.hooks.ClothClientHooks;
import me.shedaniel.cloth.utils.ClientUtils;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.MainMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClothInitializer implements ClientModInitializer {
    
    public static final Logger LOGGER = LogManager.getFormatterLogger("Cloth");
    public boolean test = false;
    
    @Override
    public void onInitializeClient() {
        new ClientUtils();
        ClothClientHooks.SCREEN_INIT_POST.registerListener(post -> {
            if (post.getScreen() instanceof MainMenuScreen) {
                post.addButton(new ButtonWidget(10, 30, 80, 20, "test button gui") {
                    @Override
                    public void onPressed() {
                        Map<String, List<Pair<String, Object>>> map = Maps.newLinkedHashMap();
                        map.put("General", Lists.newArrayList(new Pair<>("text.dawdawdaw", true), new Pair<>("dawdwad", "dwajdhawi")));
                        map.put("Test Empty 1", Collections.emptyList());
                        map.put("Test Empty 2", Collections.emptyList());
                        map.put("Test Empty 3", Collections.emptyList());
                        map.put("Test Empty 4", Collections.emptyList());
                        map.put("Test Empty 5", Collections.emptyList());
                        map.put("Test Empty 6", Collections.emptyList());
                        map.put("Not Sure", Lists.newArrayList(new Pair<>("text.dawd", new StringListEntry("dawd", "dawd"))));
                        post.getClient().openScreen(new ClothConfigScreen(post.getScreen(), "Test Mod Config", map) {
                            @Override
                            public void onSave(Map<String, List<Pair<String, Object>>> o) {
                                o.get("General").stream().filter(pair -> pair.getKey().equals("text.test")).findFirst().ifPresent(pair -> test = (Boolean) pair.getValue());
                            }
                        });
                    }
                });
            }
        });
        if (true) // Test Codes
            return;
        ClothClientHooks.SYNC_RECIPES.registerListener(event -> {
            System.out.println("HAI");
        });
        ClothClientHooks.SYNC_RECIPES.registerListener(event -> {
            System.out.println("BAD I AM FIRST");
        }, EventPriority.HIGHEST);
    }
    
}
