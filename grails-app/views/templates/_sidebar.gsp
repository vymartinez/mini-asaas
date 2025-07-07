<atlas-sidebar slot="sidebar" default-collapsed >
    <atlas-sidebar-menu slot="body">
        <atlas-sidebar-menu-item
                icon="users"
                value="payers-group"
                text="Pagadores"
            ${ controllerName == 'payer' ? 'active' : '' }
        >
            <atlas-sidebar-menu-item
                    icon="user"
                    value="payer"
                    text="Criar Pagador"
                    href="${createLink(controller: "payer", action: 'create')}"
                ${ controllerName == 'payer' && actionName == 'create' ? 'active' : '' }
            ></atlas-sidebar-menu-item>

            <atlas-sidebar-menu-item
                    icon="users"
                    value="payers"
                    text="Ver Pagadores"
                    href="${createLink(controller: "payer", action: 'list')}"
                ${ controllerName == 'payer' && actionName == 'list' ? 'active' : '' }
            ></atlas-sidebar-menu-item>
        </atlas-sidebar-menu-item>

        <atlas-sidebar-menu-item
                icon="money"
                value="payments-group"
                text="Cobranças"
            ${controllerName == 'payment' ? 'active' : ''}
        >
            <atlas-sidebar-menu-item
                    icon="file"
                    value="payment"
                    text="Criar Cobrança"
                    href="${createLink(controller: 'payment', action: 'create')}"
                ${controllerName == 'payment' && actionName == 'register' ? 'active' : ''}
            ></atlas-sidebar-menu-item>

            <atlas-sidebar-menu-item
                    icon="files"
                    value="payments"
                    text="Ver Cobranças"
                    href="${createLink(controller: 'payment', action: 'list')}"
                ${controllerName == 'payment' && actionName == 'list' ? 'active' : ''}
            >
            </atlas-sidebar-menu-item>
    </atlas-sidebar-menu>
</atlas-sidebar>