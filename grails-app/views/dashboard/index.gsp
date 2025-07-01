<!doctype html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="internal"/>
    <title>Mini Asaas</title>
</head>

<body>
    <atlas-panel header="Cobranças">
        <g:render template="/payment/templates/carousel" model="${[payments: payments]}"/>
    </atlas-panel>
    <atlas-panel header="Pagadores">
        <g:render template="/payer/templates/carousel" model="${[payers: payers]}"/>
    </atlas-panel>
</body>
</html>