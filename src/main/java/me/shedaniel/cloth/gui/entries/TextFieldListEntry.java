package me.shedaniel.cloth.gui.entries;

import com.google.common.collect.Lists;
import me.shedaniel.cloth.hooks.TextFieldWidgetHooks;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class TextFieldListEntry<T> extends ClothConfigScreen.ListEntry {
    
    protected TextFieldWidget textFieldWidget;
    protected ButtonWidget resetButton;
    protected Supplier<T> defaultValue;
    protected T original;
    protected List<Element> widgets;
    
    protected TextFieldListEntry(String fieldName, T original, String resetButtonKey, Supplier<T> defaultValue) {
        super(fieldName);
        this.defaultValue = defaultValue;
        this.original = original;
        this.textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 148, 18);
        textFieldWidget.setMaxLength(999999);
        textFieldWidget.setText(String.valueOf(original));
        textFieldWidget.setChangedListener(s -> {
            if (!original.equals(s))
                getScreen().setEdited(true);
        });
        this.resetButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(resetButtonKey)) + 6, 20, I18n.translate(resetButtonKey), widget -> {
            TextFieldListEntry.this.textFieldWidget.setText(String.valueOf(defaultValue.get()));
            getScreen().setEdited(true);
        });
        this.widgets = Lists.newArrayList(textFieldWidget, resetButton);
    }
    
    protected static void setTextFieldWidth(TextFieldWidget widget, int width) {
        ((TextFieldWidgetHooks) widget).clothconfig_setWidth(width);
    }
    
    @Override
    public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
        Window window = MinecraftClient.getInstance().window;
        this.resetButton.active = getDefaultValue().isPresent() && !isMatchDefault(textFieldWidget.getText());
        this.resetButton.y = y;
        ((TextFieldWidgetHooks) this.textFieldWidget).clothconfig_setY(y + 1);
        if (MinecraftClient.getInstance().textRenderer.isRightToLeft()) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), window.getScaledWidth() - x - MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(getFieldName())), y + 5, 16777215);
            this.resetButton.x = x;
            this.textFieldWidget.setX(x + resetButton.getWidth() + 2);
            setTextFieldWidth(textFieldWidget, 150 - resetButton.getWidth() - 4);
        } else {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), x, y + 5, 16777215);
            this.resetButton.x = window.getScaledWidth() - x - resetButton.getWidth();
            this.textFieldWidget.setX(window.getScaledWidth() - x - 150);
            setTextFieldWidth(textFieldWidget, 150 - resetButton.getWidth() - 4);
        }
        resetButton.render(mouseX, mouseY, delta);
        textFieldWidget.render(mouseX, mouseY, delta);
    }
    
    protected abstract boolean isMatchDefault(String text);
    
    @Override
    public Optional<Object> getDefaultValue() {
        return defaultValue == null ? Optional.empty() : Optional.ofNullable(defaultValue.get());
    }
    
    public String getYesNoText(boolean bool) {
        return bool ? "§aYes" : "§cNo";
    }
    
    @Override
    public List<? extends Element> children() {
        return widgets;
    }
    
}
