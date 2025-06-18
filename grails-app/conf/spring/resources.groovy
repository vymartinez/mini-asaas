import org.grails.spring.context.support.ReloadableResourceBundleMessageSource

// Place your Spring DSL code here
beans = {
    messageSource(ReloadableResourceBundleMessageSource) {
        basenames = ['classpath:messages']
        defaultEncoding = 'UTF-8'
    }

    userPasswordEncoderListener(UserPasswordEncoderListener)
}
