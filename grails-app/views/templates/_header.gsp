<atlas-navbar slot="navbar">
    <atlas-layout inline alignment="center" slot="actions" gap="3" mobile-inline>
        <atlas-icon-button description="Central de Notificações" data-atlas-offcanvas="notification-center" icon="bell"></atlas-icon-button>
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
                href="${createLink(controller: "logout", action: "index")}"
            >
                Sair
            </atlas-dropdown-item>
        </atlas-dropdown-button>
    </atlas-layout>
</atlas-navbar>