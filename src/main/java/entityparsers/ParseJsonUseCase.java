package entityparsers;

import attributes.AttributeMap;

import constants.Exceptions;

import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ParseJsonUseCase {

    public AttributeMap parseJson(InputStream is) throws Exceptions.ParseException {
        JsonObject inputObj;
        try {
            JsonReader jsonReader = Json.createReader(is);
            inputObj = jsonReader.readObject();
        } catch (JsonException e) {
            throw new Exceptions.ParseException("failed to convert inputstream to JsonObject", e);
        }
        Parser jsonParser = new JsonParser(inputObj);
        return jsonParser.parse();
    }
}
