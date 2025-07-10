<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="internal"/>

<html>
<head>
    <meta name="layout" content="internal">
    <title>Mini Asaas</title>
</head>
<body>
<atlas-panel class="js-payment-show">
    <atlas-heading>Detalhes do Pagamento</atlas-heading>

    <atlas-form action="${createLink(controller: 'payment', action: 'update')}" method="post" class="js-payment-show-form">
        <atlas-input name="id" hidden value="${payment.id}"></atlas-input>
        <atlas-button slot="actions" description="Editar" data-panel-start-editing></atlas-button>

        <atlas-grid>
            <atlas-row>

                <atlas-col lg="6">
                    <atlas-select placeholder="Selecione um pagador" id="payer-options-container" name="payerId" label="Pagador" required data-selected-id="${params.payerId ?: payment?.payer?.id}">
                        <atlas-option value="">Carregando...</atlas-option>
                    </atlas-select>

                </atlas-col>

            </atlas-col>

                <atlas-col lg="6">
                    <atlas-select placeholder="Selecione" name="billingType" id="billingType" label="Tipo de Cobrança" required>
                        <atlas-option label="PIX" value="PIX" ${((params.billingType ?: payment.billingType.toString()) == 'PIX') ? 'selected' : ''}></atlas-option>
                        <atlas-option label="Boleto" value="BANK_SLIP" ${((params.billingType ?: payment.billingType.toString()) == 'BANK_SLIP') ? 'selected' : ''}></atlas-option>
                        <atlas-option label="Cartão de Crédito" value="CREDIT_CARD" ${((params.billingType ?: payment.billingType.toString()) == 'CREDIT_CARD') ? 'selected' : ''}></atlas-option>
                    </atlas-select>
                </atlas-col>

                <atlas-col lg="6">
                    <atlas-input
                            label="Valor"
                            name="value"
                            type="number"
                            step="0.01"
                            required
                            value="${params.value ?: payment.value}"
                    />
                </atlas-col>

                <atlas-col lg="6">
                    <atlas-input
                            label="Data de vencimento"
                            name="dueDate"
                            type="date"
                            required
                            value="${params.dueDate ?: g.formatDate(date: payment.dueDate, format: 'yyyy-MM-dd')}"
                    />
                </atlas-col>

                <atlas-col lg="6">
                    <atlas-select placeholder="Selecione" name="status" id="status" label="Status" required>
                        <atlas-option label="Pendente" value="PENDING"
                            ${((params.status ?: payment.status?.toString()) == 'PENDING') ? 'selected' : ''}>
                        </atlas-option>
                        <atlas-option label="Recebido" value="RECEIVED"
                            ${((params.status ?: payment.status?.toString()) == 'RECEIVED') ? 'selected' : ''}>
                        </atlas-option>
                        <atlas-option label="Vencido" value="OVERDUE"
                            ${((params.status ?: payment.status?.toString()) == 'OVERDUE') ? 'selected' : ''}>
                        </atlas-option>
                        <atlas-option label="Cancelado" value="CANCELED"
                            ${((params.status ?: payment.status?.toString()) == 'CANCELED') ? 'selected' : ''}>
                        </atlas-option>
                    </atlas-select>
                </atlas-col>


            </atlas-row>
        </atlas-grid>

        <atlas-button class="js-submit-button" description="Atualizar dados do pagamento" submit block>
            Atualizar
        </atlas-button>
    </atlas-form>
</atlas-panel>

<asset:javascript src="payment-show.js"/>
</body>
</html>