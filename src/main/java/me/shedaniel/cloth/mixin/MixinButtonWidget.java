package me.shedaniel.cloth.mixin;

import io.github.prospector.modmenu.gui.ModListScreen;
import me.shedaniel.cloth.api.ScreenHooks;
import me.shedaniel.cloth.hooks.ClothModMenuHooks;
import me.shedaniel.cloth.hooks.ModMenuHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.SoundLoader;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ButtonWidget.class)
public abstract class MixinButtonWidget {
    
    @Shadow protected abstract boolean isSelected(double double_1, double double_2);
    
    @Shadow public abstract void playPressedSound(SoundLoader soundLoader_1);
    
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void onMouseClicked(double double_1, double double_2, int int_1, CallbackInfoReturnable<Boolean> info) {
        if (ClothModMenuHooks.isModMenuLoaded && MinecraftClient.getInstance().currentScreen instanceof ModListScreen) {
            if (((ScreenHooks) MinecraftClient.getInstance().currentScreen).getButtonWidgets().get(1).equals((ButtonWidget) (Object) this) && int_1 == 0 && isSelected(double_1, double_2)) {
                this.playPressedSound(MinecraftClient.getInstance().getSoundLoader());
                if (ModMenuHooks.onButtonClicked != null)
                    ModMenuHooks.onButtonClicked.run();
                info.setReturnValue(true);
            }
        }
    }
    
}
