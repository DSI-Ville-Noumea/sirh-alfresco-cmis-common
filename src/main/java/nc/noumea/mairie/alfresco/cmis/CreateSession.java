package nc.noumea.mairie.alfresco.cmis;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.stereotype.Service;

@Service
public class CreateSession {
	
	/**
	 * Retourne une session CMIS branchee sur Alfresco
	 * 
	 * @param alfrescoUrl String URL du serveur Alfresco
	 * @param alfrescoLogin String login pour se connecter a alfresco
	 * @param alfrescoPassword String password pour se connecter a alfresco
	 * @return Session session CMIS branchee sur alfresco
	 */
	public Session getSession(String alfrescoUrl, String alfrescoLogin, String alfrescoPassword) {
	    
	    // default factory implementation
		SessionFactoryImpl factory = SessionFactoryImpl.newInstance();
	    Map<String, String> parameter = new HashMap<String, String>();
	
	    // user credentials
	    parameter.put(SessionParameter.USER, alfrescoLogin);
	    parameter.put(SessionParameter.PASSWORD, alfrescoPassword);
	
	    // connection settings
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		parameter.put(SessionParameter.ATOMPUB_URL, alfrescoUrl+"alfresco/api/-default-/cmis/versions/1.1/atom");
//	    parameter.put(SessionParameter.WEBSERVICES_ACL_SERVICE, alfrescoUrl+"alfresco/cmisws/services/ACLService?wsdl");
//	    parameter.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE, alfrescoUrl+"alfresco/cmisws/services/DiscoveryService?wsdl");
//	    parameter.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE, alfrescoUrl+"alfresco/cmisws/services/MultiFilingService?wsdl");
//	    parameter.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE, alfrescoUrl+"alfresco/cmisws/services/NavigationService?wsdl");
//	    parameter.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE, alfrescoUrl+"alfresco/cmisws/services/ObjectService?wsdl");
//	    parameter.put(SessionParameter.WEBSERVICES_POLICY_SERVICE, alfrescoUrl+"alfresco/cmisws/services/PolicyService?wsdl");
//	    parameter.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE, alfrescoUrl+"alfresco/cmisws/services/RelationshipService?wsdl");
//	    parameter.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE, alfrescoUrl+"alfresco/cmisws/services/RepositoryService?wsdl");
//	    parameter.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE, alfrescoUrl+"alfresco/cmisws/services/VersioningService?wsdl");
	    // ATTENTION important : la classe AlfrescoObjectFactoryImpl n est utile que pour CMIS 1.0 et PAS pour CMIS 1.1
//	    parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
	    parameter.put(SessionParameter.REPOSITORY_ID, "-default-");
	    
	    // create session
		Session session = factory.getRepositories(parameter).get(0).createSession();
	    return session;
	}
}
