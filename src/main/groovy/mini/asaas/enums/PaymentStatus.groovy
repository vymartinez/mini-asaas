package mini.asaas.enums

enum PaymentStatus {
    OVERDUE,
    RECEIVED,
    PENDING

    static PaymentStatus convert(String paymentStatus) {
        try {
            if (paymentStatus instanceof String) paymentStatus = paymentStatus.toUpperCase()
            return paymentStatus as PaymentStatus
        } catch (Exception e) {
            return null
        }
    }
}