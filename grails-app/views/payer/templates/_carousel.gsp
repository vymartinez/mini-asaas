<atlas-carousel items-per-page="${ payers ? 3 : 1 }">>
    <g:if test="${ payers }">
        <g:each in="${ payers }" var="payer">
            <atlas-carousel-item>
                <g:render template="/payer/templates/carouselCard" model="${[payer: payer]}"/>
            </atlas-carousel-item>
        </g:each>
    </g:if>
    <g:else>
        <atlas-empty-state
            icon="alert-circle"
            header="Sem pagadores"
        >
            Aqui você pode visualizar os seus pagadores.
        </atlas-empty-state>
    </g:else>
</atlas-carousel>