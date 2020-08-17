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

package me.shedaniel.cloth.impl.datagen;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler;
import me.shedaniel.cloth.api.datagen.v1.ItemModelData;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;

public class ItemModelDataProvider implements DataProvider, ItemModelData {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGeneratorHandler handler;
    private final Map<Identifier, Supplier<JsonElement>> models = Maps.newHashMap();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    
    public ItemModelDataProvider(DataGeneratorHandler handler) {
        this.handler = handler;
        this.handler.install(this);
    }
    
    @Override
    public void addModel(Identifier identifier, Supplier<JsonElement> element) {
        this.models.put(identifier, element);
    }
    
    @Override
    public void run(DataCache cache) throws IOException {
        Path path = this.handler.getOutput();
        models.forEach((identifier, jsonElement) -> {
            Path outputPath = getOutput(path, identifier);
            
            try {
                DataProvider.writeToPath(GSON, cache, jsonElement.get(), outputPath);
            } catch (IOException var6) {
                LOGGER.error("Couldn't save block state {}", outputPath, var6);
            }
        });
    }
    
    private static Path getOutput(Path rootOutput, Identifier id) {
        return rootOutput.resolve("assets/" + id.getNamespace() + "/models/" + id.getPath() + ".json");
    }
    
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
