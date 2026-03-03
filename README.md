# ether-json

Libreria ligera para serializacion/deserializacion JSON sobre Jackson con:

- API tipada para errores (`JsonCodecException`)
- soporte para tipos genericos (`TypeReference` y `JavaType`)
- operaciones por `String`, `byte[]`, `InputStream` y `OutputStream`
- builder configurable (`JsonCodecBuilder`) con presets strict/lenient
- utilidades de `JsonNode` (tree model y JSON Pointer)
- validacion opcional de JSON Schema (`JsonSchemaUtils`)

## Maven

```xml
<dependency>
  <groupId>dev.rafex.ether.json</groupId>
  <artifactId>ether-json</artifactId>
  <version>3.0.1-SNAPSHOT</version>
</dependency>
```

## Uso rapido

```java
import dev.rafex.ether.json.JsonUtils;

record User(String name, int age) {}

String json = JsonUtils.toJson(new User("ana", 21));
User user = JsonUtils.fromJson(json, User.class);
```

## Tipos genericos

```java
import com.fasterxml.jackson.core.type.TypeReference;

Map<String, List<Integer>> value = JsonUtils.fromJson(
    "{\"numbers\":[1,2,3]}",
    new TypeReference<Map<String, List<Integer>>>() {}
);
```

## Streams y bytes

```java
byte[] bytes = JsonUtils.toJsonBytes(Map.of("ok", true));
Map<String, Object> payload = JsonUtils.fromJson(bytes, new TypeReference<Map<String, Object>>() {});

try (OutputStream out = response.getOutputStream()) {
  JsonUtils.writeJson(out, payload);
}
```

## Builder de configuracion

```java
import dev.rafex.ether.json.JsonCodecBuilder;
import dev.rafex.ether.json.JsonCodec;

JsonCodec strict = JsonCodecBuilder.strict().build();
JsonCodec lenient = JsonCodecBuilder.lenient().build();

JsonCodec custom = JsonCodecBuilder.create()
    .prettyPrint(true)
    .failOnUnknownProperties(false)
    .build();
```

## Tree model y JSON Pointer

```java
JsonNode root = JsonUtils.parseTree("{\"profile\":{\"name\":\"rafex\"}}");
JsonNode name = JsonUtils.at(root, "/profile/name");
```

## Validacion JSON Schema

```java
String schema = """
{
  \"$schema\": \"https://json-schema.org/draft/2020-12/schema\",
  \"type\": \"object\",
  \"required\": [\"name\"],
  \"properties\": {\"name\": {\"type\": \"string\"}}
}
""";

String data = "{\"name\":\"ether\"}";
JsonSchemaUtils.validateOrThrow(schema, data); // lanza JsonSchemaValidationException si no cumple
```

## Manejo de errores

```java
try {
  User user = JsonUtils.fromJson("{", User.class);
} catch (JsonCodecException ex) {
  // error de parseo / serializacion
}
```
