package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.hooks.ClothClientHooks;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    
    @Shadow @Final private MinecraftClient client;
    
    @Shadow private boolean repeatEvents;
    
    @Inject(method = "onChar", at = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
                                        ordinal = 0), cancellable = true)
    public void onCharFirst(long long_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_CHAR_TYPED.invoker().charTyped(client, client.currentScreen, (char) int_1, int_2);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "onChar", at = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
                                        ordinal = 1), cancellable = true)
    public void onCharSecond(long long_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_CHAR_TYPED.invoker().charTyped(client, client.currentScreen, (char) int_1, int_2);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "onKey", at = @At(value = "INVOKE",
                                       target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
                                       ordinal = 0), cancellable = true)
    public void onKey(long long_1, int int_1, int int_2, int int_3, int int_4, CallbackInfo info) {
        if (!info.isCancelled()) {
            if (int_3 != 1 && (int_3 != 2 || !this.repeatEvents)) {
                ActionResult result = ClothClientHooks.SCREEN_KEY_RELEASED.invoker().keyReleased(client, client.currentScreen, int_1, int_2, int_4);
                if (result != ActionResult.PASS)
                    info.cancel();
            } else {
                ActionResult result = ClothClientHooks.SCREEN_KEY_PRESSED.invoker().keyPressed(client, client.currentScreen, int_1, int_2, int_4);
                if (result != ActionResult.PASS)
                    info.cancel();
            }
        }
    }
    
}
