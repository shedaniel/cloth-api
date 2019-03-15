package me.shedaniel.cloth.mixin;

import io.github.prospector.modmenu.gui.ModListScreen;
import me.shedaniel.cloth.hooks.ClothModMenuHooks;
import me.shedaniel.cloth.hooks.ModMenuHooks;
import me.shedaniel.cloth.hooks.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ButtonWidget.class)
public abstract class MixinButtonWidget extends AbstractButtonWidget {
    
    public MixinButtonWidget(int int_1, int int_2, String string_1) {
        super(int_1, int_2, string_1);
    }
    
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void onMouseClicked(double double_1, double double_2, int int_1, CallbackInfoReturnable<Boolean> info) {
        if (ClothModMenuHooks.isModMenuLoaded && MinecraftClient.getInstance().currentScreen instanceof ModListScreen) {
            if (((ScreenHooks) MinecraftClient.getInstance().currentScreen).cloth_getButtonWidgets().get(1).equals((ButtonWidget) (Object) this) && enabled && visible && int_1 == 0 && isSelected(double_1, double_2)) {
                playPressedSound(MinecraftClient.getInstance().getSoundLoader());
                if (ModMenuHooks.onButtonClicked != null)
                    ModMenuHooks.onButtonClicked.run();
                info.setReturnValue(true);
            }
        }
    }
    
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void onKeyPressed(int int_1, int int_2, int int_3, CallbackInfoReturnable<Boolean> info) {
        if (ClothModMenuHooks.isModMenuLoaded && MinecraftClient.getInstance().currentScreen instanceof ModListScreen) {
            if (((ScreenHooks) MinecraftClient.getInstance().currentScreen).cloth_getButtonWidgets().get(1).equals((ButtonWidget) (Object) this) && enabled && visible) {
                if (int_1 != 257 && int_1 != 32 && int_1 != 335)
                    return;
                playPressedSound(MinecraftClient.getInstance().getSoundLoader());
                if (ModMenuHooks.onButtonClicked != null)
                    ModMenuHooks.onButtonClicked.run();
                info.setReturnValue(true);
            }
        }
    }
    
}
