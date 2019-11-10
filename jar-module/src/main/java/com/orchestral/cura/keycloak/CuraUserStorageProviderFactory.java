package com.orchestral.cura.keycloak;

import java.util.Map;

import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.ldap.LDAPIdentityStoreRegistry;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.LDAPStorageProviderFactory;
import org.keycloak.storage.ldap.idm.store.ldap.LDAPIdentityStore;
import org.keycloak.storage.ldap.mappers.LDAPConfigDecorator;

/**
 * @author Colm Brady
 */
public class CuraUserStorageProviderFactory extends LDAPStorageProviderFactory {

    public static final String PROVIDER_NAME = "cura-keycloak-user-store";

    private LDAPIdentityStoreRegistry ldapStoreRegistry;
    
    @Override
    public LDAPStorageProvider create(KeycloakSession session, ComponentModel model) {
        Map<ComponentModel, LDAPConfigDecorator> configDecorators = getLDAPConfigDecorators(session, model);

        LDAPIdentityStore ldapIdentityStore = this.ldapStoreRegistry.getLdapStore(session, model, configDecorators);
        return new CuraUserStorageProvider(this, session, model, ldapIdentityStore);
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    @Override
    public void init(Config.Scope config) {
        this.ldapStoreRegistry = new LDAPIdentityStoreRegistry();
    }

    @Override
    public void close() {
        this.ldapStoreRegistry = null;
    }
}