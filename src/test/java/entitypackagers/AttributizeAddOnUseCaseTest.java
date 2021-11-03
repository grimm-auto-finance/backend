package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.AddOn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributizeAddOnUseCaseTest {

    static AttributeMap testMap;
    static AddOn addOn;
    static AttributizeAddOnUseCase addOnAttributizer;

    @BeforeEach
    public void setup() {
        testMap = new AttributeMap();
    }

    static void addAddOnToTestMap() {
        testMap.addItem(EntityStringNames.ADD_ON_NAME, addOn.getName());
        testMap.addItem(EntityStringNames.ADD_ON_PRICE, addOn.getPrice());
        testMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, addOn.getDescription());
    }

    // TODO: Uncomment this test when AddOn constructor situation is sorted out
//    @Test
//    public void testAttributizeAddOnOnlyName() {
//        addOn = new AddOn("Marshmallows");
//        addOnAttributizer = new AttributizeAddOnUseCase(addOn);
//        testMap.addItem(EntityStringNames.ADD_ON_NAME, addOn.getName());
//        assertEquals(testMap.getAttribute().toString(), addOnAttributizer.attributizeEntity().getAttribute().toString());
//    }

    @Test
    public void testAttributizeAddOnCompleteAddOn() {
        addOn = new AddOn("Rust proofing", 1000, "no rust allowed!");
        addAddOnToTestMap();
        addOnAttributizer = new AttributizeAddOnUseCase(addOn);
        assertEquals(testMap.getAttribute().toString(), addOnAttributizer.attributizeEntity().getAttribute().toString());
    }
}
