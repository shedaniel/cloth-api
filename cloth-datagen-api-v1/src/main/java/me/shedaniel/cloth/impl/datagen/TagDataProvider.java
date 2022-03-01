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

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.shedaniel.cloth.api.datagen.v1.DataGeneratorHandler;
import me.shedaniel.cloth.api.datagen.v1.TagData;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public class TagDataProvider implements DataProvider, TagData {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGeneratorHandler handler;
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final Table<RegistryKey<? extends Registry<?>>, Identifier, Builder<?>> tagBuilders = HashBasedTable.create();
    
    public TagDataProvider(DataGeneratorHandler handler) {
        this.handler = handler;
        this.handler.install(this);
    }
    
    @Override
    public <T> TagBuilder<T> get(RegistryKey<? extends Registry<T>> registryKey, Identifier tag) {
        Builder<T> builder = (Builder<T>) tagBuilders.get(registryKey, tag);
        if (builder != null)
            return builder;
        tagBuilders.put(registryKey, tag, builder = new Builder<>(registryKey, tag));
        return builder;
    }
    
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void run(DataCache cache) throws IOException {
        Map<Identifier, LootTable> map = Maps.newHashMap();
        
        tagBuilders.rowMap().forEach((tagType, tagMap) -> tagMap.forEach((identifier, builder) -> {
            JsonObject jsonObject = builder.toJson();
            Path path = this.getOutput(tagType, identifier);
            
            try {
                String string = GSON.toJson(jsonObject);
                String string2 = SHA1.hashUnencodedChars(string).toString();
                if (!Objects.equals(cache.getOldSha1(path), string2) || !Files.exists(path)) {
                    Files.createDirectories(path.getParent());
                    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                        bufferedWriter.write(string);
                    }
                }
                
                cache.updateSha1(path, string2);
            } catch (IOException var24) {
                LOGGER.error("Couldn't save tags to {}", path, var24);
            }
        }));
    }
    
    private Path getOutput(RegistryKey<? extends Registry<?>> registryKey, Identifier identifier) {
        return this.handler.getOutput().resolve("data/" + identifier.getNamespace() + "/" + TagManagerLoader.getPath(registryKey) + "/" + identifier.getPath() + ".json");
    }
    
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
    
    private static class Builder<T> extends Tag.Builder implements TagBuilder<T> {
        private final RegistryKey<? extends Registry<T>> registryKey;
        private final Identifier identifier;
        
        public Builder(RegistryKey<? extends Registry<T>> registryKey, Identifier identifier) {
            this.registryKey = registryKey;
            this.identifier = identifier;
        }
        
        @Override
        public Identifier getId() {
            return identifier;
        }
        
        @Override
        public TagBuilder<T> append(boolean optional, Identifier value) {
            if (!optional) {
                add(value, "Datagen");
            } else {
                addOptional(value, "Datagen");
            }
            return this;
        }
        
        @Override
        public TagBuilder<T> append(boolean optional, T value) {
            Identifier id = ((Registry<Registry<T>>) Registry.REGISTRIES).get((RegistryKey<Registry<T>>) registryKey).getId(value);
            Objects.requireNonNull(id, "Couldn't find ID for " + value);
            return append(optional, id);
        }
        
        @Override
        public TagBuilder<T> appendTag(boolean optional, Identifier tag) {
            if (!optional) {
                addTag(tag, "Datagen");
            } else {
                addOptionalTag(tag, "Datagen");
            }
            return this;
        }
    }
}
