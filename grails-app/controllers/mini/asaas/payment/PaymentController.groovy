package mini.asaas.payment

class PaymentController {
    PaymentService paymentService

    def index() {
        respond paymentService.list()
    }

    def show(Long id) {
        respond paymentService.get(id)
    }

    def create() {
        respond new Payment()
    }

    def save() {
        try {
            paymentService.create(params)
            flash.message = "Cobrança criada com sucesso."
            redirect action: 'index'
        } catch (Exception e) {
            flash.message = "Erro ao criar cobrança: ${e.message}"
            redirect action: 'create'
        }
    }

    def edit(Long id) {
        respond paymentService.get(id)
    }

    def update(Long id) {
        try {
            paymentService.update(id, params)
            flash.message = "Cobrança atualizada com sucesso."
            redirect action: 'show', id: id
        } catch (Exception e) {
            flash.message = "Erro ao atualizar cobrança: ${e.message}"
            redirect action: 'edit', id: id
        }
    }

    def delete(Long id) {
        try {
            paymentService.softDelete(id)
            flash.message = "Cobrança removida com sucesso."
        } catch (Exception e) {
            flash.message = "Erro ao remover cobrança: ${e.message}"
        }

        redirect action: 'index'
    }

    def restore(Long id) {
        try {
            paymentService.restore(id)
            flash.message = "Cobrança restaurada com sucesso."
        } catch (Exception e) {
            flash.message = "Erro ao restaurar cobrança: ${e.message}"
        }

        redirect action: 'show', id: id
    }

    def confirm(Long id) {
        try {
            paymentService.confirm(id)
            flash.message = "Pagamento confirmado com sucesso."
        } catch (Exception e) {
            flash.message = "Erro ao confirmar pagamento: ${e.message}"
        }

        redirect action: 'show', id: id
    }
}
