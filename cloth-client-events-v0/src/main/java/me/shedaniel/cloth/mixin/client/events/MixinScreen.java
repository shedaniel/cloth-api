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
import me.shedaniel.cloth.api.client.events.v0.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Screen.class)
public abstract class MixinScreen implements ScreenHooks {
    @Shadow
    @Final
    protected List<AbstractButtonWidget> buttons;
    
    @Shadow
    protected MinecraftClient client;
    
    @Shadow
    @Mutable
    @Final
    protected Text title;
    
    @Shadow
    protected abstract <T extends AbstractButtonWidget> T addButton(T abstractButtonWidget_1);
    
    @Override
    public List<AbstractButtonWidget> cloth_getButtonWidgets() {
        return buttons;
    }
    
    @Override
    public List<Element> cloth_getChildren() {
        return (List<Element>) ((Screen) (Object) this).children();
    }
    
    @Override
    public AbstractButtonWidget cloth_addButton(AbstractButtonWidget buttonWidget) {
        addButton(buttonWidget);
        return buttonWidget;
    }
    
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void onPreDraw(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_RENDER_PRE.invoker().render(matrices, client, (Screen) (Object) this, mouseX, mouseY, delta);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "render", at = @At("RETURN"), remap = false)
    public void onPostDraw(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (!info.isCancelled())
            ClothClientHooks.SCREEN_RENDER_POST.invoker().render(matrices, client, (Screen) (Object) this, mouseX, mouseY, delta);
    }
    
    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;II)V", at = @At("HEAD"), cancellable = true)
    public void onPreInit(MinecraftClient minecraftClient_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_INIT_PRE.invoker().init(minecraftClient_1, (Screen) (Object) this, this);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;II)V", at = @At("RETURN"), remap = false)
    public void onPostInit(MinecraftClient minecraftClient_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled())
            ClothClientHooks.SCREEN_INIT_POST.invoker().init(minecraftClient_1, (Screen) (Object) this, this);
    }
    
    @Inject(method = "addButton", at = @At("HEAD"), cancellable = true)
    public void onAddButton(AbstractButtonWidget widget, CallbackInfoReturnable<ButtonWidget> info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_ADD_BUTTON.invoker().addButton(client, (Screen) (Object) this, widget);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Override
    public void cloth_setTitle(Text component) {
        this.title = component;
    }
}
