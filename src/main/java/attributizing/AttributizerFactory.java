// layer: usecases
package attributizing;

import entities.*;

/** A factory that returns the appropriate Attributizer for a given Entity */
public class AttributizerFactory {

    /**
     * Returns the appropraite Attributizer for entity
     *
     * @param entity The Entity from which the Attributizers is created
     * @return an Attributizer capable of Attributizing entity
     * @throws ClassCastException if the type of Entity does not have a defined Attributizer
     */
    public static Attributizer getAttributizer(Entity entity) {
        if (entity instanceof Car) {
            return new AttributizeCarUseCase((Car) entity);
        } else if (entity instanceof CarBuyer) {
            return new AttributizeCarBuyerUseCase((CarBuyer) entity);
        } else if (entity instanceof AddOn) {
            return new AttributizeAddOnUseCase((AddOn) entity);
        } else if (entity instanceof LoanData) {
            return new AttributizeLoanDataUseCase((LoanData) entity);
        } else {
            throw new ClassCastException(
                    "No appropriate Attributizer for entity " + entity.getStringName());
        }
    }
}
