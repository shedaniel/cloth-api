package me.shedaniel.cloth.hooks;

import me.shedaniel.cloth.api.EventManager;
import me.shedaniel.cloth.events.client.*;

public class ClothClientHooks {
    
    public static final EventManager<HandleInputEvent> HANDLE_INPUT = EventManager.create(HandleInputEvent.class);
    public static final EventManager<SyncRecipesEvent> SYNC_RECIPES = EventManager.create(SyncRecipesEvent.class);
    public static final EventManager<ScreenDrawEvent.Pre> SCREEN_DRAW_PRE = EventManager.create(ScreenDrawEvent.Pre.class);
    public static final EventManager<ScreenDrawEvent.Post> SCREEN_DRAW_POST = EventManager.create(ScreenDrawEvent.Post.class);
    public static final EventManager<ScreenInitEvent.Pre> SCREEN_INIT_PRE = EventManager.create(ScreenInitEvent.Pre.class);
    public static final EventManager<ScreenInitEvent.Post> SCREEN_INIT_POST = EventManager.create(ScreenInitEvent.Post.class);
    public static final EventManager<ScreenAddButtonEvent> SCREEN_ADD_BUTTON = EventManager.create(ScreenAddButtonEvent.class);
    public static final EventManager<ScreenGetFocusedEvent> SCREEN_GET_FOCUSED = EventManager.create(ScreenGetFocusedEvent.class);
    public static final EventManager<ScreenMouseScrolledEvent> SCREEN_MOUSE_SCROLLED = EventManager.create(ScreenMouseScrolledEvent.class);
    public static final EventManager<ScreenMouseClickedEvent> SCREEN_MOUSE_CLICKED = EventManager.create(ScreenMouseClickedEvent.class);
    public static final EventManager<ScreenMouseReleasedEvent> SCREEN_MOUSE_RELEASED = EventManager.create(ScreenMouseReleasedEvent.class);
    public static final EventManager<ScreenKeyTypedEvent> SCREEN_KEY_TYPED = EventManager.create(ScreenKeyTypedEvent.class);
    
}
