package me.shedaniel.cloth.gui.entries;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LongListEntry extends TextFieldListEntry<Long> {
    
    private static Function<String, String> stripCharacters = s -> {
        StringBuilder stringBuilder_1 = new StringBuilder();
        char[] var2 = s.toCharArray();
        int var3 = var2.length;
        
        for(int var4 = 0; var4 < var3; ++var4)
            if (Character.isDigit(var2[var4]) || var2[var4] == '-')
                stringBuilder_1.append(var2[var4]);
        
        return stringBuilder_1.toString();
    };
    private long minimum, maximum;
    private Consumer<Long> saveConsumer;
    
    public LongListEntry(String fieldName, Long value, Consumer<Long> saveConsumer) {
        this(fieldName, value, "text.cloth.reset_value", null, saveConsumer);
    }
    
    public LongListEntry(String fieldName, Long value, String resetButtonKey, Supplier<Long> defaultValue, Consumer<Long> saveConsumer) {
        super(fieldName, value, resetButtonKey, defaultValue);
        this.minimum = -Long.MAX_VALUE;
        this.maximum = Long.MAX_VALUE;
        this.textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 148, 18) {
            @Override
            public void addText(String string_1) {
                super.addText(stripCharacters.apply(string_1));
            }
            
            @Override
            public void render(int int_1, int int_2, float float_1) {
                try {
                    long i = Long.valueOf(textFieldWidget.getText());
                    if (i < minimum || i > maximum)
                        method_1868(16733525);
                    else
                        method_1868(14737632);
                } catch (NumberFormatException ex) {
                    method_1868(16733525);
                }
                super.render(int_1, int_2, float_1);
            }
        };
        textFieldWidget.setText(String.valueOf(value));
        textFieldWidget.setMaxLength(999999);
        textFieldWidget.setChangedListener(s -> {
            if (!original.equals(s))
                getScreen().setEdited(true);
        });
        this.saveConsumer = saveConsumer;
        this.widgets = Lists.newArrayList(textFieldWidget, resetButton);
    }
    
    @Override
    public void save() {
        if (saveConsumer != null)
            saveConsumer.accept(getObject());
    }
    
    @Override
    protected boolean isMatchDefault(String text) {
        return getDefaultValue().isPresent() ? text.equals(defaultValue.get().toString()) : false;
    }
    
    public LongListEntry setMinimum(long minimum) {
        this.minimum = minimum;
        return this;
    }
    
    public LongListEntry setMaximum(long maximum) {
        this.maximum = maximum;
        return this;
    }
    
    @Override
    public Long getObject() {
        try {
            return Long.valueOf(textFieldWidget.getText());
        } catch (Exception e) {
            return 0l;
        }
    }
    
    @Override
    public Optional<String> getError() {
        try {
            long i = Long.valueOf(textFieldWidget.getText());
            if (i > maximum)
                return Optional.of(I18n.translate("text.cloth.error.too_large", maximum));
            else if (i < minimum)
                return Optional.of(I18n.translate("text.cloth.error.too_small", minimum));
        } catch (NumberFormatException ex) {
            return Optional.of(I18n.translate("text.cloth.error.not_valid_number_long"));
        }
        return super.getError();
    }
}
