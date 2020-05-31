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

import me.shedaniel.cloth.impl.utils.ScissorsStackImpl;
import me.shedaniel.math.Rectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collection;
import java.util.List;

/**
 * A simple scissors API that allows stacking.
 */
@Environment(EnvType.CLIENT)
public interface ScissorsStack {
    /**
     * The default instance of {@link ScissorsStack}.
     */
    ScissorsStack INSTANCE = new ScissorsStackImpl();
    
    /**
     * Applies scissors without adding to the stack.
     *
     * @param rectangle the scissors bounds, empty to clear.
     * @return the {@link ScissorsStack} instance itself.
     */
    ScissorsStack applyScissors(Rectangle rectangle);
    
    /**
     * Pushes a scissor bound without applying the changes,
     * please use {@link #applyStack()} to apply the changes.
     *
     * @param rectangle the scissors bound.
     * @return the {@link ScissorsStack} instance itself.
     */
    ScissorsStack push(Rectangle rectangle);
    
    /**
     * Pushes scissor bounds without applying the changes,
     * please use {@link #applyStack()} to apply the changes.
     *
     * @param rectangles the scissors bounds.
     * @return the {@link ScissorsStack} instance itself.
     */
    ScissorsStack pushAll(Collection<Rectangle> rectangles);
    
    /**
     * Pops the last scissor bound without applying the changes,
     * please use {@link #applyStack()} to apply the changes.
     *
     * @return the {@link ScissorsStack} instance itself.
     */
    ScissorsStack pop();
    
    /**
     * Pops the last scissor bounds without applying the changes,
     * please use {@link #applyStack()} to apply the changes.
     *
     * @param layers the number of layers to pop
     * @return the {@link ScissorsStack} instance itself.
     */
    default ScissorsStack pop(int layers) {
        for (int i = 0; i < layers; i++) {
            pop();
        }
        return this;
    }
    
    /**
     * @return the current scissors stack, please refrain yourself from editing the list here.
     */
    List<Rectangle> getCurrentStack();
    
    /**
     * Applies the stack of scissors.
     *
     * @return the {@link ScissorsStack} instance itself.
     */
    ScissorsStack applyStack();
    
    /**
     * Pops all scissor bounds without applying the changes,
     * please use {@link #applyStack()} to apply the changes.
     *
     * @return the {@link ScissorsStack} instance itself.
     */
    default ScissorsStack popAll() {
        return pop(getCurrentStack().size());
    }
}
