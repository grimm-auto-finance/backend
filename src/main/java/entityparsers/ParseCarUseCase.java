package entityparsers;

import entities.Car;
import entitybuilder.GenerateBuyerUseCase;
import entitybuilder.GenerateCarUseCase;

import javax.json.JsonObject;

public class ParseCarUseCase {
    private final JsonObject jsonObject;

    /**
     * Constructs a new ParseCarUseCase to create a Car
     * using the given JsonObject
     * @param jsonObject
     */
    public ParseCarUseCase(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Creates a Car object from the fields in jsonObject
     * @return
     */
    public Car parse() throws ClassCastException {
        GenerateCarUseCase carGenerator = new GenerateCarUseCase();
        JsonObject carObj = jsonObject.getJsonObject("car");
        String make = jsonObject.getString("make");
        String model = jsonObject.getString("model");
        int year = jsonObject.getInt("year");
        return carGenerator.GenerateCarUseCase(make, model, year);
    }
}