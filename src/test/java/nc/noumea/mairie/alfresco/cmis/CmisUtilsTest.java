package nc.noumea.mairie.alfresco.cmis;

import static org.junit.Assert.*;

import org.junit.Test;

public class CmisUtilsTest {

	@Test 
	public void getUrlOfDocument_ok() {
		
		String alfrescoUrl = "localhost:8080/";
		String nodeRef = "workspace://SpacesStore/951fea05-6c6f-483d-b7ed-aae492036a86";
		
		assertEquals(CmisUtils.getUrlOfDocument(alfrescoUrl, nodeRef), "localhost:8080/alfresco/service/api/node/workspace/SpacesStore/951fea05-6c6f-483d-b7ed-aae492036a86/content");
	}
	
	@Test 
	public void getUrlOfDocument_nodeRef_Null() {
		
		String alfrescoUrl = "localhost:8080/";
		String nodeRef = null;
		
		assertNull(CmisUtils.getUrlOfDocument(alfrescoUrl, nodeRef));
		
		CmisUtils n = new CmisUtils();
		assertNotNull(n);
	}
}
