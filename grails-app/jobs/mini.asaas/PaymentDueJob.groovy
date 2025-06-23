package mini.asaas

import mini.asaas.payment.PaymentService

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class PaymentDueJob {
    public static triggers = {
        cron name: 'dueTrigger', cronExpression: '0 0 0 * * ?'
    }

    PaymentService paymentService

    def execute() {
        paymentService.processDuePayments()
    }
}
