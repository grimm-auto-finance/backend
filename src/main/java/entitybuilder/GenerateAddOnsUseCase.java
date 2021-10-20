package entitybuilder;

import entities.AddOn;

import java.util.HashMap;
import java.util.List;

/**
 * A class to generate a mapping of addon names
 * to AddOn objects with the coressponding name
 */
public class GenerateAddOnsUseCase {
    /**
     * Takes a list of addon names and generates a mapping
     * of addon names to their corresponding AddOn objects
     * @param ListOfAddOnNames A list containg names of Addons that we want to generate
     * @return A mapping of addon name to its corresponding AddOn object
     */
    public HashMap<String, AddOn> GenerateAddOnsUseCase(List<String> ListOfAddOnNames){
        /**
         * A mapping of the name of the addons we get from the list of addons in
         * our parameter to the AddOn objects with the corresponding name
         * which we create below
         */
        HashMap<String, AddOn> MapOfAddOns = new HashMap<String, AddOn>();
        for (String addOnName: ListOfAddOnNames){
            /**
             * TODO: Find out if we want to set the price and description of an addon as parameters to the constructor
             * because they will always begin as 0 and empty string as this information is obtained from the
             * database
             * I have set it to zero and the empty string respectively now
             * so that I dont change the entities class in this branch
             */
            MapOfAddOns.put(addOnName, new AddOn(addOnName, 0.0, ""));
        }
        return MapOfAddOns;
    }
}
