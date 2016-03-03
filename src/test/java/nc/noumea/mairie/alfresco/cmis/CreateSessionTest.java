package nc.noumea.mairie.alfresco.cmis;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SessionFactoryImpl.class)
public class CreateSessionTest {
	
	private String alfrescoUrl = "alfrescoUrl";
	private String alfrescoLogin = "alfrescoLogin";
	private String alfrescoPassword = "alfrescoPassword";
	
	@SuppressWarnings("unchecked")
	@Test
	public void getSession() throws Exception {

		PowerMockito.mockStatic(SessionFactoryImpl.class);
		SessionFactoryImpl sessionFactory = PowerMockito.mock(SessionFactoryImpl.class);

		PowerMockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				
				HashMap<String, String> parameters = (HashMap<String, String>) args[0];
				assertEquals(parameters.get(SessionParameter.USER), alfrescoLogin);
				assertEquals(parameters.get(SessionParameter.PASSWORD), alfrescoPassword);
				assertEquals(parameters.get(SessionParameter.BINDING_TYPE), BindingType.ATOMPUB.value());
				assertEquals(parameters.get(SessionParameter.ATOMPUB_URL), alfrescoUrl+"alfresco/api/-default-/cmis/versions/1.1/atom");
				assertEquals(parameters.get(SessionParameter.REPOSITORY_ID), "-default-");
				
				Repository repo = PowerMockito.mock(Repository.class);
				List<Repository> listRepository = new ArrayList<Repository>();
				listRepository.add(repo);
				Session session = PowerMockito.mock(Session.class);
				PowerMockito.when(repo.createSession()).thenReturn(session);
				
				return listRepository;
			}
		}).when(sessionFactory).getRepositories(Mockito.any(HashMap.class));
		
		PowerMockito.when(SessionFactoryImpl.newInstance()).thenReturn(sessionFactory);

		CreateSession createSession = new CreateSession();
		
		Session result = createSession.getSession(alfrescoUrl, alfrescoLogin, alfrescoPassword);
		
		assertNotNull(result);
	}
	
}
