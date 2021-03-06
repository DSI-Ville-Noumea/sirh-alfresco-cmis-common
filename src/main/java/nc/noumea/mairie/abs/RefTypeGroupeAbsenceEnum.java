package nc.noumea.mairie.abs;

public enum RefTypeGroupeAbsenceEnum {

	RECUP(1), REPOS_COMP(2), AS(3), CONGES_EXCEP(4), CONGES_ANNUELS(5), MALADIES(6), NOT_EXIST(99);

	private int type;

	private RefTypeGroupeAbsenceEnum(int _type) {
		type = _type;
	}

	public int getValue() {
		return type;
	}

	public static RefTypeGroupeAbsenceEnum getRefTypeGroupeAbsenceEnum(Integer type) {

		if (type == null)
			return null;

		switch (type) {
			case 1:
				return RECUP;
			case 2:
				return REPOS_COMP;
			case 3:
				return AS;
			case 4:
				return CONGES_EXCEP;
			case 5:
				return CONGES_ANNUELS;
			case 6:
				return MALADIES;
			default:
				return NOT_EXIST;
		}
	}
	
	public static String getPathAlfrescoByType(Integer type) {
		
		if (type == null)
			return null;

		switch (type) {
			case 1:
				return "";
			case 2:
				return "";
			case 3:
				return "Absences Syndicales";
			case 4:
				return "Congés Exceptionnels";
			case 5:
				return "Congés annuels";
			case 6:
				return "Maladies";
			default:
				return null;
		}
	}
}
