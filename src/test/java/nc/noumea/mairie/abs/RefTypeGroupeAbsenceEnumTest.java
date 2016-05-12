package nc.noumea.mairie.abs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class RefTypeGroupeAbsenceEnumTest {

	@Test
	public void getRefTypeGroupeAbsenceEnum() {

		assertEquals(RefTypeGroupeAbsenceEnum.RECUP, RefTypeGroupeAbsenceEnum.getRefTypeGroupeAbsenceEnum(1));
		assertEquals(RefTypeGroupeAbsenceEnum.REPOS_COMP, RefTypeGroupeAbsenceEnum.getRefTypeGroupeAbsenceEnum(2));
		assertEquals(RefTypeGroupeAbsenceEnum.AS, RefTypeGroupeAbsenceEnum.getRefTypeGroupeAbsenceEnum(3));
		assertEquals(RefTypeGroupeAbsenceEnum.CONGES_EXCEP, RefTypeGroupeAbsenceEnum.getRefTypeGroupeAbsenceEnum(4));
		assertEquals(RefTypeGroupeAbsenceEnum.CONGES_ANNUELS, RefTypeGroupeAbsenceEnum.getRefTypeGroupeAbsenceEnum(5));
		assertEquals(RefTypeGroupeAbsenceEnum.MALADIES, RefTypeGroupeAbsenceEnum.getRefTypeGroupeAbsenceEnum(6));
		assertNull(RefTypeGroupeAbsenceEnum.getRefTypeGroupeAbsenceEnum(null));
		assertEquals(RefTypeGroupeAbsenceEnum.NOT_EXIST, RefTypeGroupeAbsenceEnum.getRefTypeGroupeAbsenceEnum(99));
	}
	
	@Test
	public void getPathAlfrescoByType() {
		
		assertEquals("", RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(1));
		assertEquals("", RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(2));
		assertEquals("Absences Syndicales", RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(3));
		assertEquals("Congés Exceptionnels", RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(4));
		assertEquals("Congés annuels", RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(5));
		assertEquals("Maladies", RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(6));
		assertNull(RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(null));
		assertNull(RefTypeGroupeAbsenceEnum.getPathAlfrescoByType(99));
	}
}
