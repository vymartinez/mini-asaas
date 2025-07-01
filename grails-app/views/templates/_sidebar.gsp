<atlas-sidebar slot="sidebar">
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
        >
            <atlas-sidebar-menu-item
                icon="file"
                value="payment"
                text="Criar Cobrança"
            ></atlas-sidebar-menu-item>

            <atlas-sidebar-menu-item
                icon="files"
                value="payments"
                text="Ver Cobranças"
            >
        </atlas-sidebar-menu-item>
    </atlas-sidebar-menu>
</atlas-sidebar>