package mini.asaas.enums

enum BillingType {
    CARD,
    BOLETO,
    PIX

    static BillingType convert(String billingType) {
        try {
            if (billingType instanceof String) billingType = billingType.toUpperCase()
            return billingType as BillingType
        } catch(Exception e) {
            return null
        }
    }
}
