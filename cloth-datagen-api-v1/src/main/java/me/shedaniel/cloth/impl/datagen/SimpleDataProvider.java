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
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler;
import me.shedaniel.cloth.api.datagen.v1.SimpleData;
import me.shedaniel.cloth.api.datagen.v1.WorldGenData;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class SimpleDataProvider implements DataProvider, SimpleData, WorldGenData {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGeneratorHandler handler;
    private final Map<String, Supplier<String>> recipes = Maps.newHashMap();
    
    public SimpleDataProvider(DataGeneratorHandler handler) {
        this.handler = handler;
        this.handler.install(this);
    }
    
    @Override
    public void add(String filePath, Supplier<String> content) {
        recipes.put(filePath, content);
    }
    
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void run(DataCache cache) throws IOException {
        Path output = this.handler.getOutput();
        recipes.forEach((path, stringSupplier) -> {
            try {
                Path filePath = output.resolve(path);
                String string = stringSupplier.get();
                String string2 = SHA1.hashUnencodedChars(string).toString();
                if (!Objects.equals(cache.getOldSha1(filePath), string2) || !Files.exists(filePath)) {
                    Files.createDirectories(filePath.getParent());
                    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath)) {
                        bufferedWriter.write(string);
                    }
                }
                
                cache.updateSha1(filePath, string2);
            } catch (IOException var24) {
                LOGGER.error("Couldn't save file to {}", path, var24);
            }
        });
    }
    
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
