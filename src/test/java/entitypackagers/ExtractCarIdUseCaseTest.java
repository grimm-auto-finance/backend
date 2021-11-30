package entitypackagers;

import static org.junit.jupiter.api.Assertions.*;

import attributes.AttributeMap;
import attributes.IntAttribute;

import constants.EntityStringNames;
import constants.Exceptions;

import org.junit.jupiter.api.Test;

class ExtractCarIdUseCaseTest {

    @Test
    public void testGenerateId() {
        AttributeMap map = new AttributeMap();
        map.addItem(EntityStringNames.ID_STRING, new IntAttribute(5));
        try {
            assertEquals(5, ExtractCarIdUseCase.extractId(map));
        } catch (Exceptions.CodedException e) {
            fail();
        }
    }
}
