package nc.noumea.mairie.alfresco.ws;

import nc.noumea.mairie.alfresco.dto.GlobalPermissionDto;

public interface IAlfrescoWsConsumer {

	void setPermissionsNode(String nodeRef, GlobalPermissionDto dto);

}
