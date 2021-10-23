package entityparsers;

import entities.AddOn;
import entitybuilder.GenerateAddOnsUseCase;
import entitybuilder.GenerateBuyerUseCase;

import javax.json.JsonObject;
import java.util.Map;

public class ParseAddOnsUseCase {
    private final JsonObject jsonObject;

    /**
     * Constructs a new ParseAddOnsUseCase to create a AddOns
     * using the given JsonObject
     * @param jsonObject
     */
    public ParseAddOnsUseCase(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Creates a map from add-on names to AddOn objects from the fields in jsonObject
     * @return
     */
//    public Map<String, AddOn> parse() {
//        GenerateAddOnsUseCase addOnsGenerator = new GenerateAddOnsUseCase();
//        JsonObject addOnsObject = jsonObject.getJsonObject("add-ons");
//
//        return addOnsGenerator.GenerateAddOnsUseCase();
//    }
}
