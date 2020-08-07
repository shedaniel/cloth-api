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

package me.shedaniel.cloth.api.utils.v1;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

public final class GameInstanceUtils {
    private static MinecraftServer server = null;
    
    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> GameInstanceUtils.server = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> GameInstanceUtils.server = null);
    }
    
    public static MinecraftServer getServer() {
        MinecraftServer server = null;
        if (GameInstanceUtils.server != null) server = GameInstanceUtils.server;
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            server = getServerFromClient();
        }
        return server;
    }
    
    @Environment(EnvType.CLIENT)
    private static MinecraftServer getServerFromClient() {
        return MinecraftClient.getInstance().getServer();
    }
}
