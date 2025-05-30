package mini.asaas

import grails.gorm.transactions.Transactional
import mini.asaas.dtos.PaymentDTO

@Transactional
class PaymentService {

    Payment save(PaymentDTO paymentDto) {
        Payment payment = new Payment(
            billingType: paymentDto.billingType,
            value: paymentDto.value,
            status: paymentDto.status,
            overdueDate: paymentDto.overdueDate,
            payer: Payer.get(paymentDto.payerId)
        )

        payment.save(failOnError: true)
        return payment
    }
}
