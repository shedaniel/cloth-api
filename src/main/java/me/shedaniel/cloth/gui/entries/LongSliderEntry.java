package me.shedaniel.cloth.gui.entries;

import com.google.common.collect.Lists;
import me.shedaniel.cloth.api.ClientUtils;
import me.shedaniel.cloth.gui.ClothConfigScreen;
import me.shedaniel.cloth.gui.ClothConfigScreen.ListEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LongSliderEntry extends ListEntry {
    
    protected Slider sliderWidget;
    protected ButtonWidget resetButton;
    protected AtomicLong value;
    private int minimum, maximum;
    private Consumer<Long> saveConsumer;
    private Supplier<Long> defaultValue;
    private Function<Long, String> textGetter = value -> String.format("Value: %d", value);
    private List<InputListener> widgets;
    
    public LongSliderEntry(String fieldName, int minimum, int maximum, int value, Consumer<Long> saveConsumer) {
        this(fieldName, minimum, maximum, value, saveConsumer, "text.cloth.reset_value", null);
    }
    
    public LongSliderEntry(String fieldName, int minimum, int maximum, int value, Consumer<Long> saveConsumer, String resetButtonKey, Supplier<Long> defaultValue) {
        super(fieldName);
        this.defaultValue = defaultValue;
        this.value = new AtomicLong(value);
        this.saveConsumer = saveConsumer;
        this.maximum = maximum;
        this.minimum = minimum;
        this.sliderWidget = new Slider(0, 0, 152, 20, ((double) LongSliderEntry.this.value.get()) / Math.abs(maximum - minimum));
        this.resetButton = new ButtonWidget(0, 0, MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(resetButtonKey)) + 6, 20, I18n.translate(resetButtonKey), widget -> {
            sliderWidget.setProgress((MathHelper.clamp(this.defaultValue.get(), minimum, maximum) - minimum) / (double) Math.abs(maximum - minimum));
            sliderWidget.onProgressChanged();
            sliderWidget.updateText();
        });
        this.sliderWidget.setMessage(textGetter.apply(LongSliderEntry.this.value.get()));
        this.widgets = Lists.newArrayList(sliderWidget, resetButton);
    }
    
    public Function<Long, String> getTextGetter() {
        return textGetter;
    }
    
    public LongSliderEntry setTextGetter(Function<Long, String> textGetter) {
        this.textGetter = textGetter;
        return this;
    }
    
    @Override
    public Long getObject() {
        return value.get();
    }
    
    @Override
    public Optional<Object> getDefaultValue() {
        return defaultValue == null ? Optional.empty() : Optional.ofNullable(defaultValue.get());
    }
    
    @Override
    public List<? extends InputListener> getInputListeners() {
        return widgets;
    }
    
    public LongSliderEntry setMaximum(int maximum) {
        this.maximum = maximum;
        return this;
    }
    
    public LongSliderEntry setMinimum(int minimum) {
        this.minimum = minimum;
        return this;
    }
    
    @Override
    public boolean isActive() {
        return false;
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
        sliderWidget.onFocusChanged(boolean_1);
    }
    
    @Override
    public boolean isPartOfFocusCycle() {
        return sliderWidget.isPartOfFocusCycle();
    }
    
    @Override
    public void draw(int i, int i1, int i2, int i3, boolean b, float v) {
        Window window = MinecraftClient.getInstance().window;
        Point mouse = ClientUtils.getMouseLocation();
        this.resetButton.active = getDefaultValue().isPresent() && defaultValue.get().longValue() != value.get();
        this.resetButton.y = getY();
        this.sliderWidget.y = getY();
        if (MinecraftClient.getInstance().textRenderer.isRightToLeft()) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), window.getScaledWidth() - getX() - MinecraftClient.getInstance().textRenderer.getStringWidth(I18n.translate(getFieldName())), getY() + 5, 16777215);
            this.resetButton.x = getX();
            this.sliderWidget.x = getX() + resetButton.getWidth() + 1;
            this.sliderWidget.setWidth(150 - resetButton.getWidth() - 2);
        } else {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(I18n.translate(getFieldName()), getX(), getY() + 5, 16777215);
            this.resetButton.x = window.getScaledWidth() - getX() - resetButton.getWidth();
            this.sliderWidget.x = window.getScaledWidth() - getX() - 150;
            this.sliderWidget.setWidth(150 - resetButton.getWidth() - 2);
        }
        resetButton.render(mouse.x, mouse.y, v);
        sliderWidget.render(mouse.x, mouse.y, v);
    }
    
    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (sliderWidget.mouseClicked(double_1, double_2, int_1))
            return true;
        if (resetButton.mouseClicked(double_1, double_2, int_1))
            return true;
        return false;
    }
    
    @Override
    public boolean mouseDragged(double double_1, double double_2, int int_1, double double_3, double double_4) {
        if (sliderWidget.mouseDragged(double_1, double_2, int_1, double_3, double_4))
            return true;
        return false;
    }
    
    private class Slider extends SliderWidget {
        
        protected Slider(int int_1, int int_2, int int_3, int int_4, double double_1) {
            super(int_1, int_2, int_3, int_4, double_1);
        }
        
        @Override
        public void updateText() {
            setMessage(textGetter.apply(LongSliderEntry.this.value.get()));
        }
        
        @Override
        protected void onProgressChanged() {
            LongSliderEntry.this.value.set((long) (minimum + Math.abs(maximum - minimum) * progress));
            ((ClothConfigScreen.ListWidget) getParent()).getScreen().setEdited(true);
        }
        
        public double getProgress() {
            return progress;
        }
        
        public void setProgress(double integer) {
            this.progress = integer;
        }
    }
}
