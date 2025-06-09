package mini.asaas.security

import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.user.User
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent
import org.springframework.beans.factory.annotation.Autowired
import grails.events.annotation.gorm.Listener
import groovy.transform.CompileStatic

@CompileStatic
class UserPasswordEncoderListener {

    @Autowired
    SpringSecurityService springSecurityService

    @Listener(User)
    public void onPreInsertEvent(PreInsertEvent event) {
        encodePasswordForEvent(event)
    }

    @Listener(User)
    public void onPreUpdateEvent(PreUpdateEvent event) {
        encodePasswordForEvent(event)
    }

    private void encodePasswordForEvent(AbstractPersistenceEvent event) {
        if (event.entityObject instanceof User) {
            User u = event.entityObject as User
            if (u.password && ((event instanceof PreInsertEvent) || (event instanceof PreUpdateEvent && u.isDirty('password')))) {
                event.getEntityAccess().setProperty('password', encodePassword(u.password))
            }
        }
    }

    private String encodePassword(String password) {
        if (!springSecurityService?.passwordEncoder) {
            throw new IllegalStateException("Password encoder not configured properly. Cannot encode password.")
        }
        return springSecurityService.encodePassword(password)
    }
}
