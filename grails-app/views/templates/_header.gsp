<atlas-navbar>
    <atlas-layout inline alignment="center" justify="space-between">
        <g:render template="/templates/notification" />
        <atlas-dropdown-button type="filled" icon="asaas-logo" theme="primary" description="Olá!" pill >
            <atlas-dropdown-item 
                icon="user"
            >
                Ver perfil
            </atlas-dropdown-item>
            <atlas-dropdown-item 
                theme="danger" 
                icon="power"
            >
                Sair
            </atlas-dropdown-item>
        </atlas-dropdown-button>
    </atlas-layout>
</atlas-navbar>