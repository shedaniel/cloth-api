package me.shedaniel.cloth.hooks;

import me.shedaniel.cloth.api.EventManager;
import me.shedaniel.cloth.events.*;

// TODO: Rename to ClothClientHooks in the next snapshot
public class ClothHooks {
    
    public static final EventManager<ClientHandleInputEvent> CLIENT_HANDLE_INPUT = EventManager.create(ClientHandleInputEvent.class);
    public static final EventManager<ClientSyncRecipesEvent> CLIENT_SYNC_RECIPES = EventManager.create(ClientSyncRecipesEvent.class);
    public static final EventManager<ClientDrawScreenEvent.Pre> CLIENT_PRE_DRAW_SCREEN = EventManager.create(ClientDrawScreenEvent.Pre.class);
    public static final EventManager<ClientDrawScreenEvent.Post> CLIENT_POST_DRAW_SCREEN = EventManager.create(ClientDrawScreenEvent.Post.class);
    public static final EventManager<ClientInitScreenEvent.Pre> CLIENT_PRE_INIT_SCREEN = EventManager.create(ClientInitScreenEvent.Pre.class);
    public static final EventManager<ClientInitScreenEvent.Post> CLIENT_POST_INIT_SCREEN = EventManager.create(ClientInitScreenEvent.Post.class);
    public static final EventManager<ClientScreenAddButtonEvent> CLIENT_SCREEN_ADD_BUTTON = EventManager.create(ClientScreenAddButtonEvent.class);
    public static final EventManager<ClientScreenGetFocusedEvent> CLIENT_SCREEN_GET_FOCUSED = EventManager.create(ClientScreenGetFocusedEvent.class);
    public static final EventManager<ClientScreenMouseScrolledEvent> CLIENT_SCREEN_MOUSE_SCROLLED = EventManager.create(ClientScreenMouseScrolledEvent.class);
    
}
