package entityparsers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;
import entities.AddOn;
import entitybuilder.GenerateAddOnsUseCase;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class ParseAddOnsUseCase {
    private final AttributeMap map;

    /**
     * Constructs a new ParseAddOnsUseCase to create a list of AddOns using the given Parser
     *
     * @param parser the Parser to use to create the AttributeMap for these AddOns
     */
    public ParseAddOnsUseCase(Parser parser) throws Exceptions.ParseException {
        this.map = parser.parse();
    }

    /**
     * Creates a List of AddOns parsed from the AttributeMap returned by parser
     * @return a List of AddOns
     * @throws Exceptions.ParseException if the appropriate values aren't present in the AttributeMap or have the wrong types
     */
    public List<AddOn> parse() throws Exceptions.ParseException {
        GenerateAddOnsUseCase addOnGenerator = new GenerateAddOnsUseCase();
        List<AddOn> addOns = new ArrayList<>();
        try {
            ArrayAttribute addOnArrayAtt = (ArrayAttribute) map.getItem(EntityStringNames.ADD_ON_STRING);
            Attribute[] addOnArray = addOnArrayAtt.getAttribute();
            for (Attribute attribute : addOnArray) {
                AttributeMap addOnMap = (AttributeMap) attribute;
                String name = (String) addOnMap.getItem(EntityStringNames.ADD_ON_NAME).getAttribute();
                String description = (String) addOnMap.getItem(EntityStringNames.ADD_ON_DESCRIPTION).getAttribute();
                double price = (double) addOnMap.getItem(EntityStringNames.ADD_ON_PRICE).getAttribute();
                addOns.add(addOnGenerator.generateAddOn(name, price, description));
            }
        } catch (ClassCastException | NullPointerException e) {
            Exceptions.ParseException ex = new Exceptions.ParseException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }

        return addOns;
    }
}
