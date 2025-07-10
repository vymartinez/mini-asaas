<g:each in="${payers}" var="p">
    <atlas-option value="${p.id}" label="${p.name}" <g:if test="${params.selectedId?.toLong() == p.id}">selected</g:if>></atlas-option>
</g:each>