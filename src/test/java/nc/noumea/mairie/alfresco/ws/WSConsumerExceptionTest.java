package nc.noumea.mairie.alfresco.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.rmi.AccessException;

import org.junit.Test;

public class WSConsumerExceptionTest {

	@Test
	public void constructor_vide() {

		WSConsumerException dto = new WSConsumerException();

		assertNotNull(dto);
	}

	@Test
	public void constructor() {

		WSConsumerException dto = new WSConsumerException("message");

		assertEquals(dto.getMessage(), "message");
	}

	@Test
	public void constructor_WithInnerException() {
		Exception ex = new AccessException("bla");

		WSConsumerException dto = new WSConsumerException("message", ex);

		assertEquals(dto.getMessage(), "message");
		assertEquals(dto.getCause(), ex);
	}

}
