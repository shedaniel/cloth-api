package me.shedaniel.cloth.gui.entries;

public class StringListEntry extends TextFieldListEntry<String> {
    
    public StringListEntry(String fieldName, String value) {
        super(fieldName, value);
    }
    
    @Override
    public Object getObject() {
        return textFieldWidget.getText();
    }
    
}
