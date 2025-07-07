<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="internal"/>
    <title>Mini Asaas – Criar Cobrança</title>
</head>
<body>
<atlas-panel class="js-create-payment">
    <atlas-layout gap="5" mobile-gap="5">
        <atlas-heading muted>Criar Cobrança</atlas-heading>

        <atlas-form action="${createLink(controller:'payment', action:'create')}"
                    method="post"
                    class="js-create-payment-form">

            <atlas-grid container>
                <atlas-row>
                    <atlas-col lg="6">
                        <atlas-select placeholder="Selecione o pagador"
                                      name="payerId"
                                      id="payerId"
                                      label="Pagador"
                                      required>
                            <g:each in="${payers}" var="p">
                                <atlas-option value="${p.id}"
                                              label="${p.name}"
                                    ${params.payerId == p.id.toString() ? 'selected' : ''}>
                                </atlas-option>
                            </g:each>
                        </atlas-select>
                    </atlas-col>

                    <atlas-col lg="6">
                        <atlas-select placeholder="Selecione"
                                      name="billingType"
                                      id="billingType"
                                      label="Tipo de Cobrança"
                                      required>
                            <atlas-option label="PIX" value="PIX" ${params.billingType=='PIX' ? 'selected':''}></atlas-option>
                            <atlas-option label="Boleto" value="BANK_SLIP" ${params.billingType=='BANK_SLIP' ? 'selected':''}></atlas-option>
                            <atlas-option label="Cartão de Crédito" value="CREDIT_CARD" ${params.billingType=='CREDIT_CARD' ? 'selected':''}></atlas-option>
                        </atlas-select>
                    </atlas-col>
                </atlas-row>

                <atlas-row>
                    <atlas-col lg="6">
                        <atlas-input type="number"
                                     name="value"
                                     id="value"
                                     label="Valor"
                                     placeholder="0.00"
                                     step="0.01"
                                     value="${params.value ?: ''}"
                                     required/>
                    </atlas-col>

                    <atlas-col lg="6">
                        <atlas-input type="date"
                                     name="dueDate"
                                     id="dueDate"
                                     label="Data de Vencimento"
                                     value="${params.dueDate ?: ''}"
                                     required/>
                    </atlas-col>
                </atlas-row>
            </atlas-grid>

            <atlas-button description="Cadastrar"
                          submit
                          block
                          class="js-submit-button">
                Cadastrar
            </atlas-button>
        </atlas-form>
    </atlas-layout>
</atlas-panel>

<asset:javascript src="payment/payment-create.js"/>
</body>
</html>