package entitypackagers;

import entities.*;

public class AttributizerFactory {

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
