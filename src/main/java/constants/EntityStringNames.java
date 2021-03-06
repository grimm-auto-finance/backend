// layer: ignore
package constants;

/**
 * String representations of entity instance variable names These are the keys used in Attributized
 * versions of the entities, as well as the keys expected to be present in data received from the
 * frontend. Having them all as constants here ensures consistency throughout the program.
 */
public class EntityStringNames {

    /** String representation of the instance variable names for a Car */
    public static final String CAR_STRING = "car";

    public static final String CAR_MAKE = "make";
    public static final String CAR_MODEL = "model";
    public static final String CAR_IMAGE = "image";
    public static final String CAR_YEAR = "year";
    public static final String CAR_PRICE = "price";
    public static final String CAR_KILOMETRES = "kilometres";
    public static final String ID_STRING = "id";
    public static final String CAR_ID = "id";

    /** String representation of the instance variable names for a CarBuyer */
    public static final String BUYER_STRING = "carBuyer";

    public static final String BUYER_BUDGET = "budget";
    public static final String BUYER_CREDIT = "creditScore";
    public static final String BUYER_DOWNPAYMENT = "downpayment";

    /** String representation of the instance variable names for a LoanData */
    public static final String LOAN_STRING = "loan";

    public static final String LOAN_INTEREST_RATE = "interestRate";
    public static final String LOAN_INSTALLMENT = "installment";
    public static final String LOAN_SCORE = "sensoScore";
    public static final String LOAN_AMOUNT = "amount";
    public static final String LOAN_TERM_LENGTH = "term";
    public static final String LOAN_INTEREST_SUM = "interestSum";
    public static final String LOAN_AMORTIZATION = "installments";
    public static final String LOAN_ADD_ON_BUDGET = "addOnBudget";
    public static final String LOAN_LOOP_MAX = "loopMax";

    /** String representations of the instance variable names for an AddOn */
    public static final String ADD_ON_STRING = "addOns";

    public static final String ADD_ON_NAME = "name";
    public static final String ADD_ON_PRICE = "price";
    public static final String ADD_ON_DESCRIPTION = "description";
}
