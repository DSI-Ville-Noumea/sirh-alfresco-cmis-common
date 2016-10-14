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

		assertEquals(CmisUtils.getUrlOfDocument(alfrescoUrl, nodeRef),
				"localhost:8080/alfresco/service/api/node/workspace/SpacesStore/951fea05-6c6f-483d-b7ed-aae492036a86/content");
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
		Date date = new DateTime(2016, 3, 21, 0, 0, 0).toDate();
		Integer sequence = 1;

		assertEquals(CmisUtils.getPatternAbsence(typeDemande, nom, prenom, date, sequence), "ABS_CA_CHARVET_TATIANA_20160321_1");
	}

	@Test
	public void getPatternAbsence_nomEtPrenomSuperieurA25caracteres() {

		String typeDemande = "CA";
		String nom = "ANGLIO NATAUTAVA VENASIO";
		String prenom = "NIKOLA";
		Date date = new DateTime(2016, 3, 21, 0, 0, 0).toDate();
		Integer sequence = 1;

		assertEquals(CmisUtils.getPatternAbsence(typeDemande, nom, prenom, date, sequence), "ABS_CA_ANGLIO_NATAUTAVA_VENASIO_N_20160321_1");
	}

	@Test
	public void getPatternAbsence_nomSuperieurA25caracteres() {

		String typeDemande = "CA";
		String nom = "ANGLIO NATAUTAVA VENASIO DE TEST";
		String prenom = "NIKOLA";
		Date date = new DateTime(2016, 3, 21, 0, 0, 0).toDate();
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

	@Test
	public void getPathSIRH() {
		assertEquals("/Sites/SIRH/documentLibrary/Agents/TATIANA_CHARVET_9005138/Test_Path/",
				CmisUtils.getPathSIRH(9005138, "CHARVET", "TATIANA", "Test_Path/"));
	}

	@Test
	public void getPathSIRH_IdAgentNull() {
		assertEquals("/Sites/SIRH/documentLibrary/Test_Path/", CmisUtils.getPathSIRH(null, "CHARVET", "TATIANA", "Test_Path/"));
	}

	@Test
	public void getPatternSIRH_nomEtPrenomInferieurA25caracteres() {

		String typeDemande = "AT";
		String nom = "CHARVET";
		String prenom = "TATIANA";
		Integer idAgent = 9005138;
		Date date = new DateTime(2016, 3, 21, 0, 0, 0).toDate();
		Integer sequence = 1;

		assertEquals(CmisUtils.getPatternSIRH(typeDemande, nom, prenom, idAgent, date, sequence, null), "AT_CHARVET_TATIANA_9005138_20160321_1");
	}

	@Test
	public void getPatternSIRH_nomEtPrenomSuperieurA25caracteres() {

		String typeDemande = "ADM";
		String nom = "ANGLIO NATAUTAVA VENASIO";
		String prenom = "NIKOLA";
		Integer idAgent = 9005138;
		Date date = new DateTime(2016, 3, 21, 0, 0, 0).toDate();
		Integer sequence = 1;

		assertEquals(CmisUtils.getPatternSIRH(typeDemande, nom, prenom, idAgent, date, sequence, null),
				"ADM_ANGLIO_NATAUTAVA_VENASIO_N_9005138_20160321_1");
	}

	@Test
	public void getPatternSIRH_nomSuperieurA25caracteres() {

		String typeDemande = "DIP";
		String nom = "ANGLIO NATAUTAVA VENASIO DE TEST";
		String prenom = "NIKOLA";
		Integer idAgent = 9005138;
		Date date = new DateTime(2016, 3, 21, 0, 0, 0).toDate();
		Integer sequence = 1;

		assertEquals(CmisUtils.getPatternSIRH(typeDemande, nom, prenom, idAgent, date, sequence, null),
				"DIP_ANGLIO_NATAUTAVA_VENASIO_9005138_20160321_1");
	}

	@Test
	public void getPatternSIRH_typeEAE() {

		String typeDemande = CmisUtils.CODE_TYPE_EAE;
		String nom = "ANGLIO NATAUTAVA VENASIO DE TEST";
		String prenom = "NIKOLA";
		Integer idAgent = 9005138;
		Date date = new DateTime(2016, 3, 21, 0, 0, 0).toDate();
		Integer sequence = 1;
		Integer annee = 2016;

		assertEquals(CmisUtils.getPatternSIRH(typeDemande, nom, prenom, idAgent, date, sequence, annee),
				"EAE_2016_ANGLIO_NATAUTAVA_VENASIO_9005138");
	}

	@Test
	public void getPatternSIRH_typeACT_or_CAMP() {

		String typeDemande = CmisUtils.CODE_TYPE_ACT;
		String nom = "ANGLIO NATAUTAVA VENASIO DE TEST";
		String prenom = "NIKOLA";
		Integer idAgent = 9005138;
		Date date = new DateTime(2016, 3, 21, 0, 0, 0).toDate();
		Integer sequence = 1;
		Integer annee = 2016;

		assertEquals(CmisUtils.getPatternSIRH(typeDemande, nom, prenom, idAgent, date, sequence, annee),
				"ACT_2016_20160321_1");
	}
	
	@Test
	public void getPathEAE() {
		
		Integer idAgent = 9005138;
		String nom = "CHARVET";
		String prenom = "TATIANA";
		
		assertEquals(CmisUtils.getPathEAE(idAgent, nom, prenom),
				"/Sites/SIRH/documentLibrary/Agents/TATIANA_CHARVET_9005138/Carrière/EAE/");
	}
	
	@Test
	public void getPatternEAE() {
		
		Integer idAgent = 9005138;
		String annee = "2016";
		int i = 0;
		
		assertEquals(CmisUtils.getPatternEAE(idAgent, annee, i), "EAE_2016_9005138");
		
		assertEquals(CmisUtils.getPatternEAE(idAgent, annee, i+1), "EAE_2016_9005138_1");
	}
	
	@Test 
	public void getGroupeSHDOfAgent() {
		
		Integer idAgent = 9005131;
		String nom = "SALES";
		String prenom = "ADRIEN";
		
		assertEquals(CmisUtils.getGroupeSHDOfAgent(idAgent, nom, prenom), "GROUP_SITE_SIRH_ADRIEN_SALES_9005131_SHD");
	}
}
