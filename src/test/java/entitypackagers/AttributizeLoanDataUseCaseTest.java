package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.LoanData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributizeLoanDataUseCaseTest {

  static AttributeMap testMap;
  static LoanData loanData;
  static AttributizeLoanDataUseCase loanDataAttributizer;

  static void addLoanDataToTestMap() {
    testMap.addItem(EntityStringNames.LOAN_AMOUNT, loanData.getLoanAmount());
    testMap.addItem(EntityStringNames.LOAN_INSTALLMENT, loanData.getInstallment());
    testMap.addItem(EntityStringNames.LOAN_SCORE, loanData.getSensoScore());
    testMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, loanData.getInterestRate());
    testMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, loanData.getInterestSum());
    testMap.addItem(EntityStringNames.LOAN_TERM_LENGTH, loanData.getTermLength());
  }

  @BeforeEach
  public void setup() {
    testMap = new AttributeMap();
  }

  @Test
  public void testAttributizeLoanData() {
    loanData = new LoanData(1.25, 500.25, "Medium", 50000, 36, 200.25);
    addLoanDataToTestMap();
    loanDataAttributizer = new AttributizeLoanDataUseCase(loanData);
    assertEquals(
        testMap.getAttribute().toString(),
        loanDataAttributizer.attributizeEntity().getAttribute().toString());
  }
}
