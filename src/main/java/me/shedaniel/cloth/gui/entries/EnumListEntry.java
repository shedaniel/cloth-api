package me.shedaniel.cloth.gui.entries;

import java.awt.Point;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;

public class EnumListEntry<T extends Enum<?> & EnumListEntry.Translatable> extends ClothConfigScreen.ListEntry {
    
    private ImmutableList<T> values;
    private AtomicInteger index;
    private ButtonWidget buttonWidget, resetButton;
    private Consumer<T> saveConsumer;
    private Supplier<T> defaultValue;
    private List<Element> widgets;
    
    public EnumListEntry(String fieldName, Class<T> clazz, T value, Consumer<T> saveConsumer) {
        this(fieldName, clazz, value, "text.cloth-config.reset_value", null, saveConsumer);
    }
    
    public EnumListEntry(String fieldName, Class<T> clazz, T value, String resetButtonKey, Supplier<T> defaultValue, Consumer<T> saveConsumer) {
        super(fieldName);
        T[] valuesArray = clazz.getEnumConstants();
        if (valuesArray != null)
            this.values = ImmutableList.copyOf(valuesArray);
        else
            this.values = ImmutableList.of(value);
        this.defaultValue = defaultValue;
        this.index = new AtomicInteger(this.values.indexOf(value));
        this.index.compareAndSet(-1, 0);
        this.buttonWidget = new ButtonWidget(0, 0, 150, 20, "", widget -> {
            EnumListEntry.this.index.incrementAndGet();
            EnumListEntry.this.index.compareAndSet(EnumListEntry.this.values.size(), 0);
            getScreen().setEdited(true);
        });
        this.resetButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(resetButtonKey)) + 6, 20, I18n.translate(resetButtonKey), widget -> {
            EnumListEntry.this.index.set(getDefaultIndex());
            getScreen().setEdited(true);
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
    public T getObject() {
        return this.values.get(this.index.get());
    }
    
    @Override
    public Optional<Object> getDefaultValue() {
        return defaultValue == null ? Optional.empty() : Optional.ofNullable(defaultValue.get());
    }
    
    @Override
    public void render(int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
        Window window = MinecraftClient.getInstance().window;
        Point mouse = ClientUtils.getMouseLocation();
        this.resetButton.active = getDefaultValue().isPresent() && getDefaultIndex() != this.index.get();
        this.resetButton.y = y;
        this.buttonWidget.y = y;
        this.buttonWidget.setMessage(I18n.translate(getObject().getKey()));
        if (MinecraftClient.getInstance().textRenderer.isRightToLeft()) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), window.getScaledWidth() - x - MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(getFieldName())), y + 5, 16777215);
            this.resetButton.x = x;
            this.buttonWidget.x = x + resetButton.getWidth() + 2;
            this.buttonWidget.setWidth(150 - resetButton.getWidth() - 2);
        } else {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), x, y + 5, 16777215);
            this.resetButton.x = window.getScaledWidth() - x - resetButton.getWidth();
            this.buttonWidget.x = window.getScaledWidth() - x - 150;
            this.buttonWidget.setWidth(150 - resetButton.getWidth() - 2);
        }
        resetButton.render(mouse.x, mouse.y, delta);
        buttonWidget.render(mouse.x, mouse.y, delta);
    }
    
    private int getDefaultIndex() {
        return Math.max(0, this.values.indexOf(this.defaultValue.get()));
    }
    
    @Override
    public List<? extends Element> children() {
        return widgets;
    }

    public static interface Translatable {
        String getKey();
    }
    
}