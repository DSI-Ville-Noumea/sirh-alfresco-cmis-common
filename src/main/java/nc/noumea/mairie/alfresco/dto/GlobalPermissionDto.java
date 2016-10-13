package nc.noumea.mairie.alfresco.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GlobalPermissionDto implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8774675977112467690L;
	
	private List<PermissionDto> permissions;
	private boolean isInherited;
	
	public GlobalPermissionDto() {
		super();
		this.permissions = new ArrayList<PermissionDto>();
	}
	
	public GlobalPermissionDto(List<PermissionDto> listPermissionDto, boolean isInherited) {
		this();
		this.permissions = listPermissionDto;
		this.isInherited = isInherited;
	}
	
	
	public List<PermissionDto> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDto> permissions) {
		this.permissions = permissions;
	}

	public boolean isIsInherited() {
		return isInherited;
	}
	
	public void setIsInherited(boolean isInherited) {
		this.isInherited = isInherited;
	}
	
}
