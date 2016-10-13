package nc.noumea.mairie.alfresco.ws;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nc.noumea.mairie.alfresco.dto.GlobalPermissionDto;
import nc.noumea.mairie.alfresco.dto.PermissionDto;

public class AlfrescoWsConsumerTest {

	AlfrescoWsConsumer alfrescoWsConsumer = new AlfrescoWsConsumer();

	@Test
	public void constructor() {

		AlfrescoWsConsumer dto = new AlfrescoWsConsumer("url", "login", "mdp");

		assertEquals(dto.getAlfrescoUrl(), "url");
		assertEquals(dto.getAlfrescoLogin(), "login");
		assertEquals(dto.getAlfrescoPassword(), "mdp");
	}

	@Test
	public void testConnectionSetPermissionToAlfresco_NoNodeRef() {

		PermissionDto permission = new PermissionDto("GROUP_SITE_SIRH_ADRIEN_SALES_9005131_SHD", "Consumer", true);

		List<PermissionDto> permissions = new ArrayList<PermissionDto>();
		permissions.add(permission);

		GlobalPermissionDto dto = new GlobalPermissionDto();
		dto.setIsInherited(false);
		dto.setPermissions(permissions);

		String exMessage = "";
		try {
			alfrescoWsConsumer.setPermissionsNode(null, dto);
		} catch (WSConsumerException ex) {
			exMessage = ex.getMessage();
		}

		// Then
		assertEquals("L'URL est null car le nodeRef est null", exMessage);
	}

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
