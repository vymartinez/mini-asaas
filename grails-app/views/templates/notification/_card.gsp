<%
    Map status = [
        PAYMENT_CREATED: 'success',
        PAYMENT_PAID: 'success',
        PAYMENT_EXPIRED: 'in progress',
        PAYMENT_DELETED: 'error',
        PAYMENT_CONFIRMEDINCASH: 'success',
        PAYMENT_RESTORED: 'in progress',
        PAYMENT_UPDATED: 'in progress',
    ]

    Map icons = [
        PAYMENT_CREATED: 'money',
        PAYMENT_PAID: 'check-circle',
        PAYMENT_EXPIRED: 'clock',
        PAYMENT_DELETED: 'x',
        PAYMENT_CONFIRMEDINCASH: 'money',
        PAYMENT_RESTORED: 'calendar-sync',
        PAYMENT_UPDATED: 'pencil'
    ]
%>

<atlas-notification-center-card
    href="${createLink(controller: 'payment', action: 'list')}"
    header="${notification.subject}"
    icon="${icons."${notification.type}"}"
    date="${formatTagLib.date(date: notification.dateCreated)}"
    status="${status."${notification.type}"}"
>
    ${notification.body}
</atlas-notification-center-card>