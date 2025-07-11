<%
    Map icons = [
        PENDING: 'money',
        RECEIVED: 'check-circle',
        OVERDUE: 'clock',
        CANCELED: 'x'
    ]
    Map descriptions = [
        PENDING: 'Pagamento pendente',
        RECEIVED: 'Pagamento recebido',
        OVERDUE: 'Pagamento atrasado',
        CANCELED: 'Pagamento cancelado'
    ]
%>

<atlas-card header="R$ ${payment.value}" background="light">
    <atlas-text>${formatTagLib.firstName(name: payment.payer.name)}</atlas-text>
    <atlas-layout inline mobile-inline gap="1">
        <atlas-icon name="${icons."${payment.status}"}"></atlas-icon>
        <atlas-text>${descriptions."${payment.status}"}</atlas-text>
    </atlas-layout>
</atlas-card>