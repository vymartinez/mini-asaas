package mini.asaas

import mini.asaas.dtos.PaymentDTO

class PaymentController {

    def paymentService

    def save() {
        try {
            PaymentDTO paymentDto = new PaymentDTO(params);
            Payment payment = paymentService.save(paymentDto)
            if (!payment) {
                render "Não foi possível salvar o pagamento"
                return
            }
            redirect(action: "show", params: params)
        } catch (Exception e) {
            redirect(action: "index", params: params)
        }
    }
}
