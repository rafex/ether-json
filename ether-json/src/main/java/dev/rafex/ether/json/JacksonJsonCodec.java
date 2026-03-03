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

package dev.rafex.ether.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JacksonJsonCodec implements JsonCodec {

    private final ObjectMapper mapper;

    public JacksonJsonCodec(final ObjectMapper mapper) {
        this.mapper = Objects.requireNonNull(mapper, "mapper");
    }

    public static JacksonJsonCodec defaultCodec() {
        return JsonCodecBuilder.create().build();
    }

    public ObjectMapper mapper() {
        return mapper;
    }

    public static ObjectMapper defaultMapper() {
        final var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Override
    public String toJson(final Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (final JsonProcessingException e) {
            throw new JsonCodecException("Error serializando a JSON", e);
        }
    }

    @Override
    public String toPrettyJson(final Object value) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (final JsonProcessingException e) {
            throw new JsonCodecException("Error serializando a JSON formateado", e);
        }
    }

    @Override
    public byte[] toJsonBytes(final Object value) {
        try {
            return mapper.writeValueAsBytes(value);
        } catch (final JsonProcessingException e) {
            throw new JsonCodecException("Error serializando a bytes JSON", e);
        }
    }

    @Override
    public void writeValue(final OutputStream output, final Object value) {
        try {
            mapper.writeValue(output, value);
        } catch (final IOException e) {
            throw new JsonCodecException("Error escribiendo JSON al stream", e);
        }
    }

    @Override
    public <T> T readValue(final InputStream input, final Class<T> type) {
        try {
            return mapper.readValue(input, type);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando stream JSON", e);
        }
    }

    @Override
    public <T> T readValue(final InputStream input, final TypeReference<T> typeRef) {
        try {
            return mapper.readValue(input, typeRef);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando stream JSON", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T readValue(final InputStream input, final JavaType type) {
        try {
            return (T) mapper.readValue(input, type);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando stream JSON", e);
        }
    }

    @Override
    public <T> T readValue(final String content, final Class<T> type) {
        try {
            return mapper.readValue(content, type);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando JSON", e);
        }
    }

    @Override
    public <T> T readValue(final String content, final TypeReference<T> typeRef) {
        try {
            return mapper.readValue(content, typeRef);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando JSON", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T readValue(final String content, final JavaType type) {
        try {
            return (T) mapper.readValue(content, type);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando JSON", e);
        }
    }

    @Override
    public <T> T readValue(final byte[] content, final Class<T> type) {
        try {
            return mapper.readValue(content, type);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando bytes JSON", e);
        }
    }

    @Override
    public <T> T readValue(final byte[] content, final TypeReference<T> typeRef) {
        try {
            return mapper.readValue(content, typeRef);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando bytes JSON", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T readValue(final byte[] content, final JavaType type) {
        try {
            return (T) mapper.readValue(content, type);
        } catch (final IOException e) {
            throw new JsonCodecException("Error deserializando bytes JSON", e);
        }
    }

    @Override
    public JsonNode readTree(final String content) {
        try {
            return mapper.readTree(content);
        } catch (final IOException e) {
            throw new JsonCodecException("Error parseando JSON a JsonNode", e);
        }
    }

    @Override
    public JsonNode readTree(final InputStream input) {
        try {
            return mapper.readTree(input);
        } catch (final IOException e) {
            throw new JsonCodecException("Error parseando stream JSON a JsonNode", e);
        }
    }

    @Override
    public JsonNode readTree(final byte[] input) {
        try {
            return mapper.readTree(input);
        } catch (final IOException e) {
            throw new JsonCodecException("Error parseando bytes JSON a JsonNode", e);
        }
    }

    @Override
    public JsonNode valueToTree(final Object value) {
        return mapper.valueToTree(value);
    }

    @Override
    public <T> T treeToValue(final JsonNode node, final Class<T> type) {
        try {
            return mapper.treeToValue(node, type);
        } catch (final JsonProcessingException e) {
            throw new JsonCodecException("Error convirtiendo JsonNode a POJO", e);
        }
    }

    @Override
    public <T> T treeToValue(final JsonNode node, final TypeReference<T> typeRef) {
        return mapper.convertValue(node, typeRef);
    }

    @Override
    public JsonNode at(final JsonNode node, final String pointer) {
        Objects.requireNonNull(node, "node");
        Objects.requireNonNull(pointer, "pointer");
        return node.at(pointer);
    }
}
