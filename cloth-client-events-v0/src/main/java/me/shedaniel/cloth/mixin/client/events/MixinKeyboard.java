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
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Shadow
    @Final
    private MinecraftClient client;
    
    @Shadow
    private boolean repeatEvents;
    
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
                if (int_3 == 0) {
                    ActionResult result = ClothClientHooks.SCREEN_KEY_RELEASED.invoker().keyReleased(client, client.currentScreen, int_1, int_2, int_4);
                    if (result != ActionResult.PASS)
                        info.cancel();
                }
            } else {
                ActionResult result = ClothClientHooks.SCREEN_KEY_PRESSED.invoker().keyPressed(client, client.currentScreen, int_1, int_2, int_4);
                if (result != ActionResult.PASS)
                    info.cancel();
            }
        }
    }
    
    @Inject(method = "onKey", at = @At(value = "INVOKE",
                                       target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V",
                                       ordinal = 0, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    public void onKeyAfter(long long_1, int int_1, int int_2, int int_3, int int_4, CallbackInfo info, Screen screen, boolean bl, boolean[] bls) {
        if (!info.isCancelled() && !bls[0]) {
            ActionResult result;
            if (int_3 != 1 && (int_3 != 2 || !this.repeatEvents)) {
                result = ClothClientHooks.SCREEN_POST_KEY_RELEASED.invoker().keyReleased(client, screen, int_1, int_2, int_4);
            } else {
                result = ClothClientHooks.SCREEN_POST_KEY_PRESSED.invoker().keyPressed(client, screen, int_1, int_2, int_4);
            }
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
}
