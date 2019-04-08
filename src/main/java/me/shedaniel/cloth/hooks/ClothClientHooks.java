package me.shedaniel.cloth.hooks;

import me.shedaniel.cloth.callbacks.client.*;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public class ClothClientHooks {
    
    public static final Event<HandleInputCallback> HANDLE_INPUT = EventFactory.createArrayBacked(HandleInputCallback.class, listeners -> {
        return client -> {
            for(HandleInputCallback listener : listeners)
                listener.handleInput(client);
        };
    });
    public static final Event<SyncRecipesCallback> SYNC_RECIPES = EventFactory.createArrayBacked(SyncRecipesCallback.class, listeners -> {
        return (client, manager, packet) -> {
            for(SyncRecipesCallback listener : listeners)
                listener.syncRecipes(client, manager, packet);
        };
    });
    public static final Event<ScreenRenderCallback.Pre> SCREEN_RENDER_PRE = EventFactory.createArrayBacked(ScreenRenderCallback.Pre.class, listeners -> {
        return (client, screen, mouseX, mouseY, delta) -> {
            for(ScreenRenderCallback.Pre listener : listeners) {
                ActionResult result = listener.render(client, screen, mouseX, mouseY, delta);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenRenderCallback.Post> SCREEN_RENDER_POST = EventFactory.createArrayBacked(ScreenRenderCallback.Post.class, listeners -> {
        return (client, screen, mouseX, mouseY, delta) -> {
            for(ScreenRenderCallback.Post listener : listeners)
                listener.render(client, screen, mouseX, mouseY, delta);
        };
    });
    public static final Event<ScreenRenderCallback.Post> SCREEN_LATE_RENDER = EventFactory.createArrayBacked(ScreenRenderCallback.Post.class, listeners -> {
        return (client, screen, mouseX, mouseY, delta) -> {
            for(ScreenRenderCallback.Post listener : listeners)
                listener.render(client, screen, mouseX, mouseY, delta);
        };
    });
    public static final Event<ScreenInitCallback.Pre> SCREEN_INIT_PRE = EventFactory.createArrayBacked(ScreenInitCallback.Pre.class, listeners -> {
        return (client, screen, hooks) -> {
            for(ScreenInitCallback.Pre listener : listeners) {
                ActionResult result = listener.init(client, screen, hooks);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenInitCallback.Post> SCREEN_INIT_POST = EventFactory.createArrayBacked(ScreenInitCallback.Post.class, listeners -> {
        return (client, screen, hooks) -> {
            for(ScreenInitCallback.Post listener : listeners)
                listener.init(client, screen, hooks);
        };
    });
    public static final Event<ScreenAddButtonCallback> SCREEN_ADD_BUTTON = EventFactory.createArrayBacked(ScreenAddButtonCallback.class, listeners -> {
        return (client, screen, hooks) -> {
            for(ScreenAddButtonCallback listener : listeners) {
                ActionResult result = listener.addButton(client, screen, hooks);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenMouseScrolledCallback> SCREEN_MOUSE_SCROLLED = EventFactory.createArrayBacked(ScreenMouseScrolledCallback.class, listeners -> {
        return (client, screen, mouseX, mouseY, amount) -> {
            for(ScreenMouseScrolledCallback listener : listeners) {
                ActionResult result = listener.mouseScrolled(client, screen, mouseX, mouseY, amount);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenMouseClickedCallback> SCREEN_MOUSE_CLICKED = EventFactory.createArrayBacked(ScreenMouseClickedCallback.class, listeners -> {
        return (client, screen, mouseX, mouseY, button) -> {
            for(ScreenMouseClickedCallback listener : listeners) {
                ActionResult result = listener.mouseClicked(client, screen, mouseX, mouseY, button);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenMouseReleasedCallback> SCREEN_MOUSE_RELEASED = EventFactory.createArrayBacked(ScreenMouseReleasedCallback.class, listeners -> {
        return (client, screen, mouseX, mouseY, button) -> {
            for(ScreenMouseReleasedCallback listener : listeners) {
                ActionResult result = listener.mouseReleased(client, screen, mouseX, mouseY, button);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenKeyTypedCallback> SCREEN_CHAR_TYPED = EventFactory.createArrayBacked(ScreenKeyTypedCallback.class, listeners -> {
        return (client, screen, character, keyCode) -> {
            for(ScreenKeyTypedCallback listener : listeners) {
                ActionResult result = listener.charTyped(client, screen, character, keyCode);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenKeyPressedCallback> SCREEN_KEY_PRESSED = EventFactory.createArrayBacked(ScreenKeyPressedCallback.class, listeners -> {
        return (client, screen, keyCode, scanCode, modifiers) -> {
            for(ScreenKeyPressedCallback listener : listeners) {
                ActionResult result = listener.keyPressed(client, screen, keyCode, scanCode, modifiers);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenKeyReleasedCallback> SCREEN_KEY_RELEASED = EventFactory.createArrayBacked(ScreenKeyReleasedCallback.class, listeners -> {
        return (client, screen, keyCode, scanCode, modifiers) -> {
            for(ScreenKeyReleasedCallback listener : listeners) {
                ActionResult result = listener.keyReleased(client, screen, keyCode, scanCode, modifiers);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<Runnable> DEBUG_RENDER_PRE = EventFactory.createArrayBacked(Runnable.class, listeners -> {
        return () -> {
            for(Runnable listener : listeners)
                listener.run();
        };
    });
    
}
