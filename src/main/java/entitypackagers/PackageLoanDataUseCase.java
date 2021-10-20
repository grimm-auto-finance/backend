package entitypackagers;

import entities.LoanData;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class PackageLoanDataUseCase {
    private final JsonObjectBuilder completeJsonBuilder;
    private final JsonObjectBuilder thisJsonBuilder;

    /**
     * Constructs a new PackageLoanDataUseCase that writes
     * LoanData information to the given JsonObjectBuilder
     * @param jsonBuilder the JsonObjectBuilder to write LoanData information to
     */
    public PackageLoanDataUseCase(JsonObjectBuilder jsonBuilder) {
        this.completeJsonBuilder = jsonBuilder;
        this.thisJsonBuilder = Json.createObjectBuilder();
    }

    /**
     * Write the given LoanData's data to completeJsonBuilder
     * @param loanData the LoanData to serialize
     */
    public void writeEntity(LoanData loanData) {
        thisJsonBuilder.add("interest rate", loanData.getInterestRate());
        thisJsonBuilder.add("installment", loanData.getInstallment());
        thisJsonBuilder.add("senso score", loanData.getSensoScore());
        thisJsonBuilder.add("loan amount", loanData.getLoanAmount());
        thisJsonBuilder.add("term length", loanData.getTermLength());
        thisJsonBuilder.add("interest sum", loanData.getInterestSum());

        completeJsonBuilder.add("loan data", thisJsonBuilder);
    }
}
