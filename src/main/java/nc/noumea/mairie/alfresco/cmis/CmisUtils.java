package nc.noumea.mairie.alfresco.cmis;

public final class CmisUtils {


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
}
