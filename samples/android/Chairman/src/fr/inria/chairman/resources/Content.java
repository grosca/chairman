package fr.inria.chairman.resources;

import java.util.Set;

/**
 * A generic class representing all possible types of entities carrying some
 * information.
 */
public interface Content extends fr.inria.arles.yarta.resources.Content {

	/**
	 * 
	 * @return The list of resources linked by a "answered" edge with the
	 *         current resource. Empty list if I know no one. null if there was
	 *         an error
	 * 
	 */
	public Set<Person> getAnswered_inverse();

	/**
	 * 
	 * @return The list of resources linked by a "likes" edge with the current
	 *         resource. Empty list if I know no one. null if there was an error
	 * 
	 */
	public Set<Person> getLikes_inverse();
}
