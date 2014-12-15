package fr.inria.chairman.resources;

import fr.inria.arles.yarta.resources.Agent;
import java.util.Set;
import fr.inria.chairman.msemanagement.MSEManagerEx;
import fr.inria.arles.yarta.resources.Content;
import fr.inria.arles.yarta.resources.Resource;

/**
 * 
 * Person interface definition.
 *
 */
public interface Person extends Resource, Agent, fr.inria.arles.yarta.resources.Person {

	/** The unique "type" URI */
	public static final String typeURI = baseMSEURI + "#Person";
	
	/** the URI for property lastName */
	public static final String PROPERTY_LASTNAME_URI = baseMSEURI + "#lastName";

	/** the URI for property email */
	public static final String PROPERTY_EMAIL_URI = baseMSEURI + "#email";

	/** the URI for property userId */
	public static final String PROPERTY_USERID_URI = baseMSEURI + "#userId";

	/** the URI for property name */
	public static final String PROPERTY_NAME_URI = baseMSEURI + "#name";

	/** the URI for property firstName */
	public static final String PROPERTY_FIRSTNAME_URI = baseMSEURI + "#firstName";

	/** the URI for property homepage */
	public static final String PROPERTY_HOMEPAGE_URI = baseMSEURI + "#homepage";

	/** the URI for property knows */
	public static final String PROPERTY_KNOWS_URI = baseMSEURI + "#knows";

	/** the URI for property isTagged */
	public static final String PROPERTY_ISTAGGED_URI = baseMSEURI + "#isTagged";

	/** the URI for property isAttending */
	public static final String PROPERTY_ISATTENDING_URI = baseMSEURI + "#isAttending";

	/** the URI for property hasInterest */
	public static final String PROPERTY_HASINTEREST_URI = baseMSEURI + "#hasInterest";

	/** the URI for property isMemberOf */
	public static final String PROPERTY_ISMEMBEROF_URI = baseMSEURI + "#isMemberOf";

	/** the URI for property likes */
	public static final String PROPERTY_LIKES_URI = MSEManagerEx.baseMSEURI + "#likes";

	/** the URI for property participatesTo */
	public static final String PROPERTY_PARTICIPATESTO_URI = baseMSEURI + "#participatesTo";

	/** the URI for property isLocated */
	public static final String PROPERTY_ISLOCATED_URI = baseMSEURI + "#isLocated";

	/** the URI for property creator */
	public static final String PROPERTY_CREATOR_URI = baseMSEURI + "#creator";

	/** the URI for property answered */
	public static final String PROPERTY_ANSWERED_URI = MSEManagerEx.baseMSEURI + "#answered";

	/**
	 * Creates a "likes" edge between this person and content
	 * 
	 * @param	content
	 *			the Content
	 *
	 * @return true if all went well, false otherwise
	 */
	public boolean addLikes(Content content);
	
	/**
	 * deletes the "likes" link between this person and content
	 * 
	 * @param	content
	 * 			the Content
	 * @return true if success. false is something went wrong
	 */
	public boolean deleteLikes(Content content);
	
	/**
	 * 
	 * @return	The list of resources linked by a "likes" edge with the current resource.
	 *			Empty list if I know no one. null if there was an error
	 *
	 */
	public Set<Content> getLikes();

	/**
	 * Creates a "answered" edge between this person and content
	 * 
	 * @param	content
	 *			the Content
	 *
	 * @return true if all went well, false otherwise
	 */
	public boolean addAnswered(Content content);
	
	/**
	 * deletes the "answered" link between this person and content
	 * 
	 * @param	content
	 * 			the Content
	 * @return true if success. false is something went wrong
	 */
	public boolean deleteAnswered(Content content);
	
	/**
	 * 
	 * @return	The list of resources linked by a "answered" edge with the current resource.
	 *			Empty list if I know no one. null if there was an error
	 *
	 */
	public Set<Content> getAnswered();
}