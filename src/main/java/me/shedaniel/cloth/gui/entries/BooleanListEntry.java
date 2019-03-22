package me.shedaniel.cloth.gui.entries;

import com.google.common.collect.Lists;
import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import me.shedaniel.cloth.gui.ClothConfigScreen.ListEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanListEntry extends ListEntry {
    
    private AtomicBoolean bool;
    private ButtonWidget buttonWidget, resetButton;
    private Consumer<Boolean> saveConsumer;
    private Supplier<Boolean> defaultValue;
    private List<InputListener> widgets;
    
    public BooleanListEntry(String fieldName, boolean bool, Consumer<Boolean> saveConsumer) {
        this(fieldName, bool, "text.cloth.reset_value", null, saveConsumer);
    }
    
    public BooleanListEntry(String fieldName, boolean bool, String resetButtonKey, Supplier<Boolean> defaultValue, Consumer<Boolean> saveConsumer) {
        super(fieldName);
        this.defaultValue = defaultValue;
        this.bool = new AtomicBoolean(bool);
        this.buttonWidget = new ButtonWidget(0, 0, 150, 20, "", widget -> {
            BooleanListEntry.this.bool.set(!BooleanListEntry.this.bool.get());
            ((ClothConfigScreen.ListWidget) getParent()).getScreen().setEdited(true);
        });
        this.resetButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(resetButtonKey)) + 6, 20, I18n.translate(resetButtonKey), widget -> {
            BooleanListEntry.this.bool.set(defaultValue.get());
            ((ClothConfigScreen.ListWidget) getParent()).getScreen().setEdited(true);
        });
        this.saveConsumer = saveConsumer;
        this.widgets = Lists.newArrayList(buttonWidget, resetButton);
    }
    
    @Override
    public void save() {
        if (saveConsumer != null)
            saveConsumer.accept(getObject());
    }
    
    @Override
    public Boolean getObject() {
        return bool.get();
    }
    
    @Override
    public Optional<Object> getDefaultValue() {
        return defaultValue == null ? Optional.empty() : Optional.ofNullable(defaultValue.get());
    }
    
    @Override
    public void draw(int entryWidth, int height, int i3, int i4, boolean isSelected, float delta) {
        Window window = MinecraftClient.getInstance().window;
        Point mouse = ClientUtils.getMouseLocation();
        this.resetButton.active = getDefaultValue().isPresent() && defaultValue.get().booleanValue() != bool.get();
        this.resetButton.y = getY();
        this.buttonWidget.y = getY();
        this.buttonWidget.setMessage(getYesNoText(bool.get()));
        if (MinecraftClient.getInstance().textRenderer.isRightToLeft()) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), window.getScaledWidth() - getX() - MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(getFieldName())), getY() + 5, 16777215);
            this.resetButton.x = getX();
            this.buttonWidget.x = getX() + resetButton.getWidth() + 2;
            this.buttonWidget.setWidth(150 - resetButton.getWidth() - 2);
        } else {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), getX(), getY() + 5, 16777215);
            this.resetButton.x = window.getScaledWidth() - getX() - resetButton.getWidth();
            this.buttonWidget.x = window.getScaledWidth() - getX() - 150;
            this.buttonWidget.setWidth(150 - resetButton.getWidth() - 2);
        }
        resetButton.render(mouse.x, mouse.y, delta);
        buttonWidget.render(mouse.x, mouse.y, delta);
    }
    
    public String getYesNoText(boolean bool) {
        return bool ? "§aYes" : "§cNo";
    }
    
    @Override
    public List<? extends InputListener> getInputListeners() {
        return widgets;
    }
    
    @Override
    public boolean isActive() {
        return buttonWidget.isHovered() || resetButton.isHovered();
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
        if (resetButton.mouseClicked(double_1, double_2, int_1))
            return true;
        return false;
    }
    
}
