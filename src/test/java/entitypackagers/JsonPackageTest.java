package entitypackagers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class JsonPackageTest {

    static JsonObject obj;

    @BeforeEach
    public void setup() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("test 1", "test 1");
        builder.add("test 2", 2);
        builder.add("test 3", 3.5);
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        subBuilder.add("sub test", "sub test");
        builder.add("sub builder", subBuilder);
        obj = builder.build();
    }

    @Test
    public void testJSONPackageGetPackage() {
        JsonPackage jsonPackage = new JsonPackage(obj);
        assertEquals(obj, jsonPackage.getPackage());
    }

    @Test
    public void testJSONPackageToString() {
        Package jsonPackage = new JsonPackage(obj);
        assertEquals(obj.toString(), jsonPackage.toString());
    }
}
