<html>
<head>
    <meta name="layout" content="internal"/>
    <title>Mini Asaas</title>
</head>

<body>
<atlas-panel class="js-payments-search">
    <atlas-input type="hidden" class="js-search-url" value="${createLink(controller: 'payment', action: 'list')}"></atlas-input>

    <g:if test="${payments}">
        <atlas-toolbar>
            <atlas-button
                    icon="plus"
                    description="Adicionar cobrança"
                    href="${createLink(controller: 'payment', action: 'register')}"
                    slot="actions"
            ></atlas-button>
        </atlas-toolbar>

        <atlas-search-input
                label="Pesquisar"
                placeholder="Pesquisar por nome do pagador"
                value="${params.'payer.name[like]'}"
                class="js-search-input"
        ></atlas-search-input>

        <g:render template="/payment/templates/table" model="[payments: payments]" />
    </g:if>

    <g:else>
        <atlas-empty-state
                illustration="finance-coins"
                header="Sem cobranças cadastradas"
        >
            Aqui você pode cadastrar as cobranças a serem geradas para seus clientes.
            <atlas-button
                    icon="plus"
                    description="Adicionar cobrança"
                    href="${createLink(controller: 'payment', action: 'register')}"
                    slot="button"
            ></atlas-button>
        </atlas-empty-state>
    </g:else>

    <g:render template="/templates/pagination" model="[totalCount: totalCount, max: max]" />
</atlas-panel>

<asset:javascript src="payment/payments-list.js"/>
</body>
</html>