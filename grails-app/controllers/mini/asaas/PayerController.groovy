package mini.asaas

import mini.asaas.dtos.PayerDTO

class PayerController {

    def payerService

    def save() {
        try {
            PayerDTO payerDto = new PayerDTO(params)
            Payer payer = payerService.save(payerDto)
            if (!payer) {
                render "Não foi possível salvar o pagador"
                return
            }
            redirect(action: "show", id: payer.id)
        } catch (Exception e) {
            redirect(action: "index", params: params)
        }
    }
}