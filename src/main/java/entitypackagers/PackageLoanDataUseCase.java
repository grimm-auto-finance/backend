package entitypackagers;

import constants.EntityStringNames;
import entities.LoanData;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.swing.text.html.parser.Entity;

public class PackageLoanDataUseCase {
    private final JsonObjectBuilder completeJsonBuilder;
    private final JsonObjectBuilder thisJsonBuilder;

    /**
     * Constructs a new PackageLoanDataUseCase that writes LoanData information to the given
     * JsonObjectBuilder
     *
     * @param jsonBuilder the JsonObjectBuilder to write LoanData information to
     */
    public PackageLoanDataUseCase(JsonObjectBuilder jsonBuilder) {
        this.completeJsonBuilder = jsonBuilder;
        this.thisJsonBuilder = Json.createObjectBuilder();
    }

    /**
     * Write the given LoanData's data to completeJsonBuilder
     *
     * @param loanData the LoanData to serialize
     */
    public void writeEntity(LoanData loanData) {
        thisJsonBuilder.add(EntityStringNames.LOAN_INTEREST_RATE, loanData.getInterestRate());
        thisJsonBuilder.add(EntityStringNames.LOAN_INSTALLMENT, loanData.getInstallment());
        thisJsonBuilder.add(EntityStringNames.LOAN_SCORE, loanData.getSensoScore());
        thisJsonBuilder.add(EntityStringNames.LOAN_AMOUNT, loanData.getLoanAmount());
        thisJsonBuilder.add(EntityStringNames.LOAN_TERM_LENGTH, loanData.getTermLength());
        thisJsonBuilder.add(EntityStringNames.LOAN_INTEREST_SUM, loanData.getInterestSum());

        completeJsonBuilder.add(EntityStringNames.LOAN_STRING, thisJsonBuilder);
    }
}
