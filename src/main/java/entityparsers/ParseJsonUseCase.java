package entityparsers;

import attributes.AttributeMap;
import constants.Exceptions;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;

public class ParseJsonUseCase {

    public AttributeMap parseJson(InputStream is) throws Exceptions.ParseException {
        JsonReader jsonReader = Json.createReader(is);
        JsonObject inputObj = jsonReader.readObject();
        Parser jsonParser = new JsonParser(inputObj);
        return jsonParser.parse();
    }
}
