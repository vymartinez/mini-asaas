package mini.asaas

import grails.compiler.GrailsCompileStatic
import mini.asaas.payment.PaymentService

@GrailsCompileStatic
class ExpireOverduePaymentJob {

    PaymentService paymentService

    public static triggers = {
        cron name: 'expireOverduePaymentsTrigger', cronExpression: '0 0 00 * * ?'
    }

    def execute() {
        paymentService.expireOverduePayments()
    }
}
