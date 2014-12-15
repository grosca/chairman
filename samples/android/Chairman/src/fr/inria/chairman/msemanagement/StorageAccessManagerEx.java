package fr.inria.chairman.msemanagement;

import java.util.Set;

import fr.inria.arles.yarta.knowledgebase.KBException;
import fr.inria.arles.yarta.knowledgebase.MSEResource;
import fr.inria.arles.yarta.middleware.msemanagement.StorageAccessManager;
import fr.inria.chairman.resources.ContentImpl;
import fr.inria.chairman.resources.PersonImpl;
import fr.inria.arles.yarta.resources.Content;
import fr.inria.arles.yarta.resources.Person;

/**
 * StorageAccessManager class extension.
 */
public class StorageAccessManagerEx extends StorageAccessManager {

	/**
	 * Basic constructor. Binds interfaces to implementation.
	 */
	public StorageAccessManagerEx() {
		super();
		bindInterfacetoImplementation(
				fr.inria.arles.yarta.resources.Person.typeURI,
				"fr.inria.chairman.resources.PersonImpl");
		bindInterfacetoImplementation(Person.typeURI,
				"fr.inria.chairman.resources.PersonImpl");

		bindInterfacetoImplementation(
				fr.inria.arles.yarta.resources.Content.typeURI,
				"fr.inria.chairman.resources.ContentImpl");
		bindInterfacetoImplementation(Content.typeURI,
				"fr.inria.chairman.resources.ContentImpl");
	}

	public fr.inria.chairman.resources.Person getMe() throws KBException {
		fr.inria.chairman.resources.Person person = new fr.inria.chairman.resources.PersonImpl(
				this, new MSEResource(super.getMe().getUniqueId(),
						fr.inria.chairman.resources.Person.typeURI));
		return person;
	}

	/**
	 * Creates a new instance of Person
	 */
	public Person createPerson(String uniqueId) {
		return (Person) new PersonImpl(this, uniqueId);
	}

	/**
	 * Returns all instances of type Person
	 */
	public Set<Person> getAllPersons() {
		return getAllResourcesOfType(getPropertyNode(Person.typeURI));
	}

	/**
	 * Creates and return a new instance of the Content Interface. The URI is
	 * auto-generated
	 * 
	 * @return
	 * @return New Content. Null if something goes wrong.
	 */
	public Content createContent() {
		return (Content) new ContentImpl(this, createNewNode(Content.typeURI));
	}
}
