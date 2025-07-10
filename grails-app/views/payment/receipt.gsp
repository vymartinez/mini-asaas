<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="internal"/>
    <title>Comprovante de Pagamento</title>
</head>
<body>
<atlas-panel>
    <atlas-heading muted="true">Comprovante de Pagamento</atlas-heading>

    <atlas-grid container="true">
        <atlas-row>
            <atlas-col lg="6">
                <strong>ID da Cobrança:</strong> ${payment.id}
            </atlas-col>
            <atlas-col lg="6">
                <strong>Pagador:</strong> ${payment.payer.name}
            </atlas-col>
        </atlas-row>

        <atlas-row>
            <atlas-col lg="6">
                <strong>Tipo:</strong> ${payment.billingType}
            </atlas-col>
            <atlas-col lg="6">
                <strong>Valor:</strong> R$ ${g.formatNumber(number: payment.value, format: "###,##0.00")}
            </atlas-col>
        </atlas-row>

        <atlas-row>
            <atlas-col lg="6">
                <strong>Data de Vencimento:</strong> ${g.formatDate(date: payment.dueDate, format: "dd/MM/yyyy")}
            </atlas-col>
            <atlas-col lg="6">
                <strong>Status:</strong> ${payment.status}
            </atlas-col>
        </atlas-row>

        <atlas-row>
            <atlas-col>
                <strong>Data da Confirmação:</strong> ${g.formatDate(date: payment.lastUpdated, format: "dd/MM/yyyy HH:mm")}
            </atlas-col>
        </atlas-row>
    </atlas-grid>

    <atlas-button href="/payment/list" icon="arrow-left">Voltar</atlas-button>
</atlas-panel>
</body>
</html>