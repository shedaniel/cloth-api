package me.shedaniel.cloth.gui.entries;

import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import me.shedaniel.cloth.gui.ClothConfigScreen.ListEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BooleanListEntry extends ListEntry {
    
    private AtomicBoolean bool;
    private ButtonWidget buttonWidget;
    
    public BooleanListEntry(String fieldName, boolean bool) {
        super(fieldName);
        this.bool = new AtomicBoolean(bool);
        this.buttonWidget = new ButtonWidget(0, 0, 150, 20, "") {
            @Override
            public void onPressed() {
                BooleanListEntry.this.bool.set(!BooleanListEntry.this.bool.get());
                ((ClothConfigScreen.ListWidget) getParent()).getScreen().setEdited(true);
            }
        };
    }
    
    @Override
    public Object getObject() {
        return bool.get();
    }
    
    @Override
    public void draw(int entryWidth, int height, int i3, int i4, boolean isSelected, float delta) {
        Window window = MinecraftClient.getInstance().window;
        Point mouse = ClientUtils.getMouseLocation();
        this.buttonWidget.y = getY();
        this.buttonWidget.setText(getYesNoText(bool.get()));
        if (MinecraftClient.getInstance().textRenderer.isRightToLeft()) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), window.getScaledWidth() - getX() - MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(getFieldName())), getY() + 5, 16777215);
            this.buttonWidget.x = getX();
        } else {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), getX(), getY() + 5, 16777215);
            this.buttonWidget.x = window.getScaledWidth() - getX() - buttonWidget.getWidth();
        }
        buttonWidget.draw(mouse.x, mouse.y, delta);
    }
    
    public String getYesNoText(boolean bool) {
        return bool ? "§aYes" : "§cNo";
    }
    
    @Override
    public List<? extends InputListener> getInputListeners() {
        return Collections.singletonList(buttonWidget);
    }
    
    @Override
    public boolean isActive() {
        return buttonWidget.isHovered();
    }
    
    @Override
    public void setActive(boolean b) {
    
    }
    
    @Override
    public InputListener getFocused() {
        return null;
    }
    
    @Override
    public void setFocused(InputListener inputListener) {
    
    }
    
    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (buttonWidget.mouseClicked(double_1, double_2, int_1))
            return true;
        return false;
    }
    
}
