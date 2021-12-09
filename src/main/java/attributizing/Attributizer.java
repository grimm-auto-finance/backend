// layer: usecases
package attributizing;

import attributes.AttributeMap;

/** A use case to convert an Entity into an AttributeMap representation */
public interface Attributizer {

    /** Convert the given Entity into an AttributeMap.
     *
     * @return an AttributeMap where the keys are the Entity's instance attributes, and the values are the values of those instance attributes.
     */
    AttributeMap attributizeEntity();
}
