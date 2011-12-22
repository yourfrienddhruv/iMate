package com.dstar.imate.remote;

import java.util.SortedSet;

import com.dstar.imate.data.IGroup;
import com.dstar.imate.data.IRelationship;
import com.dstar.imate.data.IUserProfile;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.transport.ResponseDataSet;

public interface RelationshipManager<R extends IRelationship<?, U,G>, U extends IUserProfile<?>,G extends IGroup<?>> extends IManager {
	public ResponseData<U> fetchProfile(String username);
	public ResponseData<U> createProfile(U userprofile);
	public ResponseData<G> createGroup(G group);
	public ResponseDataSet<? extends SortedSet <U>> findProfiles(String usernamePrefix,int maxResults);
	public ResponseDataSet<? extends SortedSet <R>> discoverRelationship(U user);
	public ResponseData<R> addRelationship(R relationship);
	public ResponseDataSet<? extends SortedSet <G>> findGroups(String namePrefix, int maxResults);
	public ResponseData<G> checkUniqueGroup(String name);
	public ResponseData<U> checkUniqueProfile(String username);
}
