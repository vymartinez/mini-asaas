<g:if test="${flash.message}">
    <atlas-alert
        type="${flash.success ? 'success' : 'error'}"
        message="${flash.message}"
        class="js-atlas-alert"
    ></atlas-alert>
</g:if>