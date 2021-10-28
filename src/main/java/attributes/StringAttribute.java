package attributes;

public class StringAttribute extends Attribute {

  // the String item stored in this StringAttribute
  private final String item;

  /**
   * Constructs a new StringAttribute with the given String item
   *
   * @param item
   */
  public StringAttribute(String item) {
    this.item = item;
  }

  /**
   * Returns the String item stored in this StringAttribute
   *
   * @return
   */
  public String getAttribute() {
    return item;
  }
}
