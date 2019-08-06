# Cloth [ ![Download](https://api.bintray.com/packages/shedaniel/cloth-events/cloth-events/images/download.svg) ]
### Maven
```groovy
repositories {
    jcenter()
}
dependencies {
    'me.shedaniel.cloth:cloth-events:ABC_VERSION'
}
```
### APIs
###### Events
These are all the events in ClothClientHooks:
- HANDLE_INPUT: Hook into MinecraftClient's handleInputEvents()
- SYNC_RECIPES: Hook into client receiving the recipes sync packet
- SCREEN_RENDER_PRE: Hook before Screen's render (does not do anything if screen completely overrides render)
- SCREEN_RENDER_POST: Hook after Screen's render (does not do anything if screen completely overrides render)
- SCREEN_LATE_RENDER: Hook after Screen's render (works even if screen completely overrides render)
- SCREEN_INIT_PRE: Hook before Screen's initScreen
- SCREEN_INIT_POST: Hook after Screen's initScreen
- SCREEN_ADD_BUTTON: Hook into Screen's addButton
- SCREEN_MOUSE_SCROLLED: Hook before Screen's mouseScrolled
- SCREEN_MOUSE_CLICKED: Hook before Screen's mouseClicked
- SCREEN_MOUSE_RELEASED: Hook before Screen's mouseReleased
- SCREEN_CHAR_TYPED: Hook before Screen's charTyped
- SCREEN_KEY_PRESSED: Hook before Screen's keyPressed
- SCREEN_KEY_RELEASED: Hook before Screen's keyReleased
- DEBUG_RENDER_PRE: Hook before GameRenderer's debug renders (like chunk borders)
