
package com.orchestral.cura.keycloak;

import org.keycloak.storage.StorageId;
import org.keycloak.models.utils.UserModelDelegate;
import org.keycloak.models.UserModel;

/**
 * Delegation pattern. Used to proxy UserModel implementation 
 * so we can adapt the ID which is used by Keycloak to build 
 * the Access Token Subject.
 * 
 * @author Colm Brady
 */
public class CuraUserModelDelegate extends UserModelDelegate {

	/**
	 * 
	 * @param delegate a user model to delegate to
	 */
    public CuraUserModelDelegate(UserModel delegate) {
    	super(delegate);
    }

    /**
     * Overrides getId so that downstream, the Access Token 
     * represents Patient Portals user id rather then Keycloaks 
     * representation of it.
     * 
     * This will allow OHP resolve the Patient Portal user
     * from the Keycloak issued Access Token.
     * 
     * Keycloak expects the ID to be f:<storageid>:<externalid> but this
     * wont match Patient Portals user id, which is just <externalid>.
     * 
     * @return the external id from the StorageId model in Keycloak.
     */
    @Override
    public String getId() {
        StorageId storageId = new StorageId(getDelegate().getId());
        return storageId.getExternalId();
    }

}