package fr.inria.chairman;

import fr.inria.arles.yarta.resources.Group;

public class Constants {

	private static final int GroupId = 38195;

	public static final String getGroupId() {
		return Group.typeURI + "_" + GroupId;
	}
}
