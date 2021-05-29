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

package me.shedaniel.cloth.api.client.events.v0;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public class ClothClientHooks {
    @Deprecated
    public static final Event<HandleInputCallback> HANDLE_INPUT = EventFactory.createArrayBacked(HandleInputCallback.class, listeners -> {
        return client -> {
            for (HandleInputCallback listener : listeners)
                listener.handleInput(client);
        };
    });
    public static final Event<SyncRecipesCallback> SYNC_RECIPES = EventFactory.createArrayBacked(SyncRecipesCallback.class, listeners -> {
        return (client, manager, packet) -> {
            for (SyncRecipesCallback listener : listeners)
                listener.syncRecipes(client, manager, packet);
        };
    });
    public static final Event<ScreenRenderCallback.Pre> SCREEN_RENDER_PRE = EventFactory.createArrayBacked(ScreenRenderCallback.Pre.class, listeners -> {
        return (matrices, client, screen, mouseX, mouseY, delta) -> {
            for (ScreenRenderCallback.Pre listener : listeners) {
                ActionResult result = listener.render(matrices, client, screen, mouseX, mouseY, delta);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenRenderCallback.Post> SCREEN_RENDER_POST = EventFactory.createArrayBacked(ScreenRenderCallback.Post.class, listeners -> {
        return (matrices, client, screen, mouseX, mouseY, delta) -> {
            for (ScreenRenderCallback.Post listener : listeners)
                listener.render(matrices, client, screen, mouseX, mouseY, delta);
        };
    });
    public static final Event<ScreenRenderCallback.Post> SCREEN_LATE_RENDER = EventFactory.createArrayBacked(ScreenRenderCallback.Post.class, listeners -> {
        return (matrices, client, screen, mouseX, mouseY, delta) -> {
            for (ScreenRenderCallback.Post listener : listeners)
                listener.render(matrices, client, screen, mouseX, mouseY, delta);
        };
    });
    public static final Event<ScreenInitCallback.Pre> SCREEN_INIT_PRE = EventFactory.createArrayBacked(ScreenInitCallback.Pre.class, listeners -> {
        return (client, screen, hooks) -> {
            for (ScreenInitCallback.Pre listener : listeners) {
                ActionResult result = listener.init(client, screen, hooks);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenInitCallback.Post> SCREEN_INIT_POST = EventFactory.createArrayBacked(ScreenInitCallback.Post.class, listeners -> {
        return (client, screen, hooks) -> {
            for (ScreenInitCallback.Post listener : listeners)
                listener.init(client, screen, hooks);
        };
    });
    public static final Event<ScreenMouseScrolledCallback> SCREEN_MOUSE_SCROLLED = EventFactory.createArrayBacked(ScreenMouseScrolledCallback.class, listeners -> {
        return (client, screen, mouseX, mouseY, amount) -> {
            for (ScreenMouseScrolledCallback listener : listeners) {
                ActionResult result = listener.mouseScrolled(client, screen, mouseX, mouseY, amount);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenMouseClickedCallback> SCREEN_MOUSE_CLICKED = EventFactory.createArrayBacked(ScreenMouseClickedCallback.class, listeners -> {
        return (client, screen, mouseX, mouseY, button) -> {
            for (ScreenMouseClickedCallback listener : listeners) {
                ActionResult result = listener.mouseClicked(client, screen, mouseX, mouseY, button);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenMouseReleasedCallback> SCREEN_MOUSE_RELEASED = EventFactory.createArrayBacked(ScreenMouseReleasedCallback.class, listeners -> {
        return (client, screen, mouseX, mouseY, button) -> {
            for (ScreenMouseReleasedCallback listener : listeners) {
                ActionResult result = listener.mouseReleased(client, screen, mouseX, mouseY, button);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenMouseDraggedCallback> SCREEN_MOUSE_DRAGGED = EventFactory.createArrayBacked(ScreenMouseDraggedCallback.class, listeners -> {
        return (client, screen, mouseX1, mouseY1, button, mouseX2, mouseY2) -> {
            for (ScreenMouseDraggedCallback listener : listeners) {
                ActionResult result = listener.mouseDragged(client, screen, mouseX1, mouseY1, button, mouseX2, mouseY2);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenKeyTypedCallback> SCREEN_CHAR_TYPED = EventFactory.createArrayBacked(ScreenKeyTypedCallback.class, listeners -> {
        return (client, screen, character, keyCode) -> {
            for (ScreenKeyTypedCallback listener : listeners) {
                ActionResult result = listener.charTyped(client, screen, character, keyCode);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenKeyPressedCallback> SCREEN_KEY_PRESSED = EventFactory.createArrayBacked(ScreenKeyPressedCallback.class, listeners -> {
        return (client, screen, keyCode, scanCode, modifiers) -> {
            for (ScreenKeyPressedCallback listener : listeners) {
                ActionResult result = listener.keyPressed(client, screen, keyCode, scanCode, modifiers);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenKeyPressedCallback> SCREEN_POST_KEY_PRESSED = EventFactory.createArrayBacked(ScreenKeyPressedCallback.class, listeners -> {
        return (client, screen, keyCode, scanCode, modifiers) -> {
            for (ScreenKeyPressedCallback listener : listeners) {
                ActionResult result = listener.keyPressed(client, screen, keyCode, scanCode, modifiers);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenKeyReleasedCallback> SCREEN_KEY_RELEASED = EventFactory.createArrayBacked(ScreenKeyReleasedCallback.class, listeners -> {
        return (client, screen, keyCode, scanCode, modifiers) -> {
            for (ScreenKeyReleasedCallback listener : listeners) {
                ActionResult result = listener.keyReleased(client, screen, keyCode, scanCode, modifiers);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<ScreenKeyReleasedCallback> SCREEN_POST_KEY_RELEASED = EventFactory.createArrayBacked(ScreenKeyReleasedCallback.class, listeners -> {
        return (client, screen, keyCode, scanCode, modifiers) -> {
            for (ScreenKeyReleasedCallback listener : listeners) {
                ActionResult result = listener.keyReleased(client, screen, keyCode, scanCode, modifiers);
                if (result != ActionResult.PASS)
                    return result;
            }
            return ActionResult.PASS;
        };
    });
    public static final Event<Runnable> DEBUG_RENDER_PRE = EventFactory.createArrayBacked(Runnable.class, listeners -> {
        return () -> {
            for (Runnable listener : listeners)
                listener.run();
        };
    });
}
