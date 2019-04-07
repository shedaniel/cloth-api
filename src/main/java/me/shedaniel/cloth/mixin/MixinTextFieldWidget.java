package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.hooks.TextFieldWidgetHooks;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextFieldWidget.class)
public class MixinTextFieldWidget implements TextFieldWidgetHooks {
    
    @Shadow
    private int y;
    
    @Shadow
    @Mutable
    @Final
    private int width;
    
    @Override
    public void cloth_setY(int y) {
        this.y = y;
    }
    
    @Override
    public int cloth_getWidth() {
        return width;
    }
    
    @Override
    public void cloth_setWidth(int width) {
        this.width = width;
    }
    
}
