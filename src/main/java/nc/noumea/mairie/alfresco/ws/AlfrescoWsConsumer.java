package nc.noumea.mairie.alfresco.ws;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nc.noumea.mairie.alfresco.cmis.CmisUtils;
import nc.noumea.mairie.alfresco.dto.GlobalPermissionDto;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flexjson.JSONSerializer;

@Service
public class AlfrescoWsConsumer extends BaseWsConsumer implements IAlfrescoWsConsumer {

	Logger logger = LoggerFactory.getLogger(AlfrescoWsConsumer.class);
	
	public AlfrescoWsConsumer() {
		
	}
	
	@Autowired
	public AlfrescoWsConsumer(String alfrescoUrl, String alfrescoLogin, String alfrescoPassword) {
		this.alfrescoUrl = alfrescoUrl;
		this.alfrescoLogin = alfrescoLogin;
		this.alfrescoPassword = alfrescoPassword;
	}
	
	private String alfrescoUrl;
	private String alfrescoLogin;
	private String alfrescoPassword;
	
	private final static String URL_WS_SET_PERMISSION = "alfresco/s/slingshot/doclib/permissions";
	
	public void setPermissionsNode(String nodeRef, GlobalPermissionDto dto) {
		
		String url = getUrl(nodeRef);
		
		Map<String, String> params = new HashMap<String, String>();

		String json = new JSONSerializer().exclude("*.class").deepSerialize(dto);

		//TODO faire l authentification
		
//		ClientResponse res = createAndFirePostRequest(params, url, json, new HTTPBasicAuthFilter(alfrescoLogin, alfrescoPassword));
//		readResponse(res, url);
		
		HttpClient client = new HttpClient();
		
		Credentials defaultcreds = new UsernamePasswordCredentials(alfrescoLogin, alfrescoPassword);
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		PostMethod mPost = new PostMethod(url);
		
		mPost.setRequestHeader("Content-Type", "application/json");
		try {
			mPost.setRequestEntity(new StringRequestEntity(json, "application/json", "UTF-8"));
		
		
//		logger.debug("addUser URL : " + url + " ; JSON : " + objUserDetails.toString()); 
		
			int result = client.executeMethod(mPost);
			
			logger.debug("result", result);
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getUrl(String nodeRef) {
		
		if(null == nodeRef) {
			return null;
		}
		
		String result = alfrescoUrl + URL_WS_SET_PERMISSION;
		
		String[] properties = nodeRef.split(CmisUtils.SLASH);
		
		for(int i=0; i<properties.length; i++) {
			if( "".equals(properties[i].trim()))
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
