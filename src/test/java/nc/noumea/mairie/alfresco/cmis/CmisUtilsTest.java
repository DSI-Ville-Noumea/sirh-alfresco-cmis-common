package nc.noumea.mairie.alfresco.cmis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.joda.time.DateTime;
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
	
	@Test
	public void getPatternAbsence_nomEtPrenomInferieurA25caracteres() {
		
		String typeDemande = "CA";
		String nom = "CHARVET"; 
		String prenom = "TATIANA"; 
		Date date = new DateTime(2016,3,21,0,0,0).toDate();
		Integer sequence = 1;
		
		assertEquals(CmisUtils.getPatternAbsence(typeDemande, nom, prenom, date, sequence), "ABS_CA_CHARVET_TATIANA_20160321_1");
	}
	
	@Test
	public void getPatternAbsence_nomEtPrenomSuperieurA25caracteres() {
		
		String typeDemande = "CA";
		String nom = "ANGLIO NATAUTAVA VENASIO"; 
		String prenom = "NIKOLA"; 
		Date date = new DateTime(2016,3,21,0,0,0).toDate();
		Integer sequence = 1;
		
		assertEquals(CmisUtils.getPatternAbsence(typeDemande, nom, prenom, date, sequence), "ABS_CA_ANGLIO_NATAUTAVA_VENASIO_N_20160321_1");
	}
	
	@Test
	public void getPatternAbsence_nomSuperieurA25caracteres() {
		
		String typeDemande = "CA";
		String nom = "ANGLIO NATAUTAVA VENASIO DE TEST"; 
		String prenom = "NIKOLA"; 
		Date date = new DateTime(2016,3,21,0,0,0).toDate();
		Integer sequence = 1;
		
		assertEquals(CmisUtils.getPatternAbsence(typeDemande, nom, prenom, date, sequence), "ABS_CA_ANGLIO_NATAUTAVA_VENASIO_20160321_1");
	}
}
