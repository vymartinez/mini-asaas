<atlas-input type="hidden" class="js-notification-url" value="${createLink(controller: 'notification', action: 'list')}"></atlas-input>
<atlas-layout gap="2" fluid justify="space-between">
    <atlas-notification-center-group>
        <g:if test="${ notifications }">
            <g:each in="${ notifications }" var="notification">
                <g:render template="/templates/notification/card" model="${[notification: notification]}"/>
            </g:each>
        </g:if>
        <g:else>
            <atlas-empty-state
                icon="alert-circle"
                header="Sem notificações"
            >
                Aqui você pode visualizar as notificações do sistema.
            </atlas-empty-state>
        </g:else>
    </atlas-notification-center-group>
    <g:render template="/templates/pagination" model="${[totalCount: totalCount, max: max]}"/>
</atlas-layout>