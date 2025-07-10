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
        try {
            log.info "OverduePaymentsJob.execute >>> Iniciando OverduePaymentsJob em ${new Date()}"
            paymentService.notifyOverduePayments()
            log.info "OverduePaymentsJob.execute >>> Concluído com sucesso"
        } catch (Exception exception) {
            log.error "OverduePaymentsJob.execute >>> Erro ao executar OverduePaymentsJob", exception
        }
    }
}