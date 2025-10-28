package dev.yohannses.project_management.commons.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Currency;

public class CurrencyDeserializer extends JsonDeserializer<Currency> {

    public Currency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken token = p.getCurrentToken();

        if (token == JsonToken.VALUE_NULL) {
            return null;
        }

        if (token == JsonToken.VALUE_STRING) {
            return parseCurrency(p.getText().trim(), p);
        }

        if (token == JsonToken.START_OBJECT) {
            ObjectNode node = p.readValueAsTree();
            String code = extractCurrencyCode(node);
            return parseCurrency(code, p);
        }

        throw InvalidFormatException.from(p, "Cannot deserialize Currency from token: " + token, p.getText(), Currency.class);
    }

    private String extractCurrencyCode(ObjectNode node) {
        if (node.has("code")) return node.get("code").asText(null);
        if (node.has("currency")) return node.get("currency").asText(null);
        if (node.has("name")) return node.get("name").asText(null);
        return null;
    }

    private Currency parseCurrency(String code, JsonParser p) throws IOException {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        try {
            return Currency.getInstance(code.trim());
        } catch (IllegalArgumentException ex) {
            throw InvalidFormatException.from(p, "Invalid currency code", code, Currency.class);
        }
    }
}

