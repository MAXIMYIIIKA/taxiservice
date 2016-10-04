package by.nichipor.taxiservice.security.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

/**
 * This class register Spring Security with the existing ApplicationContext.
 * <p>
 *     This would register the springSecurityFilterChain Filter
 *     for every URL in the application.
 * </p>
 */
public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {

    /**
     * This method adds a ContextLoaderListener
     * that loads the {@link SecurityConfig SecurityConfig}.
     */
    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}