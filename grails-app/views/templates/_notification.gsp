<atlas-offcanvas
    header="Notificações"
    has-button-block
    id="notification-center"
>
    <atlas-layout gap="2">
        <atlas-notification-center-group header="Hoje">
           
        </atlas-notification-center-group>

        <atlas-notification-center-group header="Ontem">
            
        </atlas-notification-center-group>

        <atlas-notification-center-group header="Últimos 7 dias">
            
        </atlas-notification-center-group>

        <atlas-notification-center-group header="Últimos 30 dias">
            
        </atlas-notification-center-group>

        <atlas-text size="xs" muted alignment="center">
            Estas são todas as notificações dos últimos 30 dias
        </atlas-text>
    </atlas-layout>

    <atlas-button
        slot="actions"
        description="Marcar todas como lidas"
        block
        type="ghost"
        icon="double-check"
        size="sm"
    >
    </atlas-button>

    <atlas-switch slot="header-action" label="Não lidas" name="unread">
    </atlas-switch>
</atlas-offcanvas>