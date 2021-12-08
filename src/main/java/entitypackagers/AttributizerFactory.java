package entitypackagers;

import entities.*;

/**
 * A factory that creates an AttributizerUseCase given an Entity that is either a Car, CarBuyer or
 * AddOn
 */
public class AttributizerFactory {

    /**
     * @param entity The Entity from which the attributizerUseCase object is created
     * @return a Car, CarBuyer or AddOn Attributizer, depending on the entity in the parameter
     * @throws ClassCastException This is because an incorrect class is used for the parameter
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
            throw new ClassCastException("No appropriate Attributizer for entity");
        }
    }
}
