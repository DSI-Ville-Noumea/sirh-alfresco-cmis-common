package nc.noumea.mairie.alfresco.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PermissionDtoTest {
	
	@Test
	public void constructor() {
		
		PermissionDto permissionDto = new PermissionDto("authority", "role", true);
		
		assertEquals(permissionDto.getAuthority(), "authority");
		assertEquals(permissionDto.getRole(), "role");
		assertTrue(permissionDto.isRemove());
	}
	
	@Test
	public void constructor_WithSetter() {
		
		PermissionDto permissionDto = new PermissionDto();
		permissionDto.setAuthority("authority");
		permissionDto.setRemove(true);
		permissionDto.setRole("role");
		
		assertEquals(permissionDto.getAuthority(), "authority");
		assertEquals(permissionDto.getRole(), "role");
		assertTrue(permissionDto.isRemove());
	}
	
}
