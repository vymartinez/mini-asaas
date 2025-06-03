<!doctype html>
<html lang="pt-BR">
<head>
    <meta name="layout" content="external"/>
    <title>Mini Asaas</title>
</head>

<body>
    <main class="flex align-items-center justify-content-center w-100 h-screen bg-asaas">
        <section class="form bg-light p-5">
            <h2 class="h2 text-center">
                Cadastro de Usuários
            </h2>
            <form action="/customer/save" class="flex flex-column">
                <label for="name" class="col-form-label">
                    Nome
                    <input id="name" name="name" type="text" placeholder="Nome" class="form-control">
                </label>
                <label for="email" class="col-form-label">
                    Email
                    <input id="email" name="email" type="text" placeholder="Email" class="form-control">
                </label>
                <div class="flex inline">
                    <label for="cpfCnpj" class="col-form-label w-100">
                        CPF/CNPJ
                        <input id="cpfCnpj" name="cpfCnpj" type="text" placeholder="CPF/CNPJ" class="form-control">
                    </label>
                    <label for="cellphone" class="col-form-label w-100">
                        Telefone
                        <input id="cellphone" name="cellphone" type="text" placeholder="Telefone" class="form-control">
                    </label>
                </div>
                <div class="flex inline">
                    <label for="zipCode" class="col-form-label w-100">
                        CEP
                        <input id="zipCode" maxlength="9" name="zipCode" type="text" placeholder="CEP" class="form-control" onclose="getCep(cep)">
                    </label>
                    <label for="city" class="col-form-label w-100">
                        Bairro
                        <input id="city" name="city" type="text" placeholder="Bairro" class="form-control">
                    </label>
                </div>
                <div class="flex inline">
                    <label for="address" class="col-form-label w-100">
                        Logradouro
                        <input id="address" name="address" type="text" placeholder="Logradouro" class="form-control">
                    </label>
                    <label for="province" class="col-form-label">
                        UF
                        <input id="province" name="province" type="text" placeholder="UF" class="form-control">
                    </label>
                </div>
                <div class="flex inline mb-3">
                    <label for="complement" class="col-form-label w-100">
                        Complemento (Opcional)
                        <input id="complement" name="complement" type="text" placeholder="Complemento" class="form-control">
                    </label>
                    <label for="addressNumber" class="col-form-label w-100">
                        Número
                        <input id="addressNumber" name="addressNumber" type="text" placeholder="Complemento" class="form-control">
                    </label>
                </div>
                <button type="submit" class="btn text-white bg-asaas mb-5">
                    Cadastrar
                </button>
            </form>
        </section>
    </main>
    <asset:javascript src="script.js"/>
</body>
</html>
