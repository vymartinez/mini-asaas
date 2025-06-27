<html>
<head>
    <meta name="layout" content="internal"/>
    <title>Mini Asaas</title>
</head>

<body>
<atlas-panel>
    <g:if test="${ payers }">
        <atlas-toolbar>
            <atlas-button
                icon="plus"
                description="Adicionar pagador"
                href="${createLink(controller: "payer", action: "create")}"
                slot="actions"
            ></atlas-button>
        </atlas-toolbar>
        <atlas-search-input label="Pesquisar" placeholder="Pesquisar por nome ou e-mail"></atlas-search-input>
        <g:render template="/payer/templates/table" model="${[payers: payers]}"/>
    </g:if>
    <g:else>
        <atlas-empty-state
            illustration="schedule-user-avatar"
            header="Sem pagadores cadastrados"
        >
            Aqui você pode cadastrar os pagadores que deseja utilizar em suas transações.
            <atlas-button
                icon="plus"
                description="Adicionar pagador"
                href="${createLink(controller: "payer", action: "create")}"
                slot="button"
            ></atlas-button>
        </atlas-empty-state>
    </g:else>
</atlas-panel>
</body>
</html>