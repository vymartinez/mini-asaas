<atlas-input type="hidden" class="js-payers-search-url" value="${createLink(controller: 'payer', action: 'findAll')}"></atlas-input>
<atlas-toolbar>
    <atlas-button
        icon="plus"
        description="Adicionar pagador"
        href="${createLink(controller: "payer", action: "register")}"
        slot="actions"
    ></atlas-button>
</atlas-toolbar>
<atlas-search-input block label="Pesquisar" value="${ params."nameOrEmail[like]" }" placeholder="Pesquisar por nome ou e-mail" class="js-search-input"></atlas-search-input>
<g:if test="${ payers }">
    <g:render template="/payer/templates/table" model="${[payers: payers]}"/>
</g:if>
<g:else>
<atlas-empty-state
        illustration="schedule-user-avatar"
        header="Sem pagadores cadastrados"
    >
        Aqui você pode cadastrar os pagadores que deseja utilizar em suas transações.
    </atlas-empty-state>
</g:else>
<g:render template="/templates/pagination" model="${[totalCount: totalCount, max: max]}" />