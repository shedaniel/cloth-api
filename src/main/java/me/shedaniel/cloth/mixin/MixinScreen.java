package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.hooks.ClothClientHooks;
import me.shedaniel.cloth.hooks.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Screen.class)
public abstract class MixinScreen implements ScreenHooks {
    
    @Shadow
    protected MinecraftClient client;
    
    @Shadow(remap = false)
    @Final
    protected List<AbstractButtonWidget> buttons;
    
    @Shadow(remap = false)
    protected abstract <T extends AbstractButtonWidget> T addButton(T abstractButtonWidget_1);
    
    @Override
    public List<AbstractButtonWidget> cloth_getButtonWidgets() {
        return buttons;
    }
    
    @Override
    public List<InputListener> cloth_getInputListeners() {
        return (List) ((Screen) (Object) this).getInputListeners();
    }
    
    @Override
    public AbstractButtonWidget cloth_addButton(AbstractButtonWidget buttonWidget) {
        addButton(buttonWidget);
        return buttonWidget;
    }
    
    @Inject(method = "render(IIF)V", at = @At("HEAD"), cancellable = true, remap = false)
    public void onPreDraw(int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_RENDER_PRE.invoker().render(client, (Screen) (Object) this, mouseX, mouseY, delta);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "render(IIF)V", at = @At("RETURN"), remap = false)
    public void onPostDraw(int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (!info.isCancelled())
            ClothClientHooks.SCREEN_RENDER_POST.invoker().render(client, (Screen) (Object) this, mouseX, mouseY, delta);
    }
    
    @Inject(method = "initialize", at = @At("HEAD"), cancellable = true)
    public void onPreInit(MinecraftClient minecraftClient_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_INIT_PRE.invoker().init(client, (Screen) (Object) this, (ScreenHooks) (Screen) (Object) this);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
    @Inject(method = "initialize", at = @At("RETURN"))
    public void onPostInit(MinecraftClient minecraftClient_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled())
            ClothClientHooks.SCREEN_INIT_POST.invoker().init(client, (Screen) (Object) this, (ScreenHooks) (Screen) (Object) this);
    }
    
    @Inject(method = "addButton", at = @At("HEAD"), cancellable = true, remap = false)
    public void onAddButton(AbstractButtonWidget widget, CallbackInfoReturnable<ButtonWidget> info) {
        if (!info.isCancelled()) {
            ActionResult result = ClothClientHooks.SCREEN_ADD_BUTTON.invoker().addButton(client, (Screen) (Object) this, widget);
            if (result != ActionResult.PASS)
                info.cancel();
        }
    }
    
}
