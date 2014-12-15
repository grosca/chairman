package fr.inria.chairman.resources;

import java.util.Set;

import fr.inria.arles.yarta.knowledgebase.interfaces.Node;
import fr.inria.arles.yarta.middleware.msemanagement.ThinStorageAccessManager;

/**
 * A generic class representing all possible types of entities carrying some
 * information.
 */
public class ContentImpl extends fr.inria.arles.yarta.resources.ContentImpl
		implements Content {

	/**
	 * Wraps a given node into a ContentImpl object
	 * 
	 * @param sam
	 *            The storage and access manager
	 * @param n
	 *            The node to wrap
	 */
	public ContentImpl(ThinStorageAccessManager sam, Node n) {
		super(sam, n);
	}

	@Override
	public Set<Person> getAnswered_inverse() {
		return sam.getObjectProperty_inverse(kbNode,
				Person.PROPERTY_ANSWERED_URI);
	}

	@Override
	public Set<Person> getLikes_inverse() {
		return sam.getObjectProperty_inverse(kbNode, Person.PROPERTY_LIKES_URI);
	}
}
