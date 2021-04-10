package payment;

public class Payment {

    private Boolean isPaid;
    private Double value;

    public Payment(Double value) {
        isPaid = false;
        this.value = value;
    }

    public Boolean pay(Double paidValue) {
        Boolean isBankTransferSuccessful = makeBankTransfer(paidValue);
        if (isBankTransferSuccessful) {
            isPaid = true;
        }
        return isBankTransferSuccessful;
    }

    private Boolean makeBankTransfer(Double paidValue) {
        return paidValue >= value;
    }
}
