package nc.noumea.mairie.alfresco.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GlobalPermissionDtoTest {

	@Test
	public void constructor() {
		
		PermissionDto permissionDto = new PermissionDto("authority", "role", true);
		
		List<PermissionDto> listPermissionDto = new ArrayList<PermissionDto>();
		listPermissionDto.add(permissionDto);
		
		GlobalPermissionDto dto = new GlobalPermissionDto(listPermissionDto, true);
		
		assertTrue(dto.isIsInherited());
		assertEquals(dto.getPermissions().size(), listPermissionDto.size());
	}
	
}
