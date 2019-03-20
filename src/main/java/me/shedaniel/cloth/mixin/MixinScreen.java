package me.shedaniel.cloth.mixin;

import me.shedaniel.cloth.events.client.ScreenAddButtonEvent;
import me.shedaniel.cloth.events.client.ScreenDrawEvent;
import me.shedaniel.cloth.events.client.ScreenInitEvent;
import me.shedaniel.cloth.hooks.ClothClientHooks;
import me.shedaniel.cloth.hooks.ScreenHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.InputListener;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(Screen.class)
public abstract class MixinScreen implements ScreenHooks {
    
    @Shadow
    protected MinecraftClient client;
    
    @Shadow
    @Final
    protected List<AbstractButtonWidget> buttons;
    
    @Shadow
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
    
    @Inject(method = "draw(IIF)V", at = @At("HEAD"), cancellable = true)
    public void onPreDraw(int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (!info.isCancelled())
            for(Consumer<ScreenDrawEvent.Pre> sortedListener : ClothClientHooks.SCREEN_DRAW_PRE.getSortedListeners()) {
                ScreenDrawEvent.Pre event;
                sortedListener.accept(event = new ScreenDrawEvent.Pre(client, (Screen) (Object) this, mouseX, mouseY, delta));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
    @Inject(method = "draw(IIF)V", at = @At("TAIL"))
    public void onPostDraw(int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (!info.isCancelled())
            ClothClientHooks.SCREEN_DRAW_POST.invoke(new ScreenDrawEvent.Post(client, (Screen) (Object) this, mouseX, mouseY, delta));
    }
    
    @Inject(method = "initialize", at = @At("HEAD"), cancellable = true)
    public void onPreInit(MinecraftClient minecraftClient_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled())
            for(Consumer<ScreenInitEvent.Pre> sortedListener : ClothClientHooks.SCREEN_INIT_PRE.getSortedListeners()) {
                ScreenInitEvent.Pre event;
                sortedListener.accept(event = new ScreenInitEvent.Pre(minecraftClient_1, (Screen) (Object) this));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
    @Inject(method = "initialize", at = @At("TAIL"))
    public void onPostInit(MinecraftClient minecraftClient_1, int int_1, int int_2, CallbackInfo info) {
        if (!info.isCancelled())
            ClothClientHooks.SCREEN_INIT_POST.invoke(new ScreenInitEvent.Post(minecraftClient_1, (Screen) (Object) this));
    }
    
    @Inject(method = "addButton", at = @At("HEAD"), cancellable = true)
    public void onAddButton(AbstractButtonWidget widget, CallbackInfoReturnable<ButtonWidget> info) {
        if (!info.isCancelled())
            for(Consumer<ScreenAddButtonEvent> sortedListener : ClothClientHooks.SCREEN_ADD_BUTTON.getSortedListeners()) {
                ScreenAddButtonEvent event;
                sortedListener.accept(event = new ScreenAddButtonEvent(client, (Screen) (Object) this, widget));
                if (event.isCancelled()) {
                    info.cancel();
                    return;
                }
            }
    }
    
}
