package nc.noumea.mairie.alfresco.dto;

import java.io.Serializable;

public class PermissionDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5402623001949425500L;
	
	private String authority;
	private String role;
	private boolean remove;
	
	public PermissionDto(String authority, String role, boolean remove) {
		super();
		this.authority = authority;
		this.role = role;
		this.remove = remove;
	}
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	
}
