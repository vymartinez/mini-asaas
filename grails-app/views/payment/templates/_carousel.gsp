<atlas-carousel items-per-page="${payments ? 3 : 1}">
    <g:if test="${ payments }">
        <g:each in="${ payments }" var="payment">
            <atlas-carousel-item>
                <g:render template="/payment/templates/card" model="${[payment: payment]}"/>
            </atlas-carousel-item>
        </g:each>
    </g:if>
    <g:else>
        <atlas-empty-state
            icon="alert-circle"
            header="Sem cobranças"
        >
            Aqui você pode visualizar as suas cobranças.
        </atlas-empty-state>
    </g:else>
</atlas-carousel>