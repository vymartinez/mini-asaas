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

        <atlas-filter>
            <atlas-filter-form
                    slot="simple-filter"
                    data-base-url="${createLink(controller: 'payment', action: 'list')}">

                <atlas-filter-group name="payerId">
                    <atlas-select placeholder="Selecione um pagador" id="payer-options-container" name="payerId" label="Pagador" required data-selected-id="${params.payerId ?: payment?.payer?.id}">
                        <atlas-option value="">Carregando...</atlas-option>
                    </atlas-select>
                </atlas-filter-group>

            </atlas-filter-form>
        </atlas-filter>

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

<asset:javascript src="payments-list.js"/>
</body>
</html>