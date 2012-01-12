package com.dstar.imate.remote;

import com.dstar.imate.data.IGroup;
import com.dstar.imate.data.IRelationship;
import com.dstar.imate.data.IUserProfile;
import com.dstar.imate.transport.ResponseData;

public interface RelationshipManager<R extends IRelationship<?, U,G>, U extends IUserProfile<?>,G extends IGroup<?>> extends IManager {
	public ResponseData<U> fetchProfile(String username);
	public ResponseData<U> createProfile(U userprofile);
	public ResponseData<G> createGroup(G group);
	public ResponseData<U> findProfiles(String usernamePrefix,int maxResults);
	public ResponseData<R> discoverRelationship(U user);
	public ResponseData<R> addRelationship(R relationship);
	public ResponseData<G> findGroups(String namePrefix, int maxResults);
	public ResponseData<G> checkUniqueGroup(String name);
	public ResponseData<U> checkUniqueProfile(String username);
}
