package entities;

import constants.EntityStringNames;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddOnTest {

  static AddOn addOn;

  @BeforeEach
  public void setup() {
    addOn = new AddOn("Rust-proofing", 30000, "no rust allowed!");
  }

  @Test
  public void testGetPrice() {
    assertEquals(30000, addOn.getPrice());
  }

  @Test
  public void testGetName() {
    assertEquals("Rust-proofing", addOn.getName());
  }

  @Test
  public void testGetDescription() {
    assertEquals("no rust allowed!", addOn.getDescription());
  }

  @Test
  public void testSetPrice() {
    addOn.setPrice(5.59);
    assertEquals(5.59, addOn.getPrice());
  }

  @Test
  public void testSetDescription() {
    addOn.setDescription("sweet yummy goodness");
    assertEquals("sweet yummy goodness", addOn.getDescription());
  }

  @Test
  public void testGetStringName() {
    assertEquals(EntityStringNames.ADD_ON_STRING, addOn.getStringName());
  }
}
