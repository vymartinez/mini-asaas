<!doctype html>
<html lang="pt-BR">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link
            rel="stylesheet"
            href="https://atlas.asaas.com/v26.2.0/atlas.css"
            crossorigin="anonymous">
    <script
            defer
            src="https://atlas.asaas.com/v26.2.0/atlas.js"
            crossorigin="anonymous"
    ></script>
    <link
            rel="stylesheet"
            href="https://atlas.asaas.com/reset.css"
            crossorigin="anonymous">
    <g:layoutHead/>
</head>

<body class="has-atlas">
<atlas-screen fullscreen>
    <g:render template="/templates/sidebar" />
    <atlas-page class="js-atlas-page" container>
    <atlas-page-header 
            slot="header"
            page-name="${pageProperty(name: "body.page-title")}"
        >
            <g:render template="/templates/header" />
        </atlas-page-header>
        <atlas-page-content slot="content" class="js-atlas-content">
        <g:if test="${flash.message}">
            <atlas-alert
                    type="${flash.success ? 'success' : 'error'}"
                    message="${flash.message}"
                    class="js-atlas-alert"
            ></atlas-alert>
        </g:if>
            <g:layoutBody />
        </atlas-page-content>
    </atlas-page>
</atlas-screen>
<asset:javascript src="application.js"/>
</body>
</html>