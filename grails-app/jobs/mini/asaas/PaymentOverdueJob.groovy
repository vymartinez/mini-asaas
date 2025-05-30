package mini.asaas

class PaymentOverdueJob {

    def paymentService

    static triggers = {
        cron name: 'cronTrigger', startDelay: 0, cronExpression: '0 0 12 ? * *'
    }

    static void execute() {
        try {
            // paymentService.overduePaymentsIfNeeeded()
        } catch (Exception e) {
            throw new RuntimeException(e)
        }
    }
}