package payment;

import java.math.BigDecimal;

public class Payment {

    private boolean isPaid;
    private BigDecimal value;

    public Payment(BigDecimal value) {
        isPaid = false;
        this.value = value;
    }

    public boolean pay(BigDecimal paidValue) {
        boolean isBankTransferSuccessful = makeBankTransfer(paidValue);
        if (isBankTransferSuccessful) {
            isPaid = true;
        }
        return isBankTransferSuccessful;
    }

    private boolean makeBankTransfer(BigDecimal paidValue) {
        return paidValue.equals(value);
    }
}
