package com.example.virtualtravelback.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.cloud.firestore.GeoPoint;

import java.io.IOException;

public class GeoPointDeserializer extends JsonDeserializer<GeoPoint> {
    @Override
    public GeoPoint deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final ObjectCodec codec = jsonParser.getCodec();
        final JsonNode node = codec.readTree(jsonParser);
        final double latitude = node.get("lat").doubleValue();
        final double longitude = node.get("lon").doubleValue();
        return new GeoPoint(latitude, longitude);
    }
}
