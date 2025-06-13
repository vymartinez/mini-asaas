<atlas-navbar>
    <atlas-layout inline alignment="center" justify="end" >
        <atlas-dropdown-button type="filled" icon="asaas-logo" theme="primary" description="Olá, Usuário" pill >
            <atlas-dropdown-item 
                icon="user"
                href="${createLink(controller: "dashboard", action: "profile")}"
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
<atlas-layout>
    <atlas-dropdown-button type="filled" icon="asaas-logo" theme="primary" hide-arrow description="Olá, Usuário" block hide-on-desktop >
        <atlas-dropdown-item
                icon="user"
                href="${createLink(controller: "dashboard", action: "profile")}"
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