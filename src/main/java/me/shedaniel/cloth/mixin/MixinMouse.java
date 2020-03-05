package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.hooks.ClothClientHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Mouse.class)
public class MixinMouse {
    
    @Shadow @Final private MinecraftClient client;
    
    @Shadow private int activeButton;
    
    @Shadow private double x;
    
    @Shadow private double y;
    
    @Inject(method = "onMouseScroll",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;mouseScrolled(DDD)Z",
                     ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void onMouseScrolled(long long_1, double double_1, double double_2, CallbackInfo info, double amount, double x, double y) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_MOUSE_SCROLLED.invoker().mouseScrolled(client, client.currentScreen, x, y, amount);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "onMouseButton", at = @At(value = "INVOKE",
                                               target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
                                               ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void onMouseClicked(long long_1, int button, int int_2, int int_3, CallbackInfo info, boolean bl, int i, boolean[] bls, double d, double e) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_MOUSE_CLICKED.invoker().mouseClicked(client, client.currentScreen, d, e, button);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "onMouseButton", at = @At(value = "INVOKE",
                                               target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
                                               ordinal = 1), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void onMouseReleased(long long_1, int button, int int_2, int int_3, CallbackInfo info, boolean bl, int i, boolean[] bls, double d, double e) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_MOUSE_RELEASED.invoker().mouseReleased(client, client.currentScreen, d, e, button);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "onCursorPos", at = @At(value = "INVOKE",
                                             target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
                                             ordinal = 1), cancellable = true, locals = LocalCapture.PRINT)
    public void onMouseDragged(long window, double x, double y, CallbackInfo info, Element element, double d, double e, double f, double g) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_MOUSE_DRAGGED.invoker().mouseDragged(client, (Screen) element, d, e, activeButton, f, g);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
}
