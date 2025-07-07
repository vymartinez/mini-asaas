package mini.asaas

import groovy.util.logging.Slf4j
import org.quartz.JobExecutionContext
import grails.gorm.transactions.Transactional
import mini.asaas.PaymentService

@Slf4j
class OverduePaymentsJob {

    PaymentService paymentService

    static triggers = {
        cron name: 'overduePaymentsTrigger', cronExpression: '0 0 0 * * ?'
    }

    @Transactional
    public void execute(JobExecutionContext context) {
        log.info "Iniciando OverduePaymentsJob em ${new Date()}"
        try {
            paymentService.notifyOverduePayments()
            log.info "OverduePaymentsJob concluído com sucesso"
        } catch (Exception exception) {
            log.error "Erro ao executar OverduePaymentsJob", exception
        }
    }
}