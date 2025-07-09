<atlas-table has-actions>
    <atlas-table-header slot="header">
        <atlas-table-col>Pagador</atlas-table-col>
        <atlas-table-col>Tipo</atlas-table-col>
        <atlas-table-col>Valor</atlas-table-col>
        <atlas-table-col>Vencimento</atlas-table-col>
        <atlas-table-col>Status</atlas-table-col>
    </atlas-table-header>
    <atlas-table-body slot="body">
        <g:each var="payment" in="${payments}">
            <atlas-table-row theme="${ !payment.deleted ? "primary" : "danger" }" href="${createLink(controller:'payment', action:'show', id:payment.id)}">
                <atlas-table-col>${payment.payer?.name}</atlas-table-col>
                <atlas-table-col>${payment.billingType}</atlas-table-col>
                <atlas-table-col>
                    ${g.formatNumber(number: payment.value, format: '###,##0.00')}
                </atlas-table-col>
                <atlas-table-col>
                    ${g.formatDate(date: payment.dueDate, format: 'dd/MM/yyyy')}
                </atlas-table-col>
                <atlas-table-col>${payment.status}</atlas-table-col>
                <atlas-button-group slot="actions" group-all>
                    <atlas-icon-button
                            icon="pencil"
                            theme="primary"
                            description="Editar cobrança"
                            href="${createLink(controller: "payment", action: "show", id: payment.id)}"
                    >
                    </atlas-icon-button>
                    <g:if test="${payment.status != paymentStatuses.RECEIVED && payment.status != paymentStatuses.OVERDUE}">
                        <atlas-icon-button
                                icon="money "
                                theme="success"
                                class="js-confirm-cash-btn"
                                description="Confirmar recebimento em dinheiro"
                                href="${createLink(controller: 'payment', action: 'confirmCash', params: [paymentId: payment.id])}"
                        ></atlas-icon-button>
                    </g:if>
                    <g:if test="${ !payment.deleted }">
                        <atlas-icon-button
                                icon="trash"
                                theme="danger"
                                description="Desativar cobrança"
                                href="${createLink(controller: 'payment', action: 'disable', params: [paymentId: payment.id])}"
                        ></atlas-icon-button>
                    </g:if>
                    <g:else>
                        <atlas-icon-button
                                icon="check"
                                theme="success"
                                description="Reativar cobrança"
                                href="${createLink(controller: 'payment', action: 'restore', params: [paymentId: payment.id])}"
                        ></atlas-icon-button>
                    </g:else>
                </atlas-button-group>
            </atlas-table-row>
        </g:each>
    </atlas-table-body>
</atlas-table>