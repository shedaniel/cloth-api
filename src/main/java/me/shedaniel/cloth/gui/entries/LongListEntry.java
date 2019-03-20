package me.shedaniel.cloth.gui.entries;

import me.shedaniel.cloth.gui.ClothConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;

import java.util.Optional;
import java.util.function.Function;

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
    
    public LongListEntry(String fieldName, Long value) {
        super(fieldName, value);
        this.textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 148, 18) {
            @Override
            public void addText(String string_1) {
                super.addText(stripCharacters.apply(string_1));
            }
            
            @Override
            public void draw(int int_1, int int_2, float float_1) {
                try {
                    Long.valueOf(textFieldWidget.getText());
                    method_1868(14737632);
                } catch (NumberFormatException ex) {
                    method_1868(16733525);
                }
                super.draw(int_1, int_2, float_1);
            }
        };
        textFieldWidget.setText(String.valueOf(value));
        textFieldWidget.setMaxLength(999999);
        textFieldWidget.setChangedListener(s -> {
            if (!original.equals(s))
                ((ClothConfigScreen.ListWidget) getParent()).getScreen().setEdited(true);
        });
    }
    
    @Override
    public Object getObject() {
        try {
            return Long.valueOf(textFieldWidget.getText());
        } catch (Exception e) {
            return 0l;
        }
    }
    
    @Override
    public Optional<String> getError() {
        try {
            Long.valueOf(textFieldWidget.getText());
        } catch (NumberFormatException ex) {
            return Optional.of(I18n.translate("text.cloth.error.not_valid_number_long"));
        }
        return super.getError();
    }
}
