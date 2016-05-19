package nc.noumea.mairie.ptg;

public enum TypeEtatPayeurPointageEnum {

	TYPE_ETAT_PAYEUR_POINTAGE("Etats Payeur"),
	TYPE_ETAT_PAYEUR_TITRE_REPAS("Titres Repas"),
	UNKNOW("unkown");
	
	private String type;

	private TypeEtatPayeurPointageEnum(String _type) {
		type = _type;
	}

	public String getValue() {
		return type;
	}
	
	public static String getPathAlfrescoByType(TypeEtatPayeurPointageEnum type) {
		
		if (type == null)
			return null;

		switch (type) {
			case TYPE_ETAT_PAYEUR_POINTAGE:
				return "Etats Payeur/";
			case TYPE_ETAT_PAYEUR_TITRE_REPAS:
				return "Titres Repas/";
			default:
				throw new IllegalArgumentException();
		}
	}
}
