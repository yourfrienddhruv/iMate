package com.dstar.imate.remote;

import com.dstar.imate.entity.Group;
import com.dstar.imate.entity.Relationship;
import com.dstar.imate.entity.UserProfile;
import com.dstar.imate.transport.ResponseData;

public interface RelationshipManager extends IManager {
	public ResponseData<UserProfile> fetchProfile(String username);
	public ResponseData<UserProfile> createProfile(UserProfile userprofile);
	public ResponseData<Group> createGroup(Group group);
	public ResponseData<UserProfile> findProfiles(String usernamePrefix,int maxResults);
	public ResponseData<Relationship> discoverRelationship(UserProfile user);
	public ResponseData<Relationship> addRelationship(Relationship relationship);
	public ResponseData<Group> findGroups(String namePrefix, int maxResults);
	public ResponseData<Group> checkUniqueGroup(String name);
	public ResponseData<UserProfile> checkUniqueProfile(String username);
}
