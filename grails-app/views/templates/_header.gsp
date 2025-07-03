<atlas-navbar slot="navbar">
    <atlas-layout inline alignment="center" justify="space-between" >
        <atlas-button pill description="Central de Notificações" data-atlas-offcanvas="notification-center"></atlas-button>
        <atlas-dropdown-button type="filled" icon="asaas-logo" theme="primary" description="Olá, ${ formatTagLib.firstName(name: customer.name) }" pill >
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
    <atlas-dropdown-button type="filled" icon="asaas-logo" theme="primary" hide-arrow description="Olá, ${ formatTagLib.firstName(name: customer.name) }" block hide-on-desktop >
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