<%
    Map status = [
        CREATED: 'success',
        PAID: 'success',
        EXPIRED: 'in progress',
        DELETED: 'error'
    ]

    Map icons = [
        CREATED: 'money',
        PAID: 'check-circle',
        EXPIRED: 'clock',
        DELETED: 'x'
    ]
%>

<atlas-notification-center-card
        href="${createLink(controller: 'payment', action: 'show', id: notification.payment.id)}"
        header="${notification.subject}"
        icon="${icons."${notification.notificationStatus}"}"
        date="${formatTagLib.date(date: notification.dateCreated)}"
        status="${status."${notification.notificationStatus}"}"
>
    ${notification.body}
</atlas-notification-center-card>