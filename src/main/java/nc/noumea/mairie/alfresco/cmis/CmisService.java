package nc.noumea.mairie.alfresco.cmis;

import java.io.File;
import java.io.IOException;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CmisService {
	
	private Logger logger = LoggerFactory.getLogger(CmisService.class);
	
	/**
	 * Retourne le fichier enregistre dans Alfresco
	 * par rapport au nodeRef en parametre
	 * 
	 * @param session Session La session CMIS
	 * @param nodeRef String La reference du node Alfresco
	 * @return Fichier le fichier
	 */
	public File getFile(Session session, String nodeRef) {
		
		Document document = (Document) session.getObject(nodeRef);
		
		File file = new File(document.getName());
		
		try {
			FileUtils.copyInputStreamToFile(document.getContentStream().getStream(), file);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
		
		return file;
	}
	
	/**
	 * Retourne l identifiant d un object CMIS
	 * par rapport au chemin passe en parametre. 
	 * 
	 * @param path String le chemin de l objet dans Alfresco
	 * @param session Session La session CMIS
	 * @return String L identifiant de l objet CMIS
	 */
	public String getIdObjectCmis(String path, Session session) {
		
		CmisObject object = (CmisObject) session.getObjectByPath(path);
		return null != object ? object.getId() : null;
	}
}
