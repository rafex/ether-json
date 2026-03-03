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

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JacksonJsonCodec implements JsonCodec {

	private final ObjectMapper mapper;

	public JacksonJsonCodec(final ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public static JacksonJsonCodec defaultCodec() {
		final var mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return new JacksonJsonCodec(mapper);
	}

	public ObjectMapper mapper() {
		return mapper;
	}

	@Override
	public String toJson(final Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new RuntimeException("Error serializando a JSON", e);
		}
	}

	@Override
	public <T> T readValue(final InputStream input, final Class<T> type) throws IOException {
		return mapper.readValue(input, type);
	}

	@Override
	public JsonNode readTree(final String content) throws IOException {
		return mapper.readTree(content);
	}

	@Override
	public JsonNode readTree(final InputStream input) throws IOException {
		return mapper.readTree(input);
	}

}

