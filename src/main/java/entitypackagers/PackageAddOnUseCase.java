package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.AddOn;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class PackageAddOnUseCase {
    
    private final AddOn addOn;

    /**
     * Constructs a new PackageAddOnUseCase that writes AddOn information to Packages
     * @param addOn the AddOn to be serialized
     */
    public PackageAddOnUseCase(AddOn addOn) {
        this.addOn = addOn;
    }

    /**
     * Write addOn to a Package using the given Packager
     * @param packager
     * @return
     * @throws Exception
     */
    public Package writeEntity(Packager packager) throws Exception {
        AttributizeAddOnUseCase addOnAttributizer = new AttributizeAddOnUseCase(addOn);
        AttributeMap addOnMap = addOnAttributizer.attributizeEntity();
        return packager.writePackage(addOnMap);
    }
}
