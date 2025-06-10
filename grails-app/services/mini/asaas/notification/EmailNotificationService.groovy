package mini.asaas.notification

import mini.asaas.payment.Payment

import grails.gorm.transactions.Transactional

@Transactional
class EmailNotificationService {

    def mailService

    public void notifyCreated(Payment payment) {
        send(
                to: payment.payer.email,
                subject: "Cobrança criada: ${payment.id}",
                body: "Uma nova cobrança de R\$${payment.value} para ${payment.payer.name} foi criada e está pendente."
        )
    }

    public void notifyPaid(Payment payment) {
        send(
                to: payment.payer.email,
                subject: "Pagamento recebido: #${payment.id}",
                body: "O pagamento da cobrança de R\$${payment.value} para ${payment.payer.name} foi confirmado com sucesso."
        )
    }

    public void notifyExpired(Payment payment) {
        send(
                to: payment.payer.email,
                subject: "Cobrança vencida: #${payment.id}",
                body: "A cobrança de R\$${payment.value} para ${payment.payer.name} venceu em ${payment.dueDate}."
        )
    }

    public void notifyDeleted(Payment payment) {
        send(
                to: payment.payer.email,
                subject: "Cobrança excluída: #${payment.id}",
                body: "A cobrança de R\$${payment.value} para ${payment.payer.name} foi removida do sistema."
        )
    }

    private void send(Map args) {
        mailService.sendMail {
            to args.to
            subject args.subject
            body args.body
        }
    }
}