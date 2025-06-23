<!doctype html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="internal"/>
    <title>Mini Asaas</title>
    <asset:stylesheet src="register.css"/>
</head>

<body>
<atlas-panel>
    <atlas-form action="/payment/save">
        <atlas-heading>Cadastro de Pagamento</atlas-heading>
        <atlas-grid>
            <atlas-row>

                <atlas-col lg="6">
                    <atlas-input label="Payer ID" id="payerId" name="payerId" type="number" placeholder="ID do Payer" required></atlas-input>
                </atlas-col>

                <atlas-col lg="6">
                    <atlas-select label="Tipo de Cobrança" id="billingType" name="billingType" placeholder="Selecione" required>
                        <atlas-option label="Cartão de Crédito" value="CREDIT_CARD"></atlas-option>
                        <atlas-option label="Boleto" value="BANK_SLIP"></atlas-option>
                        <atlas-option label="PIX" value="PIX"></atlas-option>
                    </atlas-select>
                </atlas-col>

                <atlas-col lg="6">
                    <atlas-input label="Valor" id="value" name="value" type="number" step="0.01" placeholder="Valor" required></atlas-input>
                </atlas-col>

                <atlas-col lg="6">
                    <atlas-masked-input label="Data de Vencimento" id="dueDate" name="dueDate" type="text" placeholder="YYYY-MM-DD" alias="date" required></atlas-masked-input>
                </atlas-col>

            </atlas-row>
        </atlas-grid>

        <atlas-button description="Cadastrar" submit block></atlas-button>
    </atlas-form>
</atlas-panel>
</body>
</html>