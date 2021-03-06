package attributizing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import entities.*;

import org.junit.jupiter.api.Test;

public class AttributizerFactoryTest {

    @Test
    public void testGetCarAttributizer() {
        Entity car = TestEntityCreator.getTestCar();
        assertTrue(AttributizerFactory.getAttributizer(car) instanceof AttributizeCarUseCase);
    }

    @Test
    public void testGetBuyerAttributizer() {
        Entity buyer = TestEntityCreator.getTestBuyer();
        assertTrue(
                AttributizerFactory.getAttributizer(buyer) instanceof AttributizeCarBuyerUseCase);
    }

    @Test
    public void testGetAddOnAttributizer() {
        Entity addOn = TestEntityCreator.getTestAddOn();
        assertTrue(AttributizerFactory.getAttributizer(addOn) instanceof AttributizeAddOnUseCase);
    }

    @Test
    public void testGetLoanDataAttributizer() {
        Entity loanData = TestEntityCreator.getTestLoanData();
        assertTrue(
                AttributizerFactory.getAttributizer(loanData)
                        instanceof AttributizeLoanDataUseCase);
    }

    @Test
    public void testGetFakeEntityAttributizer() {
        Entity faker = new FakeEntity();
        try {
            AttributizerFactory.getAttributizer(faker);
        } catch (ClassCastException e) {
            return;
        }
        fail();
    }
}
