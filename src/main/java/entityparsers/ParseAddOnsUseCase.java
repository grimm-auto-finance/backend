package entityparsers;

import attributes.AttributeMap;
import java.util.ArrayList;
import java.util.HashMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.AddOn;

import entitybuilder.GenerateAddOnsUseCase;
import entitybuilder.GenerateCarUseCase;

public class ParseAddOnsUseCase {

    private final ArrayList<AttributeMap> addonList;

    /**
     * Constructs a new ParseCarUseCase to create a Car using the given Parser
     *
     * @param parser object to be parsed
     */
    public ParseAddOnsUseCase(ArrayList<Parser> parser) throws Exceptions.ParseException {
        addonList = new ArrayList<AttributeMap>();
        for(Parser p: parser) {
            map.add(p.parse());
        }
    }

    /**
     * Creates a map from add-on names to AddOn objects from the fields in jsonObject
     *
     * @return
     */
    //    TODO: Uncomment ParseAddOnsUseCase.parse() when this is implemented
    public HashMap<String, AddOn> parse() {
        HashMap<String, AttributeMap> addOns = new HashMap<String, AttributeMap>();
        GenerateAddOnsUseCase AddOnGenerator = new GenerateAddOnsUseCase();
        for (AttributeMap a : addonList) {
            String name, description;
            double price;
            AttributeMap addOnMap;
            try {
                addOnMap = (AttributeMap) a.getItem(EntityStringNames.ADD_ON_STRING);
                name = (String) addOnMap.getItem(EntityStringNames.ADD_ON_NAME).getAttribute();
                price = (double) addOnMap.getItem(EntityStringNames.ADD_ON_PRICE).getAttribute();
                description = (String) addOnMap.getItem(EntityStringNames.ADD_ON_DESCRIPTION).getAttribute();
            } catch (ClassCastException e) {
                Exceptions.ParseException ex = new Exceptions.ParseException(e.getMessage());
                ex.setStackTrace(e.getStackTrace());
                throw ex;
            }
            addOns.put(name, addOnMap);
        }
        return AddOnGenerator.GenerateAddOnDataUseCase(addOns);
    }
}
