package nc.noumea.mairie.alfresco.cmis;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class CmisUtils {

	private static final String UNDERSCORE = "_";
	private static final Integer NOMBRE_CARACTERES_MAX = 25;
	
	private static SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyyMMdd");

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
		
		String[] properties = nodeRef.split("/");
		
		String result = alfrescoUrl + "alfresco/service/api/node/";
		
		for(int i=0; i<properties.length; i++) {
			if( "".equals(properties[i].trim()))
				continue;
			
			result += properties[i].replace(":", "") + "/";
		}
		result += "content";
		
		return result;
	}
	
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
}
