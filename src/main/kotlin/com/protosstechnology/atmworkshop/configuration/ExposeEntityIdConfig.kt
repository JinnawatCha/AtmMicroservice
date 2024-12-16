package com.protosstechnology.atmworkshop.configuration

import com.protosstechnology.atmworkshop.entity.AccountRequest
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry

@Configuration
open class ExposeEntityIdConfig : RepositoryRestConfigurer {
    override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration, cors: CorsRegistry) {
        config!!.exposeIdsFor(AccountRequest::class.java)
    }
}