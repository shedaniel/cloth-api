/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org>
 */

package me.shedaniel.cloth.mixin.client.events;

import me.shedaniel.cloth.api.client.events.v0.ClothClientHooks;
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
    @Shadow
    @Final 
    private MinecraftClient client;
    
    @Shadow 
    private int activeButton;
    
    @Shadow
    private double x;
    
    @Shadow 
    private double y;
    
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
    
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "method_1602", at = @At("HEAD"), cancellable = true)
    public void onMouseDragged(Element element, double d, double e, double f, double g, CallbackInfo info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_MOUSE_DRAGGED.invoker().mouseDragged(client, (Screen) element, d, e, activeButton, f, g);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
}
