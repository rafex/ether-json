package dev.rafex.ether.json;

/*-
 * #%L
 * ether-json
 * %%
 * Copyright (C) 2025 Raúl Eduardo González Argote
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public final class JsonUtils {

    private static final JacksonJsonCodec DEFAULT_CODEC = JacksonJsonCodec.defaultCodec();

    private JsonUtils() {
        // Utility class
    }

    /** Serializa un objeto Java a su representación JSON en String. */
    public static String toJson(Object value) {
        return DEFAULT_CODEC.toJson(value);
    }

    /** Deserializa un JSON (String) a un POJO de tipo T. */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return DEFAULT_CODEC.mapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializando JSON", e);
        }
    }

    /** Deserializa un JSON (String) a una lista de POJOs de tipo T. */
    public static <T> List<T> fromJsonToList(String json, Class<T> elementClass) {
        try {
            CollectionType listType = DEFAULT_CODEC.mapper().getTypeFactory()
                .constructCollectionType(List.class, elementClass);
            return DEFAULT_CODEC.mapper().readValue(json, listType);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializando JSON a List", e);
        }
    }

    /** Parsea un String JSON a un árbol de nodos Jackson. */
    public static JsonNode parseTree(String json) {
        try {
            return DEFAULT_CODEC.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("Error parseando JSON a JsonNode", e);
        }
    }

    /** Parsea un stream JSON a un árbol de nodos Jackson. */
    public static JsonNode parseTree(InputStream input) {
        try {
            return DEFAULT_CODEC.readTree(input);
        } catch (IOException e) {
            throw new RuntimeException("Error parseando stream JSON a JsonNode", e);
        }
    }

    /** Lee un fichero JSON y lo parsea a JsonNode. */
    public static JsonNode readTreeFromFile(Path path) {
        try {
            return DEFAULT_CODEC.mapper().readTree(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo JSON desde fichero", e);
        }
    }

    /** Convierte un JsonNode a un POJO de tipo T. */
    public static <T> T treeToValue(JsonNode node, Class<T> clazz) {
        try {
            return DEFAULT_CODEC.mapper().treeToValue(node, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error convirtiendo JsonNode a POJO", e);
        }
    }

    /** Deserializa un JSON desde InputStream a un POJO de tipo T. */
    public static <T> T fromJson(InputStream input, Class<T> clazz) {
        try {
            return DEFAULT_CODEC.readValue(input, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializando stream JSON", e);
        }
    }

    /** Expone el codec por defecto para reutilización en otros módulos Ether. */
    public static JsonCodec codec() {
        return DEFAULT_CODEC;
    }
}
