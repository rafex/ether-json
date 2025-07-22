package dev.rafex.ether.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {
        // Utility class
    }

    /** Serializa un objeto Java a su representación JSON en String. */
    public static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando a JSON", e);
        }
    }

    /** Deserializa un JSON (String) a un POJO de tipo T. */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializando JSON", e);
        }
    }

    /** Deserializa un JSON (String) a una lista de POJOs de tipo T. */
    public static <T> List<T> fromJsonToList(String json, Class<T> elementClass) {
        try {
            CollectionType listType = MAPPER.getTypeFactory()
                .constructCollectionType(List.class, elementClass);
            return MAPPER.readValue(json, listType);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializando JSON a List", e);
        }
    }

    /** Parsea un String JSON a un árbol de nodos Jackson. */
    public static JsonNode parseTree(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parseando JSON a JsonNode", e);
        }
    }

    /** Lee un fichero JSON y lo parsea a JsonNode. */
    public static JsonNode readTreeFromFile(Path path) {
        try {
            return MAPPER.readTree(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo JSON desde fichero", e);
        }
    }

    /** Convierte un JsonNode a un POJO de tipo T. */
    public static <T> T treeToValue(JsonNode node, Class<T> clazz) {
        try {
            return MAPPER.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error convirtiendo JsonNode a POJO", e);
        }
    }
}