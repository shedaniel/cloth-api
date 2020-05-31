# Cloth API
## Maven
## APIs
#### cloth-armor-api-v1
**To be implemented:**
- CustomModeledArmor: A custom BipedEntityModel for your armor
- CustomTexturedArmor: A custom Identifier texture for your armor
- TickableArmor: Tick when the armor piece is worn
#### cloth-client-events-v0
**Events:**
- ~~ClothClientHooks#HANDLE_INPUT: Called on MinecraftClient#handleInputEvents~~
- ClothClientHooks#SYNC_RECIPES: Called on ClientPlayNetworkHandler#onSynchronizeRecipes
- ClothClientHooks#SCREEN_RENDER_PRE: Called before Screen#render
- ClothClientHooks#SCREEN_RENDER_POST: Called after Screen#render
- ClothClientHooks#SCREEN_LATE_RENDER: Called after Screen#render after everything
- ClothClientHooks#SCREEN_INIT_PRE: Called before Screen#init
- ClothClientHooks#SCREEN_INIT_POST: Called after Screen#init
- ClothClientHooks#SCREEN_ADD_BUTTON: Called on Screen#addButton
- ClothClientHooks#SCREEN_MOUSE_SCROLLED: Called on mouseScrolled
- ClothClientHooks#SCREEN_MOUSE_CLICKED: Called on mouseClicked
- ClothClientHooks#SCREEN_MOUSE_RELEASED: Called on mouseReleased
- ClothClientHooks#SCREEN_MOUSE_DRAGGED: Called on mouseDragged
- ClothClientHooks#SCREEN_CHAR_TYPED: Called on charTyped
- ClothClientHooks#SCREEN_KEY_PRESSED: Called before keyPressed
- ClothClientHooks#SCREEN_POST_KEY_PRESSED: Called after keyPressed
- ClothClientHooks#SCREEN_KEY_RELEASED: Called before keyReleased
- ClothClientHooks#SCREEN_POST_KEY_PRESSED: Called after keyReleased
- ClothClientHooks#DEBUG_RENDER_PRE: Called before DebugRenderer#render
#### cloth-common-events-v1
**Events:**
- BlockBreakCallback#EVENT: Called on block break by player
- BlockPlaceCallback#EVENT: Called on block place by player
- ItemPickupCallback#EVENT: Called on item pickup by player
- PlayerChangeWorldCallback#EVENT: Called on player changing worlds
- PlayerJoinCallback#EVENT: Called on player joining
- PlayerLeaveCallback#EVENT: Called on player leaving
- WorldLoadCallback#EVENT: Called on world loading
- WorldSaveCallback#EVENT: Called on world saving
#### cloth-durability-bar-api-v1
**To be implemented:**
- DurabilityBarItem: Custom durability bar display
#### cloth-scissors-api-v1
**Utils:**
- ScissorsStack: Stacking support for scissors
#### cloth-utils-v1
**Utils:**
- Executor
- GameInstanceUtils: Method to get the MinecraftServer instance