package attributes;

public abstract class Attribute {

  /**
   * Return the item stored in this Attribute.
   *
   * @return
   */
  public abstract Object getAttribute();

  /**
   * Returns a string representation of this Attribute.
   *
   * @return
   */
  public String toString() {
    return getAttribute().toString();
  }
}
