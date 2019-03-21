package me.shedaniel.cloth.gui.entries;

import java.util.function.Consumer;

public class StringListEntry extends TextFieldListEntry<String> {
    
    private Consumer<String> saveConsumer;
    
    public StringListEntry(String fieldName, String value, Consumer<String> saveConsumer) {
        super(fieldName, value);
        this.saveConsumer = saveConsumer;
    }
    
    @Override
    public String getObject() {
        return textFieldWidget.getText();
    }
    
    @Override
    public void save() {
        if (saveConsumer != null)
            saveConsumer.accept(getObject());
    }
    
}
