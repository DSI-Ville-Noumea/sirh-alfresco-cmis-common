package nc.noumea.mairie.alfresco.cmis;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConstraintException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CmisService {
	
	private Logger logger = LoggerFactory.getLogger(CmisService.class);
	
	final static private String FOLDER_AGENT_EXIST = "Le dossier Agent existe déjà.";
	final static private String ERROR_FOLDER_AGENTS = "Une erreur est survenue à la récupération du dossier Agents."; 
	final static private String CREATE_FOLDER_OK = "Répertoire %s créé";
	final static private String CREATE_TREE_OK = "Arborescence créée pour l'agent %s";
	
	final static public String PATH_FOLDER_AGENTS = "/Sites/SIRH/documentLibrary/Agents/"; 
	
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
	
	/**
	 * Cree l'arborescence de dossier pour un agent dans le site SIRH sous Alfresco.
	 * 
	 * @param idAgent Integer ID de l agent
	 * @param nomAgent String Nom de l agent
	 * @param prenomAgent String Prenom de l agent
	 * @param session Session CMIS
	 */
	public void createArborescenceAgent(Integer idAgent, String nomAgent, String prenomAgent, Session session) {
		
		logger.info("CmisService/createArborescenceAgent for agent " + prenomAgent + " " + nomAgent + " " + idAgent);
		
		String pathAgentRelative = CmisUtils.getPathAgent(idAgent, nomAgent, prenomAgent);
		String pathAgentAbsolute = PATH_FOLDER_AGENTS + pathAgentRelative;
		
		// on cherche le repertoire distant 
		CmisObject object = null;
		try {
			object = session.getObjectByPath(pathAgentAbsolute);
		} catch(CmisObjectNotFoundException e) {
		}
		
	    if(null != object) {
	    	// si il existe, on ne fait rien
	    	logger.debug(FOLDER_AGENT_EXIST);
	    	return;
	    }
	    
	    // sinon on le cree avec toute l arborescence
	    
	    // 1. on recupere le dossier parent Agents
	    try {
			object = session.getObjectByPath(PATH_FOLDER_AGENTS);
		} catch(CmisObjectNotFoundException e) {
	    	logger.debug(ERROR_FOLDER_AGENTS);
	    	return;
		}
		
	    if(null == object) {
	    	logger.debug(ERROR_FOLDER_AGENTS);
	    	return;
	    }

	    Folder folder = (Folder) object;
	    // 2. on cree l arborescence
	    try {
	    	Map<String, String> propertiesFolderCustomAgent = new HashMap<String, String>();
	    	propertiesFolderCustomAgent.put("cmis:objectTypeId",  "cmis:folder");
	    	propertiesFolderCustomAgent.put("cmis:name", pathAgentRelative);
		    
	    	Folder folderCustomAgent = folder.createFolder(propertiesFolderCustomAgent);
	    	logger.debug(String.format(CREATE_FOLDER_OK, pathAgentRelative));
	        
	    	/////////////////////////////////
	    	////////// ABSENCES /////////////
//	    	Map<String, String> propertiesFolderAbsences = new HashMap<String, String>();
//	    	propertiesFolderAbsences.put("cmis:objectTypeId",  "cmis:folder");
//	    	propertiesFolderAbsences.put("cmis:name", FOLDER_ABSENCES);
//	    	Folder folderAbsences = folderCustomAgent.createFolder(propertiesFolderAbsences);
//	    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_ABSENCES));
//	    	
//		    	Map<String, String> propertiesFolderASA = new HashMap<String, String>();
//		    	propertiesFolderASA.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderASA.put("cmis:name", FOLDER_ASA);
//		    	folderAbsences.createFolder(propertiesFolderASA);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_ASA));
//		    	
//		    	Map<String, String> propertiesFolderMaladies = new HashMap<String, String>();
//		    	propertiesFolderMaladies.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderMaladies.put("cmis:name", FOLDER_MALADIES);
//		    	folderAbsences.createFolder(propertiesFolderMaladies);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_MALADIES));
//		    	
//		    	Map<String, String> propertiesFolderCA = new HashMap<String, String>();
//		    	propertiesFolderCA.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderCA.put("cmis:name", FOLDER_CA);
//		    	folderAbsences.createFolder(propertiesFolderCA);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_CA));
//		    	
//		    	Map<String, String> propertiesFolderCE = new HashMap<String, String>();
//		    	propertiesFolderCE.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderCE.put("cmis:name", FOLDER_CE);
//		    	folderAbsences.createFolder(propertiesFolderCE);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_CE));
	    	
	    	////////////////////////////////////
	    	///////// Administratif ////////////
//	    	Map<String, String> propertiesFolderAdministratif = new HashMap<String, String>();
//	    	propertiesFolderAdministratif.put("cmis:objectTypeId",  "cmis:folder");
//	    	propertiesFolderAdministratif.put("cmis:name", FOLDER_ADMINISTRATIF);
//	    	Folder folderAdministratif = folderCustomAgent.createFolder(propertiesFolderAdministratif);
//	    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_ADMINISTRATIF));
//	    	
//		    	Map<String, String> propertiesFolderCasier = new HashMap<String, String>();
//		    	propertiesFolderCasier.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderCasier.put("cmis:name", FOLDER_CASIER);
//		    	folderAdministratif.createFolder(propertiesFolderCasier);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_CASIER));
//		    	
//		    	Map<String, String> propertiesFolderPermis = new HashMap<String, String>();
//		    	propertiesFolderPermis.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderPermis.put("cmis:name", FOLDER_PERMIS);
//		    	folderAdministratif.createFolder(propertiesFolderPermis);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_PERMIS));
//		    	
//		    	Map<String, String> propertiesFolderPI = new HashMap<String, String>();
//		    	propertiesFolderPI.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderPI.put("cmis:name", FOLDER_PI);
//		    	folderAdministratif.createFolder(propertiesFolderPI);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_PI));
//		    	
//		    	Map<String, String> propertiesFolderPhotos = new HashMap<String, String>();
//		    	propertiesFolderPhotos.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderPhotos.put("cmis:name", FOLDER_PHOTOS);
//		    	folderAdministratif.createFolder(propertiesFolderPhotos);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_PHOTOS));
//		    	
//		    	Map<String, String> propertiesFolderDivers = new HashMap<String, String>();
//		    	propertiesFolderDivers.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderDivers.put("cmis:name", FOLDER_DIVERS);
//		    	folderAdministratif.createFolder(propertiesFolderDivers);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_DIVERS));
	    	
	    	///////////////////////////////
	    	///////// Carrière ////////////
//	    	Map<String, String> propertiesFolderCarriere = new HashMap<String, String>();
//	    	propertiesFolderCarriere.put("cmis:objectTypeId",  "cmis:folder");
//	    	propertiesFolderCarriere.put("cmis:name", FOLDER_CARRIERE);
//	    	Folder folderCarriere = folderCustomAgent.createFolder(propertiesFolderCarriere);
//	    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_CARRIERE));
//
//		    	Map<String, String> propertiesFolderContrats = new HashMap<String, String>();
//		    	propertiesFolderContrats.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderContrats.put("cmis:name", FOLDER_CONTRATS);
//		    	folderCarriere.createFolder(propertiesFolderContrats);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_CONTRATS));
//	
//		    	Map<String, String> propertiesFolderDiplomes = new HashMap<String, String>();
//		    	propertiesFolderDiplomes.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderDiplomes.put("cmis:name", FOLDER_DIPLOMES);
//		    	folderCarriere.createFolder(propertiesFolderDiplomes);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_DIPLOMES));
//	
//		    	Map<String, String> propertiesFolderEAE = new HashMap<String, String>();
//		    	propertiesFolderEAE.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderEAE.put("cmis:name", FOLDER_EAE);
//		    	folderCarriere.createFolder(propertiesFolderEAE);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_EAE));
//	
//		    	Map<String, String> propertiesFolderEvolutions = new HashMap<String, String>();
//		    	propertiesFolderEvolutions.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderEvolutions.put("cmis:name", FOLDER_EVOLUTION);
//		    	folderCarriere.createFolder(propertiesFolderEvolutions);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_EVOLUTION));
//	
//		    	Map<String, String> propertiesFolderFDP = new HashMap<String, String>();
//		    	propertiesFolderFDP.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderFDP.put("cmis:name", FOLDER_FDP);
//		    	folderCarriere.createFolder(propertiesFolderFDP);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_FDP));
//	
//		    	Map<String, String> propertiesFolderFormations = new HashMap<String, String>();
//		    	propertiesFolderFormations.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderFormations.put("cmis:name", FOLDER_FORMATION);
//		    	folderCarriere.createFolder(propertiesFolderFormations);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_FORMATION));
//	
//		    	Map<String, String> propertiesFolderSuiviDisciplinaire = new HashMap<String, String>();
//		    	propertiesFolderSuiviDisciplinaire.put("cmis:objectTypeId",  "cmis:folder");
//		    	propertiesFolderSuiviDisciplinaire.put("cmis:name", FOLDER_SUIVI_DISCIPLINAIRE);
//		    	folderCarriere.createFolder(propertiesFolderSuiviDisciplinaire);
//		    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_SUIVI_DISCIPLINAIRE));

	    	///////// Médical ////////////
//	    	Map<String, String> propertiesFolderMedical = new HashMap<String, String>();
//	    	propertiesFolderMedical.put("cmis:objectTypeId",  "cmis:folder");
//	    	propertiesFolderMedical.put("cmis:name", FOLDER_MEDICAL);
//	    	folderCustomAgent.createFolder(propertiesFolderMedical);
//	    	logger.debug(String.format(CREATE_FOLDER_OK, FOLDER_MEDICAL));
	    	
	    } catch(CmisContentAlreadyExistsException e) {
	    	logger.error("CmisContentAlreadyExistsException "  + pathAgentRelative);
	    	logger.debug(e.getMessage());
	    	return;
	    } catch(CmisConstraintException e) {
	    	logger.error("CmisConstraintException " + pathAgentRelative);
	    	logger.debug(e.getMessage());
	    	return;
	    }
	    
	    ///////// FIN ///////////
	    logger.info(String.format(CREATE_TREE_OK, idAgent));
	}
}
