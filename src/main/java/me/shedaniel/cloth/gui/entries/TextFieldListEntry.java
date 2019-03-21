package me.shedaniel.cloth.gui.entries;

import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import me.shedaniel.cloth.gui.ClothConfigScreen.ListEntry;
import me.shedaniel.cloth.hooks.TextFieldWidgetHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public abstract class TextFieldListEntry<T> extends ListEntry {
    
    protected TextFieldWidget textFieldWidget;
    protected T original;
    
    protected TextFieldListEntry(String fieldName, T value) {
        super(fieldName);
        this.original = value;
        this.textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 148, 18);
        textFieldWidget.setText(String.valueOf(value));
        textFieldWidget.setMaxLength(999999);
        textFieldWidget.setChangedListener(s -> {
            if (!original.equals(s))
                ((ClothConfigScreen.ListWidget) getParent()).getScreen().setEdited(true);
        });
    }
    
    @Override
    public void draw(int entryWidth, int height, int i3, int i4, boolean isSelected, float delta) {
        Window window = MinecraftClient.getInstance().window;
        Point mouse = ClientUtils.getMouseLocation();
        ((TextFieldWidgetHooks) this.textFieldWidget).setY(getY());
        if (MinecraftClient.getInstance().textRenderer.isRightToLeft()) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), window.getScaledWidth() - getX() - MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(getFieldName())), getY() + 5, 16777215);
            this.textFieldWidget.setX(getX());
        } else {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), getX(), getY() + 5, 16777215);
            this.textFieldWidget.setX(window.getScaledWidth() - getX() - ((TextFieldWidgetHooks) this.textFieldWidget).getWidth() - 1);
        }
        textFieldWidget.render(mouse.x, mouse.y, delta);
    }
    
    public String getYesNoText(boolean bool) {
        return bool ? "§aYes" : "§cNo";
    }
    
    @Override
    public List<? extends InputListener> getInputListeners() {
        return Collections.singletonList(textFieldWidget);
    }
    
    @Override
    public boolean isActive() {
        return textFieldWidget.isFocused();
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
    public void onFocusChanged(boolean boolean_1) {
        textFieldWidget.onFocusChanged(boolean_1);
    }
    
    @Override
    public boolean isPartOfFocusCycle() {
        return textFieldWidget.isPartOfFocusCycle();
    }
    
    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (textFieldWidget.mouseClicked(double_1, double_2, int_1))
            return true;
        return false;
    }
    
    @Override
    public boolean charTyped(char char_1, int int_1) {
        if (textFieldWidget.charTyped(char_1, int_1))
            return true;
        return false;
    }
    
    @Override
    public boolean keyPressed(int int_1, int int_2, int int_3) {
        if (textFieldWidget.keyPressed(int_1, int_2, int_3))
            return true;
        return false;
    }
    
}
