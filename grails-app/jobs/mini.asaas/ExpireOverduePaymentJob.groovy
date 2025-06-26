package mini.asaas

import mini.asaas.PaymentService

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ExpireOverduePaymentJob {

    PaymentService paymentService

    public static triggers = {
        cron name: 'expireOverduePaymentsTrigger', cronExpression: '0 0 0 * * ?'
    }

    def execute() {
        paymentService.expireOverduePayments()
    }
}
