<atlas-table has-actions>
    <atlas-table-header slot="header">
        <atlas-table-col>
            Nome
        </atlas-table-col>
        <atlas-table-col>
            E-mail
        </atlas-table-col>
    </atlas-table-header>
    <atlas-table-body slot="body">
        <g:each var="payer" in="${ payers }">
            <atlas-table-row href="${createLink(controller: "payer", action: "show", id: payer.id)}">
                <atlas-table-col>
                    ${payer.name}
                </atlas-table-col>
                <atlas-table-col>
                    ${payer.email}
                </atlas-table-col>

                <atlas-button-group slot="actions" group-all>
                    <atlas-icon-button
                        icon="pencil"
                        theme="primary"
                        description="Editar pagador"
                        href="${createLink(controller: "payer", action: "show", id: payer.id)}"
                    >
                    </atlas-icon-button>
                    <atlas-icon-button
                        icon="trash"
                        theme="danger"
                        description="Desativar pagador"
                        href="${createLink(controller: "payer", action: "delete")}"
                    ></atlas-icon-button>
                </atlas-button-group>
            </atlas-table-row>
        </g:each>
    </atlas-table-body>
</atlas-table>