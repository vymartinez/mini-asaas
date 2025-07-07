package mini.asaas.utils

import grails.gorm.dirty.checking.DirtyCheck

@DirtyCheck
abstract class BaseEntity {

    Date dateCreated

    Date lastUpdated

    Boolean deleted = false
    
    static mapping = {
        tablePerHierarchy false
    }
}