package nc.noumea.mairie.ptg;

import static org.junit.Assert.*;

import org.junit.Test;

public class TypeEtatPayeurPointageEnumTest {

	@Test
	public void getRefTypeGroupeAbsenceEnum() {
		
		assertEquals("Etats Payeur/", TypeEtatPayeurPointageEnum.getPathAlfrescoByType(TypeEtatPayeurPointageEnum.TYPE_ETAT_PAYEUR_POINTAGE));
		assertEquals("Titres Repas/", TypeEtatPayeurPointageEnum.getPathAlfrescoByType(TypeEtatPayeurPointageEnum.TYPE_ETAT_PAYEUR_TITRE_REPAS));
	}
}
