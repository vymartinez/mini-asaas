package mini.asaas.user

import mini.asaas.BaseController
import mini.asaas.enums.MessageType
import mini.asaas.adapters.user.SaveUserAdapter
import mini.asaas.adapters.user.UpdateUserAdapter


class UserController extends BaseController {

    UserService userService

    def index() {
        params.max = limitPerPage
        params.offset = offset

        List<User> users = userService.list(params)
        Long userCount = userService.count()

        return [users: users, userCount: userCount]
    }

    def show() {
        Long id   = params.long('id')
        User user = userService.get(id)
        if (!user) {
            buildFlashAlert("Usuário não encontrado para ID ${id}", MessageType.ERROR, false)
            redirect action: 'index'
            return [:]
        }

        return [user: user]
    }

    def create() {
        return [adapter: new SaveUserAdapter(params)]
    }

    def save() {
        SaveUserAdapter adapter = new SaveUserAdapter(params)
        bindData(adapter, params)

        try {
            User user = userService.create(adapter)
            buildFlashAlert("Usuário criado com sucesso: ${user.id}", MessageType.SUCCESS, true)
            redirect(action: 'show', id: user.id)
        } catch (Exception exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            render(view: 'create', model: [adapter: adapter])
        }
    }

    def edit() {
        Long id = params.long('id')
        User user = userService.get(id)
        if (!user) {
            buildFlashAlert("Usuário não encontrado para edição (ID ${id})", MessageType.ERROR, false)
            redirect(action: 'index')
            return [:]
        }
        UpdateUserAdapter adapter = new UpdateUserAdapter()
        adapter.username = user.username
        adapter.enable = user.enabled
        adapter.roles = user.authorities*.authority as List<String>

        return [id: id, adapter: adapter]
    }

    def update() {
        Long id = params.long('id')
        UpdateUserAdapter adapter = new UpdateUserAdapter(params)
        bindData(adapter, params)

        try {
            User user = userService.update(id, adapter)
            buildFlashAlert("Usuário atualizado com sucesso: ${user.id}", MessageType.SUCCESS, true)
            redirect action: 'show', id: user.id
        } catch (Exception exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            render(view: 'edit', model: [id: id, adapter: adapter])
        }
    }

    def delete() {
        Long id = params.long('id')
        try {
            userService.softDelete(id)
            buildFlashAlert("Usuário desabilitado com sucesso: ${id}", MessageType.SUCCESS, true)
        } catch (Exception exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }
        redirect(action: 'index')
    }

    def restore() {
        Long id = params.long('id')
        try {
            userService.restore(id)
            buildFlashAlert("Usuário restaurado com sucesso: ${id}", MessageType.SUCCESS, true)
            redirect action: 'show', id: id
        } catch (Exception e) {
            buildFlashAlert(e.message, MessageType.ERROR, false)
            redirect(action: 'show', id: id)
        }
    }

    protected void notFound() {
        render(status: 404)
    }
}