package nc.noumea.mairie.alfresco.cmis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileUtils.class)
public class CmisServiceTest {

	@Test
	public void getFile_ok() {
		
		Session session = Mockito.mock(Session.class);
		String nodeRef = "";
		
		Document doc = Mockito.mock(Document.class);
		
		Mockito.when(session.getObject(nodeRef)).thenReturn(doc);
		Mockito.when(doc.getName()).thenReturn("PieceJointe");
		
		ContentStream cs = Mockito.mock(ContentStream.class);
		Mockito.when(doc.getContentStream()).thenReturn(cs);

		PowerMockito.mockStatic(FileUtils.class);
		
		PowerMockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws IOException {
				InputStream is = Mockito.mock(InputStream.class);
				is.read(new String("data").getBytes());
				return is;
			}
		}).when(cs).getStream();
		
		CmisService service = new CmisService();
		
		File file = service.getFile(session, nodeRef);
		
		assertNotNull(file);
		assertEquals(file.getName(), "PieceJointe");
	}

	@Test
	public void getFile_IOException() throws IOException {
		
		Session session = Mockito.mock(Session.class);
		String nodeRef = "";
		
		Document doc = Mockito.mock(Document.class);
		
		Mockito.when(session.getObject(nodeRef)).thenReturn(doc);
		Mockito.when(doc.getName()).thenReturn("PieceJointe");
		
		ContentStream cs = Mockito.mock(ContentStream.class);
		Mockito.when(doc.getContentStream()).thenReturn(cs);

		PowerMockito.mockStatic(FileUtils.class);
		
		PowerMockito.doThrow(new IOException()).when(FileUtils.class);
		FileUtils.copyInputStreamToFile(Mockito.any(InputStream.class), Mockito.any(File.class));
		
		PowerMockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws IOException {
				InputStream is = Mockito.mock(InputStream.class);
				is.read(new String("data").getBytes());
				return is;
			}
		}).when(cs).getStream();
		
		CmisService service = new CmisService();
		
		File file = service.getFile(session, nodeRef);
		
		assertNull(file);
	}
	
	@Test
	public void getIdObjectCmis_ok() {
		
		Session session = Mockito.mock(Session.class);
		String path = "path/to/file";
		
		CmisObject object = Mockito.mock(CmisObject.class);
		Mockito.when(object.getId()).thenReturn("idObject");
		
		Mockito.when(session.getObjectByPath(path)).thenReturn(object);
		
		CmisService service = new CmisService();
		String result = service.getIdObjectCmis(path, session);
		
		assertEquals(result, "idObject");
	}
	
	@Test
	public void getIdObjectCmis_ko() {
		
		Session session = Mockito.mock(Session.class);
		String path = "path/to/file";
		
		Mockito.when(session.getObjectByPath(path)).thenReturn(null);
		
		CmisService service = new CmisService();
		String result = service.getIdObjectCmis(path, session);
		
		assertNull(result);
	}
}
