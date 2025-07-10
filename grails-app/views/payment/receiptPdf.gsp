<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <style>
    body { font-family: Arial, sans-serif; margin: 40px; }
    h2 { text-align: center; }
    .info-row { margin-bottom: 15px; }
    .label { font-weight: bold; display: inline-block; width: 200px; }
    </style>
</head>
<body>
<h2>Recibo de Pagamento</h2>

<div class="info-row"><span class="label">ID da Cobrança:</span> ${payment.id}</div>
<div class="info-row"><span class="label">Pagador:</span> ${payment.payer.name}</div>
<div class="info-row"><span class="label">Tipo:</span> ${payment.billingType}</div>
<div class="info-row"><span class="label">Valor:</span> R$ ${g.formatNumber(number: payment.value, format: "###,##0.00")}</div>
<div class="info-row"><span class="label">Data de Vencimento:</span> ${g.formatDate(date: payment.dueDate, format: "dd/MM/yyyy")}</div>
<div class="info-row"><span class="label">Status:</span> ${payment.status}</div>
<div class="info-row"><span class="label">Data da Confirmação:</span> ${g.formatDate(date: payment.lastUpdated, format: "dd/MM/yyyy HH:mm")}</div>
</body>
</html>