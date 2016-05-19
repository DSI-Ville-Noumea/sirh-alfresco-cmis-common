package nc.noumea.mairie.ptg;

import static org.junit.Assert.*;

import org.junit.Test;

public class TypeEtatPayeurPointageEnumTest {

	@Test
	public void getRefTypeGroupeAbsenceEnum() {
		
		assertEquals("Etats Payeur/", TypeEtatPayeurPointageEnum.getPathAlfrescoByType(TypeEtatPayeurPointageEnum.TYPE_ETAT_PAYEUR_POINTAGE));
		assertEquals("Titres Repas/", TypeEtatPayeurPointageEnum.getPathAlfrescoByType(TypeEtatPayeurPointageEnum.TYPE_ETAT_PAYEUR_TITRE_REPAS));
		assertNull(TypeEtatPayeurPointageEnum.getPathAlfrescoByType(null));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getRefTypeGroupeAbsenceEnum_Exception() {
		assertNull(TypeEtatPayeurPointageEnum.getPathAlfrescoByType(TypeEtatPayeurPointageEnum.UNKNOW));
	}
	
	@Test
	public void getValue() {
		
		assertEquals(TypeEtatPayeurPointageEnum.TYPE_ETAT_PAYEUR_POINTAGE.getValue(), "Etats Payeur");
		assertEquals(TypeEtatPayeurPointageEnum.TYPE_ETAT_PAYEUR_TITRE_REPAS.getValue(), "Titres Repas");
	}
}
