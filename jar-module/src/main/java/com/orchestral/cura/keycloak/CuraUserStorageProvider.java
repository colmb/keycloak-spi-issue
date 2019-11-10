package com.orchestral.cura.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.LDAPStorageProviderFactory;
import org.keycloak.storage.ldap.idm.store.ldap.LDAPIdentityStore;

/**
 * A custom implemetation of LDAPStorageProvider which wraps the standard
 * UserModel into a custom Cura User Model which returns our Cura ID rather
 * then Keycloaks.
 * 
 * @author Colm Brady
 */
public class CuraUserStorageProvider extends LDAPStorageProvider {

    private static final Logger log = Logger.getLogger(CuraUserStorageProvider.class);

    public CuraUserStorageProvider(LDAPStorageProviderFactory factory, KeycloakSession session, ComponentModel model,
            LDAPIdentityStore ldapIdentityStore) {
        super(factory, session, model, ldapIdentityStore);
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
    	log.debug("getUserById custom");
        UserModel userModel = super.getUserById(id, realm);
        return new CuraUserModelDelegate(userModel);
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
    	log.debug("getUserByUsername custom");
    	UserModel userModel = super.getUserByUsername(username, realm);
        return new CuraUserModelDelegate(userModel);
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
    	log.debug("getUserByEmail custom");
    	UserModel userModel = super.getUserByEmail(email, realm);
        return new CuraUserModelDelegate(userModel);
    }

}
