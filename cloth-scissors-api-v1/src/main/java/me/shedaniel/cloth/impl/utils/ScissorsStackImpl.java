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

package me.shedaniel.cloth.impl.utils;

import com.google.common.collect.Lists;
import me.shedaniel.cloth.api.utils.v1.Executor;
import me.shedaniel.cloth.api.utils.v1.ScissorsStack;
import me.shedaniel.math.Rectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.List;

@Environment(EnvType.CLIENT)
public final class ScissorsStackImpl implements ScissorsStack {
    private final List<Rectangle> scissorsAreas = Lists.newArrayList();
    private final Rectangle empty = new Rectangle();
    private static final Logger LOGGER = LogManager.getFormatterLogger("cloth-scissors-api-v1");
    
    static {
        Executor.runIf(() -> FabricLoader.getInstance().isModLoaded("notenoughcrashes"), () -> () -> {
            try {
                Class.forName("fudge.notenoughcrashes.api.NotEnoughCrashesApi").getDeclaredMethod("onEveryCrash", Runnable.class).invoke(null, (Runnable) () -> {
                    try {
                        ScissorsStack.INSTANCE.popAll().applyStack();
                    } catch (Throwable t) {
                        LOGGER.error("[ClothConfig] Failed to clear scissors on game crash!", t);
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    
    @Override
    public ScissorsStack applyScissors(Rectangle rectangle) {
        if (rectangle.isEmpty()) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        } else {
            Window window = MinecraftClient.getInstance().getWindow();
            double scaleFactor = window.getScaleFactor();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor((int) (rectangle.x * scaleFactor), (int) ((window.getScaledHeight() - rectangle.height - rectangle.y) * scaleFactor), (int) (rectangle.width * scaleFactor), (int) (rectangle.height * scaleFactor));
        }
        return this;
    }
    
    @Override
    public ScissorsStack push(Rectangle rectangle) {
        scissorsAreas.add(rectangle);
        return this;
    }
    
    @Override
    public ScissorsStack pushAll(Collection<Rectangle> rectangles) {
        scissorsAreas.addAll(rectangles);
        return this;
    }
    
    @Override
    public ScissorsStack pop() {
        if (scissorsAreas.isEmpty())
            throw new IllegalStateException("There is no entries in the stack!");
        scissorsAreas.remove(scissorsAreas.size() - 1);
        return this;
    }
    
    @Override
    public List<Rectangle> getCurrentStack() {
        return scissorsAreas;
    }
    
    @Override
    public ScissorsStack applyStack() {
        if (scissorsAreas.isEmpty()) {
            return applyScissors(empty);
        }
        Rectangle rectangle = null;
        for (Rectangle area : scissorsAreas) {
            if (rectangle == null) {
                rectangle = area.clone();
            } else {
                rectangle = rectangle.intersection(area);
            }
        }
        return applyScissors(rectangle);
    }
}
