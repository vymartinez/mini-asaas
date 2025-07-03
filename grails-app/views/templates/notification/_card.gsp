<%
    Map status = [
        PAYMENT_CREATED: 'success',
        PAYMENT_PAID: 'success',
        PAYMENT_EXPIRED: 'in progress',
        PAYMENT_DELETED: 'error'
    ]

    Map icons = [
        PAYMENT_CREATED: 'money',
        PAYMENT_PAID: 'check-circle',
        PAYMENT_EXPIRED: 'clock',
        PAYMENT_DELETED: 'x'
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