package nc.noumea.mairie.alfresco.ws;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flexjson.JSONSerializer;
import nc.noumea.mairie.alfresco.cmis.CmisUtils;
import nc.noumea.mairie.alfresco.dto.GlobalPermissionDto;

@Service
public class AlfrescoWsConsumer implements IAlfrescoWsConsumer {

	Logger						logger					= LoggerFactory.getLogger(AlfrescoWsConsumer.class);

	private String				alfrescoUrl;
	private String				alfrescoLogin;
	private String				alfrescoPassword;

	private final static String	URL_WS_SET_PERMISSION	= "alfresco/s/slingshot/doclib/permissions";

	public AlfrescoWsConsumer() {

	}

	@Autowired
	public AlfrescoWsConsumer(String alfrescoUrl, String alfrescoLogin, String alfrescoPassword) {
		this.alfrescoUrl = alfrescoUrl;
		this.alfrescoLogin = alfrescoLogin;
		this.alfrescoPassword = alfrescoPassword;
	}

	public void setPermissionsNode(String nodeRef, GlobalPermissionDto dto) {

		String url = getUrl(nodeRef);
		if (url == null) {
			throw new WSConsumerException("L'URL est null car le nodeRef est null");
		}

		String json = new JSONSerializer().exclude("*.class").deepSerialize(dto);

		HttpClient client = new HttpClient();

		Credentials defaultcreds = new UsernamePasswordCredentials(alfrescoLogin, alfrescoPassword);
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		PostMethod mPost = new PostMethod(url);

		mPost.setRequestHeader("Content-Type", "application/json");
		String messageErreur = "Erreur dans Alfresco pour la gestion des droits EAE.";
		try {
			mPost.setRequestEntity(new StringRequestEntity(json, "application/json", "UTF-8"));

			int result = client.executeMethod(mPost);

			logger.debug("result", result);
			if (result != HttpStatus.SC_OK) {
				logger.error(messageErreur);
				throw new WSConsumerException(messageErreur);
			}
		} catch (HttpException e) {
			logger.error(messageErreur + e.getMessage());
			throw new WSConsumerException(messageErreur + e.getMessage());
		} catch (IOException e) {
			logger.error(messageErreur + e.getMessage());
			throw new WSConsumerException(messageErreur + e.getMessage());
		}
	}

	private String getUrl(String nodeRef) {

		if (null == nodeRef) {
			return null;
		}

		String result = alfrescoUrl + URL_WS_SET_PERMISSION;

		String[] properties = nodeRef.split(CmisUtils.SLASH);

		for (int i = 0; i < properties.length; i++) {
			if ("".equals(properties[i].trim()))
				continue;

			result += CmisUtils.SLASH + properties[i].replace(":", "");
		}

		return result;
	}

	public String getAlfrescoUrl() {
		return alfrescoUrl;
	}

	public void setAlfrescoUrl(String alfrescoUrl) {
		this.alfrescoUrl = alfrescoUrl;
	}

	public String getAlfrescoLogin() {
		return alfrescoLogin;
	}

	public void setAlfrescoLogin(String alfrescoLogin) {
		this.alfrescoLogin = alfrescoLogin;
	}

	public String getAlfrescoPassword() {
		return alfrescoPassword;
	}

	public void setAlfrescoPassword(String alfrescoPassword) {
		this.alfrescoPassword = alfrescoPassword;
	}

}
