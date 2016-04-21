package nc.noumea.mairie.alfresco.cmis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import nc.noumea.mairie.ptg.TypeEtatPayeurPointageEnum;

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
	
	@Test
	public void getPathAgent() {
		assertEquals("TATIANA_CHARVET_9005138", CmisUtils.getPathAgent(9005138, "CHARVET", "TATIANA"));
	}
	
	@Test
	public void getPathAgent_returnNull() {
		assertNull(CmisUtils.getPathAgent(null, "CHARVET", "TATIANA"));
		assertNull(CmisUtils.getPathAgent(9005138, null, "TATIANA"));
		assertNull(CmisUtils.getPathAgent(9005138, "CHARVET", null));
	}
	
	@Test
	public void getPathAbsence() {
		assertEquals("/Sites/SIRH/documentLibrary/Agents/TATIANA_CHARVET_9005138/Absences/Absences Syndicales/", 
				CmisUtils.getPathAbsence(9005138, "CHARVET", "TATIANA", 3, false));
	}
	
	@Test
	public void getPathAbsence_HSCT() {
		assertEquals("/Sites/SIRH/documentLibrary/Agents/TATIANA_CHARVET_9005138/Médical/AT MP/", 
				CmisUtils.getPathAbsence(9005138, "CHARVET", "TATIANA", 6, true));
	}
	
	@Test
	public void getPathPointage_EtatPayeur() {
		assertEquals("/Sites/SIRH/documentLibrary/Pointages/Etats Payeur/", 
				CmisUtils.getPathPointage(TypeEtatPayeurPointageEnum.TYPE_ETAT_PAYEUR_POINTAGE));
	}
	
	@Test
	public void getPathPointage_titreRepas() {
		assertEquals("/Sites/SIRH/documentLibrary/Pointages/Titres Repas/", 
				CmisUtils.getPathPointage(TypeEtatPayeurPointageEnum.TYPE_ETAT_PAYEUR_TITRE_REPAS));
	}
}
