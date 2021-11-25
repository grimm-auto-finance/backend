package entitypackagers;

import static org.junit.jupiter.api.Assertions.*;

import attributes.AttributeMap;
import attributes.DoubleAttribute;

import constants.EntityStringNames;

import org.junit.jupiter.api.Test;

class ExtractCarIdUseCaseTest {

    @Test
    public void testGenerateId() {
        AttributeMap map = new AttributeMap();
        map.addItem(EntityStringNames.ID_STRING, new DoubleAttribute(5));
        assertEquals(5, ExtractCarIdUseCase.extractId(map));
    }
}
