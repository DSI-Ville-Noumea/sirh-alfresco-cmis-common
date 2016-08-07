package nc.noumea.mairie.alfresco.cmis;

import java.text.SimpleDateFormat;
import java.util.Date;

import nc.noumea.mairie.abs.RefTypeGroupeAbsenceEnum;
import nc.noumea.mairie.ptg.TypeEtatPayeurPointageEnum;

public final class CmisUtils {

	private static final String UNDERSCORE = "_";
	public static final String SLASH = "/";
	private static final Integer NOMBRE_CARACTERES_MAX = 25;

	protected static final String PATH_SIRH = "/Sites/SIRH/documentLibrary/";
	protected static final String PATH_ETATS_PAYEUR = "/Sites/SIRH/documentLibrary/Pointages/";
	protected static final String PATH_ABSENCES_AGENTS = "/Sites/SIRH/documentLibrary/Agents/";
	protected static final String PATH_ABSENCES = "/Absences/";
	
	private static SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	// message d erreurs
	public static final String ERROR_UPLOAD = "Erreur lors de l'envoi du fichier.";
	public static final String ERROR_PATH = "Erreur lors de l'envoi du fichier : le dossier distant n'existe pas.";
	
	
	// arborescence Dossier d'un agent
	final static private String FOLDER_ABSENCES = "Absences";
		final static private String FOLDER_MALADIES = "Maladies";
		final static private String FOLDER_CA = "Congés annuels";
		final static private String FOLDER_CE = "Congés Exceptionnels";
		final static private String FOLDER_ASA = "Absences Syndicales";
	final static private String FOLDER_ADMINISTRATIF = "Administratif";
		final static private String FOLDER_CASIER = "Casier";
		final static private String FOLDER_PERMIS = "Permis";
		final static private String FOLDER_PI = "Pièces Identité";
		final static private String FOLDER_PHOTOS = "Photos";
		final static private String FOLDER_DIVERS = "Divers";
	final static private String FOLDER_CARRIERE = "Carrière";
		final static private String FOLDER_CONTRATS = "Contrats";
		final static private String FOLDER_DIPLOMES = "Diplômes";
		final static private String FOLDER_EAE = "EAE";
		final static private String FOLDER_EVOLUTION = "Evolutions";
		final static private String FOLDER_FDP = "Fiches de Poste";
		final static private String FOLDER_FORMATION = "Formations";
		final static private String FOLDER_SUIVI_DISCIPLINAIRE = "Suivi Disciplinaire";
	final static private String FOLDER_MEDICAL = "Médical";
		final static private String FOLDER_AT_MP = "AT MP";
		final static private String FOLDER_HANDICAP = "Handicap";
		final static private String FOLDER_VM = "Visites médicales";

    protected CmisUtils () { 
    }
    
	/**
	 * exemple de nodeRef : "workspace://SpacesStore/1a344bd7-6422-45c6-94f7-5640048b20ab"
	 * exemple d URL a retourner :
	 * http://localhost:8080/alfresco/service/api/node/workspace/SpacesStore/418c511a-7c0a-4bb1-95a2-37e5946be726/content
	 * 
	 * @param nodeRef String
	 * @return String l URL pour acceder au document directement a alfresco
	 */
	public static String getUrlOfDocument(String alfrescoUrl, String nodeRef) {
		
		if(null == nodeRef) {
			return null;
		}
		
		String[] properties = nodeRef.split(SLASH);
		
		String result = alfrescoUrl + "alfresco/service/api/node/";
		
		for(int i=0; i<properties.length; i++) {
			if( "".equals(properties[i].trim()))
				continue;
			
			result += properties[i].replace(":", "") + SLASH;
		}
		result += "content";
		
		return result;
	}
	
	/**
	 * Retourne le nom pour une piece jointe ajoutee une demande
	 * d absence
	 * 
	 * @param typeDemande String Type d absence
	 * @param nom String nom agent
	 * @param prenom String prenom de l agent
	 * @param date Date de debut de la demande
	 * @param sequence Integer sequence si boucle
	 * @return String le nom du fichier absence
	 */
	public static String getPatternAbsence(String typeDemande, String nom, String prenom, Date date, Integer sequence) { 

		String nomCustom = "";
		String prenomCustom = "";
		
		if(nom.length() + prenom.length() > NOMBRE_CARACTERES_MAX) {
			if(nom.length() > NOMBRE_CARACTERES_MAX) {
				nomCustom = nom.substring(0, NOMBRE_CARACTERES_MAX);
			}else{
				nomCustom = nom;
				prenomCustom = prenom.substring(0, NOMBRE_CARACTERES_MAX-nom.length());
			}
		}else{
			nomCustom = nom;
			prenomCustom = prenom;
		}
		
		String result = "ABS" + UNDERSCORE + typeDemande.toUpperCase() + UNDERSCORE + nomCustom.trim().toUpperCase() 
				+ (prenomCustom.trim().equals("") ? "" : UNDERSCORE + prenomCustom.trim().toUpperCase()) + UNDERSCORE 
				+ sdfyyyyMMdd.format(date) + UNDERSCORE + (null != sequence ? sequence : "");
		result = result.replace(" ", UNDERSCORE);
		return result;
	}
	
	/**
	 * Retourne le nom du dossier agent dans le site SIRH
	 * 
	 * @param idAgent Integer ID de l agent
	 * @param nom String nom de l agent
	 * @param prenom String Prenom de l agent
	 * @return String Nom du dossier agent
	 */
	public static String getPathAgent(Integer idAgent, String nom, String prenom) {
		if(null == nom
				|| null == prenom
				|| null == idAgent) {
			return null;
		}
		return prenom + UNDERSCORE + nom + UNDERSCORE + idAgent;
	}
	
	/**
	 * retourne le chemin dans le site SIRH pour la piece jointe a ajouter
	 * 
	 * @param idAgent Integer ID de l agent
	 * @param nom String nom de l agent
	 * @param prenom String prenom de l agent
	 * @param idRefGroupeAbsence Integer Groupe d absence
	 * @param fromHSCT boolean provient ou non de HSCT dans SIRH
	 * @return String retourne le chemin dans le site SIRH pour la piece jointe a ajouter
	 */
	public static String getPathAbsence(Integer idAgent, String nom, String prenom, Integer idRefGroupeAbsence, boolean fromHSCT) {
		
		String folderTypeAbsence = null;
		
		if(fromHSCT
				&& idRefGroupeAbsence.equals(RefTypeGroupeAbsenceEnum.MALADIES.getValue())) {
			folderTypeAbsence = SLASH + FOLDER_MEDICAL + SLASH + FOLDER_AT_MP;
		}else{
			folderTypeAbsence = PATH_ABSENCES + RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(idRefGroupeAbsence);
		}
		
		return PATH_ABSENCES_AGENTS + getPathAgent(idAgent, nom, prenom) + folderTypeAbsence + SLASH;
	}
	
	/**
	 * Retourne le chemin dans Alfresco pour les etats du payeur
	 * soit pointage soit titre repas
	 * 
	 * @param type TypeEtatPayeurPointageEnum
	 * @return String
	 */
	public static String getPathPointage(TypeEtatPayeurPointageEnum type) {
		
		String folderTypePointage = TypeEtatPayeurPointageEnum.getPathAlfrescoByType(type);
		
		return PATH_ETATS_PAYEUR + folderTypePointage;
	}
	
	/**
	 * Utile a SIRH
	 * Retourne le chemin pour l ajout d un document dans SIRH
	 * 
	 * @param idAgent Integer ID de l agent
	 * @param nom String nom de l agent
	 * @param prenom String prenom de l agent
	 * @param path du type de document (parametre dans la table SIRH.P_PATH_ALFRESCO)
	 * @return String retourne le chemin dans le site SIRH pour le document a ajouter
	 */
	public static String getPathSIRH(Integer idAgent, String nom, String prenom, String pathDocument) {
		
		if(null == idAgent) {
			return PATH_SIRH + pathDocument;
		}
		
		return PATH_ABSENCES_AGENTS + getPathAgent(idAgent, nom, prenom) + SLASH + pathDocument;
	}
	
	/**
	 * Retourne le nom pour un document ajoute depuis SIRH
	 * 
	 * @param typeDemande String Type d absence
	 * @param nom String nom agent
	 * @param prenom String prenom de l agent
	 * @param date Date de debut de la demande
	 * @param sequence Integer sequence si boucle
	 * @return String le nom du document
	 */
	public static String getPatternSIRH(String typeDemande, String nom, String prenom, Integer idAgent, Date date, Integer sequence) { 

		String nomCustom = "";
		String prenomCustom = "";
		
		if(nom.length() + prenom.length() > NOMBRE_CARACTERES_MAX) {
			if(nom.length() > NOMBRE_CARACTERES_MAX) {
				nomCustom = nom.substring(0, NOMBRE_CARACTERES_MAX);
			}else{
				nomCustom = nom;
				prenomCustom = prenom.substring(0, NOMBRE_CARACTERES_MAX-nom.length());
			}
		}else{
			nomCustom = nom;
			prenomCustom = prenom;
		}
		
		String result = typeDemande.toUpperCase() + UNDERSCORE + nomCustom.trim().toUpperCase() 
				+ (prenomCustom.trim().equals("") ? "" : UNDERSCORE + prenomCustom.trim().toUpperCase()) + UNDERSCORE 
				+ idAgent + UNDERSCORE
				+ sdfyyyyMMdd.format(date) + UNDERSCORE + (null != sequence ? sequence : "");
		result = result.replace(" ", UNDERSCORE);
		return result;
	}
}
