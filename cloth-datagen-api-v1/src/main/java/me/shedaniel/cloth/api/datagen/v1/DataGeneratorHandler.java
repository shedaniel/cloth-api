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

package me.shedaniel.cloth.api.datagen.v1;

import me.shedaniel.cloth.impl.datagen.DataGeneratorHandlerImpl;
import net.minecraft.Bootstrap;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

public interface DataGeneratorHandler extends Runnable {
    static DataGeneratorHandler create(Path output) {
        Bootstrap.initialize();
        DataGenerator generator = new DataGenerator(output.toAbsolutePath().normalize(), Collections.emptyList());
        
        return new DataGeneratorHandlerImpl(generator);
    }
    
    default void install(DataProvider dataProvider) {
        getDataGenerator().install(dataProvider);
    }
    
    default Collection<Path> getInputs() {
        return getDataGenerator().getInputs();
    }
    
    default Path getOutput() {
        return getDataGenerator().getOutput();
    }
    
    DataGenerator getDataGenerator();
    
    LootTableData getLootTables();
    
    TagData getTags();
    
    RecipeData getRecipes();
}
