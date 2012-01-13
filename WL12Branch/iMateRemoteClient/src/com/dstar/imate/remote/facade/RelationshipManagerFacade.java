package com.dstar.imate.remote.facade;

import com.dstar.imate.entity.Group;
import com.dstar.imate.entity.Relationship;
import com.dstar.imate.entity.UserProfile;
import com.dstar.imate.remote.RelationshipManager;
import com.dstar.imate.transport.ResponseData;

public class RelationshipManagerFacade extends BaseFacade<RelationshipManager> implements RelationshipManager{
	//@EJB(lookup="java:global/iMateEAR/iMate/RelationshipManagerBean")
	private RelationshipManager manager;
	protected RelationshipManagerFacade(RelationshipManager manager){
		super(manager);
		this.manager=manager;
	}
	
	public static RelationshipManagerFacade getInstance(RelationshipManager manager){
		if(manager!=null){
			return new RelationshipManagerFacade(manager);
		}else{
			throw new IllegalArgumentException("Cant initilize Facade,EJB manager Reference is null.");
		}
	}
	@Override
	public ResponseData<UserProfile> fetchProfile(String username){
		try{
			return manager.fetchProfile(username);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}
	@Override
	public ResponseData<UserProfile> createProfile(UserProfile userprofile){
		try{
			return manager.createProfile(userprofile);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}
	@Override
	public ResponseData<UserProfile> findProfiles(String usernamePrefix,int maxResults){
		try{
			return manager.findProfiles(usernamePrefix, maxResults);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}
	@Override
	public ResponseData<Relationship> addRelationship(Relationship relationship){
		try{
			return manager.addRelationship(relationship);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}

	@Override
	public ResponseData<Group> createGroup(Group group) {
		try{
			return manager.createGroup(group);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}

	@Override
	public ResponseData<Relationship> discoverRelationship(UserProfile user) {
		try{
			return manager.discoverRelationship(user);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}

	@Override
	public ResponseData<Group> findGroups(String namePrefix, int maxResults) {
		try{
			return manager.findGroups( namePrefix, maxResults);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}

	@Override
	public ResponseData<Group> checkUniqueGroup(String name) {
		try{
			return manager.checkUniqueGroup( name);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}

	@Override
	public ResponseData<UserProfile> checkUniqueProfile(String username) {
		try{
			return manager.checkUniqueProfile(username);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}
}
