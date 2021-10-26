package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.LoanData;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.swing.text.html.parser.Entity;

public class PackageLoanDataUseCase {

    private final LoanData loan;

    /**
     * Constructs a new PackageLoanDataUseCase to write the data of the given loan
     * @param loan
     */
    public PackageLoanDataUseCase(LoanData loan) {
        this.loan = loan;
    }

    /**
     * Write the given LoanData to a Package using the given Packager
     * @param packager
     * @return
     * @throws Exception
     */
    public Package writeEntity(Packager packager) throws Exception {
        AttributizeLoanDataUseCase loanDataAttributizer = new AttributizeLoanDataUseCase(loan);
        AttributeMap loanMap = loanDataAttributizer.attributizeEntity();
        return packager.writePackage(loanMap);
    }
}
