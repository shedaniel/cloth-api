package me.shedaniel.cloth.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import javafx.util.Pair;
import me.shedaniel.cloth.gui.entries.BooleanListEntry;
import me.shedaniel.cloth.gui.entries.StringListEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.MultiInputListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.menu.YesNoScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ClothConfigScreen extends Screen {
    
    private static final Identifier CONFIG_TEX = new Identifier("cloth", "textures/gui/cloth_config.png");
    public int nextTabIndex;
    public int selectedTabIndex;
    private Screen parent;
    private ListWidget listWidget;
    private LinkedHashMap<String, List<AbstractListEntry>> tabbedEntries;
    private List<Pair<String, Integer>> tabs;
    private boolean edited;
    private boolean confirmSave;
    private ButtonWidget buttonQuit;
    private ButtonWidget buttonSave;
    private ButtonWidget buttonLeftTab;
    private ButtonWidget buttonRightTab;
    private Rectangle tabsBounds, tabsLeftBounds, tabsRightBounds;
    private String title;
    private double tabsScrollProgress = 0d;
    private double tabsMaximumScrolled = -1d;
    private List<ClothConfigTabButton> tabButtons;
    
    public ClothConfigScreen(Screen parent, String title, Map<String, List<Pair<String, Object>>> o) {
        this(parent, title, o, true);
    }
    
    public ClothConfigScreen(Screen parent, String title, Map<String, List<Pair<String, Object>>> o, boolean confirmSave) {
        this.parent = parent;
        this.title = title;
        this.tabbedEntries = Maps.newLinkedHashMap();
        o.forEach((tab, pairs) -> {
            List<AbstractListEntry> list = Lists.newArrayList();
            for(Pair<String, Object> pair : pairs) {
                if (pair.getValue() instanceof ListEntry) {
                    list.add((ListEntry) pair.getValue());
                } else if (pair.getValue() instanceof AbstractListEntry) {
                    throw new IllegalArgumentException("Unsupported Type (" + pair.getKey() + "): AbstractListEntry");
                } else if (boolean.class.isAssignableFrom(pair.getValue().getClass()) || Boolean.class.isAssignableFrom(pair.getValue().getClass())) {
                    list.add(new BooleanListEntry(pair.getKey(), (boolean) pair.getValue()));
                } else if (String.class.isAssignableFrom(pair.getValue().getClass())) {
                    list.add(new StringListEntry(pair.getKey(), (String) pair.getValue()));
                } else {
                    throw new IllegalArgumentException("Unsupported Type (" + pair.getKey() + "): " + pair.getValue().getClass().getSimpleName());
                }
            }
            tabbedEntries.put(tab, list);
        });
        this.nextTabIndex = 0;
        this.selectedTabIndex = 0;
        this.confirmSave = confirmSave;
        this.edited = false;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        this.tabs = tabbedEntries.keySet().stream().map(s -> new Pair<>(s, textRenderer.getStringWidth(s) + 8)).collect(Collectors.toList());
        this.tabsScrollProgress = 0d;
        this.tabButtons = Lists.newArrayList();
    }
    
    public boolean isEdited() {
        return edited;
    }
    
    public void setEdited(boolean edited) {
        this.edited = edited;
        buttonQuit.setText(edited ? I18n.translate("text.cloth.cancel_discard") : I18n.translate("gui.cancel"));
        buttonSave.enabled = edited;
    }
    
    @Override
    protected void onInitialized() {
        super.onInitialized();
        this.listeners.clear();
        this.tabButtons.clear();
        if (listWidget != null)
            tabbedEntries.put(tabs.get(selectedTabIndex).getKey(), listWidget.getInputListeners());
        selectedTabIndex = nextTabIndex;
        listeners.add(listWidget = new ListWidget(this, client, screenWidth, screenHeight, 70, screenHeight - 32, 24));
        if (tabbedEntries.size() > selectedTabIndex) {
            Lists.newArrayList(tabbedEntries.values()).get(selectedTabIndex).forEach(entry -> listWidget.getInputListeners().add(entry));
        }
        clampTabsScrolled();
        addButton(buttonQuit = new ButtonWidget(screenWidth / 2 - 154, screenHeight - 26, 150, 20, edited ? I18n.translate("text.cloth.cancel_discard") : I18n.translate("gui.cancel")) {
            @Override
            public void onPressed() {
                if (confirmSave && edited)
                    client.openScreen(new YesNoScreen(ClothConfigScreen.this, I18n.translate("text.cloth.quit_config"), I18n.translate("text.cloth.quit_config_sure"), I18n.translate("text.cloth.quit_discard"), I18n.translate("gui.cancel"), 812748710));
                else
                    client.openScreen(parent);
            }
        });
        addButton(buttonSave = new ButtonWidget(screenWidth / 2 + 4, screenHeight - 26, 150, 20, I18n.translate("text.cloth.save_and_done")) {
            @Override
            public void onPressed() {
                Map<String, List<Pair<String, Object>>> map = Maps.newLinkedHashMap();
                tabbedEntries.forEach((s, abstractListEntries) -> {
                    List list = abstractListEntries.stream().map(entry -> new Pair(entry.getFieldName(), entry.getObject())).collect(Collectors.toList());
                    map.put(s, list);
                });
                onSave(map);
                ClothConfigScreen.this.client.openScreen(parent);
            }
        });
        buttonSave.enabled = edited;
        tabsBounds = new Rectangle(0, 41, screenWidth, 24);
        tabsLeftBounds = new Rectangle(0, 41, 18, 24);
        tabsRightBounds = new Rectangle(screenWidth - 18, 41, 18, 24);
        listeners.add(buttonLeftTab = new ButtonWidget(4, 44, 12, 18, "") {
            @Override
            public void onPressed() {
                tabsScrollProgress = Integer.MIN_VALUE;
                clampTabsScrolled();
            }
            
            @Override
            public void drawButton(int int_1, int int_2, float float_1) {
                TextRenderer textRenderer_1 = client.textRenderer;
                client.getTextureManager().bindTexture(CONFIG_TEX);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.opacity);
                int int_3 = this.getTextureId(this.isHovered());
                GlStateManager.enableBlend();
                GlStateManager.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
                GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
                this.drawTexturedRect(x, y, 12, 18 * int_3, width, height);
            }
        });
        int j = 0;
        int xx = 20 - (int) tabsScrollProgress;
        for(Pair<String, Integer> tab : tabs) {
            tabButtons.add(new ClothConfigTabButton(this, j, -100, 43, tab.getValue(), 20, tab.getKey()));
            j++;
        }
        tabButtons.forEach(listeners::add);
        listeners.add(buttonRightTab = new ButtonWidget(screenWidth - 16, 44, 12, 18, "") {
            @Override
            public void onPressed() {
                tabsScrollProgress = Integer.MAX_VALUE;
                clampTabsScrolled();
            }
            
            @Override
            public void drawButton(int int_1, int int_2, float float_1) {
                TextRenderer textRenderer_1 = client.textRenderer;
                client.getTextureManager().bindTexture(CONFIG_TEX);
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.opacity);
                int int_3 = this.getTextureId(this.isHovered());
                GlStateManager.enableBlend();
                GlStateManager.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
                GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
                this.drawTexturedRect(x, y, 0, 18 * int_3, width, height);
            }
        });
    }
    
    @Override
    public boolean mouseScrolled(double double_1, double double_2, double double_3) {
        if (tabsBounds.contains(double_1, double_2) && !tabsLeftBounds.contains(double_1, double_2) && !tabsRightBounds.contains(double_1, double_2)) {
            if (double_3 != 0d) {
                if (double_3 < 0)
                    tabsScrollProgress += 16;
                if (double_3 > 0)
                    tabsScrollProgress -= 16;
                clampTabsScrolled();
                return true;
            }
        }
        return super.mouseScrolled(double_1, double_2, double_3);
    }
    
    public double getTabsMaximumScrolled() {
        if (tabsMaximumScrolled == -1d) {
            AtomicDouble d = new AtomicDouble();
            tabs.forEach(pair -> d.addAndGet(pair.getValue() + 2));
            tabsMaximumScrolled = d.get();
        }
        return tabsMaximumScrolled;
    }
    
    public void resetTabsMaximumScrolled() {
        tabsMaximumScrolled = -1d;
    }
    
    public void clampTabsScrolled() {
        tabsScrollProgress = MathHelper.clamp(tabsScrollProgress, 0, getTabsMaximumScrolled() - screenWidth + 40);
    }
    
    @Override
    public void draw(int int_1, int int_2, float float_1) {
        clampTabsScrolled();
        int xx = 20 - (int) tabsScrollProgress;
        for(ClothConfigTabButton tabButton : tabButtons) {
            tabButton.x = xx;
            xx += tabButton.getWidth() + 2;
        }
        buttonLeftTab.enabled = tabsScrollProgress > 0d;
        buttonRightTab.enabled = tabsScrollProgress < getTabsMaximumScrolled() - screenWidth - 16;
        drawTextureBackground(0);
        listWidget.draw(int_1, int_2, float_1);
        overlayBackground(tabsBounds, 32, 32, 32, 255, 255);
        
        drawStringCentered(client.textRenderer, title, screenWidth / 2, 18, -1);
        tabButtons.forEach(widget -> widget.draw(int_1, int_2, float_1));
        overlayBackground(tabsLeftBounds, 64, 64, 64, 255, 255);
        overlayBackground(tabsRightBounds, 64, 64, 64, 255, 255);
        drawShades();
        buttonLeftTab.draw(int_1, int_2, float_1);
        buttonRightTab.draw(int_1, int_2, float_1);
        super.draw(int_1, int_2, float_1);
    }
    
    private void drawShades() {
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ZERO, DestFactor.ONE);
        GlStateManager.disableAlphaTest();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_UV_COLOR);
        buffer.vertex(tabsBounds.getMinX() + 20, tabsBounds.getMinY() + 4, 0.0D).texture(0.0D, 1.0D).color(0, 0, 0, 0).next();
        buffer.vertex(tabsBounds.getMaxX() - 20, tabsBounds.getMinY() + 4, 0.0D).texture(1.0D, 1.0D).color(0, 0, 0, 0).next();
        buffer.vertex(tabsBounds.getMaxX() - 20, tabsBounds.getMinY(), 0.0D).texture(1.0D, 0.0D).color(0, 0, 0, 255).next();
        buffer.vertex(tabsBounds.getMinX() + 20, tabsBounds.getMinY(), 0.0D).texture(0.0D, 0.0D).color(0, 0, 0, 255).next();
        tessellator.draw();
        buffer.begin(7, VertexFormats.POSITION_UV_COLOR);
        buffer.vertex(tabsBounds.getMinX() + 20, tabsBounds.getMaxY(), 0.0D).texture(0.0D, 1.0D).color(0, 0, 0, 255).next();
        buffer.vertex(tabsBounds.getMaxX() - 20, tabsBounds.getMaxY(), 0.0D).texture(1.0D, 1.0D).color(0, 0, 0, 255).next();
        buffer.vertex(tabsBounds.getMaxX() - 20, tabsBounds.getMaxY() - 4, 0.0D).texture(1.0D, 0.0D).color(0, 0, 0, 0).next();
        buffer.vertex(tabsBounds.getMinX() + 20, tabsBounds.getMaxY() - 4, 0.0D).texture(0.0D, 0.0D).color(0, 0, 0, 0).next();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlphaTest();
        GlStateManager.disableBlend();
    }
    
    protected void overlayBackground(Rectangle rect, int red, int green, int blue, int startAlpha, int endAlpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        client.getTextureManager().bindTexture(DrawableHelper.OPTIONS_BG);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        buffer.begin(7, VertexFormats.POSITION_UV_COLOR);
        buffer.vertex(rect.getMinX(), rect.getMaxY(), 0.0D).texture(rect.getMinX() / 32.0D, rect.getMaxY() / 32.0D).color(red, green, blue, endAlpha).next();
        buffer.vertex(rect.getMaxX(), rect.getMaxY(), 0.0D).texture(rect.getMaxX() / 32.0D, rect.getMaxY() / 32.0D).color(red, green, blue, endAlpha).next();
        buffer.vertex(rect.getMaxX(), rect.getMinY(), 0.0D).texture(rect.getMaxX() / 32.0D, rect.getMinY() / 32.0D).color(red, green, blue, startAlpha).next();
        buffer.vertex(rect.getMinX(), rect.getMinY(), 0.0D).texture(rect.getMinX() / 32.0D, rect.getMinY() / 32.0D).color(red, green, blue, startAlpha).next();
        tessellator.draw();
    }
    
    @Override
    public boolean keyPressed(int int_1, int int_2, int int_3) {
        if (int_1 == 256 && this.doesEscapeKeyClose()) {
            if (confirmSave && edited)
                client.openScreen(new YesNoScreen(this, I18n.translate("text.cloth.quit_config"), I18n.translate("text.cloth.quit_config_sure"), I18n.translate("text.cloth.quit_discard"), I18n.translate("gui.cancel"), 812748710));
            else
                client.openScreen(parent);
            return true;
        }
        return super.keyPressed(int_1, int_2, int_3);
    }
    
    @Override
    public void confirmResult(boolean boolean_1, int int_1) {
        if (int_1 == 812748710) {
            if (!boolean_1)
                this.client.openScreen(this);
            else
                this.client.openScreen(parent);
            return;
        }
        super.confirmResult(boolean_1, int_1);
    }
    
    public abstract void onSave(Map<String, List<Pair<String, Object>>> o);
    
    public static class ListWidget extends EntryListWidget {
        private ClothConfigScreen screen;
        
        public ListWidget(ClothConfigScreen screen, MinecraftClient client, int int_1, int int_2, int int_3, int int_4, int int_5) {
            super(client, int_1, int_2, int_3, int_4, int_5);
            this.screen = screen;
        }
        
        @Override
        public int getEntryWidth() {
            return width - 80;
        }
        
        public ClothConfigScreen getScreen() {
            return screen;
        }
        
        @Override
        protected int getScrollbarPosition() {
            return width - 36;
        }
        
        protected final void clearStuff() {
            this.clearEntries();
        }
    }
    
    public static abstract class AbstractListEntry extends EntryListWidget.Entry<AbstractListEntry> implements MultiInputListener {
        public abstract String getFieldName();
        
        public abstract Object getObject();
    }
    
    public static abstract class ListEntry extends AbstractListEntry {
        private String fieldName;
        
        public ListEntry(String fieldName) {
            this.fieldName = fieldName;
        }
        
        @Override
        public String getFieldName() {
            return fieldName;
        }
    }
    
}
