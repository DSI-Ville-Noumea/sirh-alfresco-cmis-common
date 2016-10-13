package nc.noumea.mairie.alfresco.ws;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.alfresco.dto.GlobalPermissionDto;
import nc.noumea.mairie.alfresco.dto.PermissionDto;

import org.junit.Test;

public class AlfrescoWsConsumerTest {

	AlfrescoWsConsumer alfrescoWsConsumer = new AlfrescoWsConsumer();
	
	@Test
	public void testConnectionSetPermissionToAlfresco() {
		
		alfrescoWsConsumer.setAlfrescoUrl("http://svi-alfresco.site-mairie.noumea.nc:8080/");
		alfrescoWsConsumer.setAlfrescoLogin("admin");
		alfrescoWsConsumer.setAlfrescoPassword("8tu1vi04");
		
		PermissionDto permission = new PermissionDto("GROUP_SITE_SIRH_ADRIEN_SALES_9005131_SHD", "Consumer", true);
		
		List<PermissionDto> permissions = new ArrayList<PermissionDto>();
		permissions.add(permission);
		
		
		GlobalPermissionDto dto = new GlobalPermissionDto();
		dto.setIsInherited(false);
		dto.setPermissions(permissions);
		
		alfrescoWsConsumer.setPermissionsNode("workspace://SpacesStore/7d4fa4ec-a0f6-4ee0-a0f7-07ea62747575", dto);
	}
}
