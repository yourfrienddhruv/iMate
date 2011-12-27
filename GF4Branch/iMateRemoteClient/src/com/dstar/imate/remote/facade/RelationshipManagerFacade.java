package com.dstar.imate.remote.facade;

import java.util.SortedSet;

import com.dstar.imate.remote.RelationshipManager;
import com.dstar.imate.remote.data.Group;
import com.dstar.imate.remote.data.Relationship;
import com.dstar.imate.remote.data.UserProfile;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.transport.ResponseDataSet;

public class RelationshipManagerFacade extends BaseFacade<RelationshipManager<Relationship,UserProfile,Group>> implements RelationshipManager<Relationship,UserProfile,Group>{
	//@EJB(lookup="java:global/iMateEAR/iMate/RelationshipManagerBean")
	private RelationshipManager<Relationship, UserProfile, Group> manager;
	protected RelationshipManagerFacade(RelationshipManager<Relationship, UserProfile, Group> manager){
		super(manager);
		this.manager=manager;
	}
		
	public static RelationshipManagerFacade getInstance(RelationshipManager<Relationship, UserProfile, Group> manager){
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
	public ResponseDataSet<? extends SortedSet<UserProfile>> findProfiles(String usernamePrefix,int maxResults){
		try{
			return manager.findProfiles(usernamePrefix, maxResults);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseDataSet.failed(e);
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

	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.RelationshipManager#createGroup(com.dstar.imate.data.Group)
	 */
	@Override
	public ResponseData<Group> createGroup(Group group) {
		try{
			return manager.createGroup(group);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.RelationshipManager#discoverRelationship(com.dstar.imate.data.UserProfile)
	 */
	@Override
	public ResponseDataSet<? extends SortedSet<Relationship>> discoverRelationship(UserProfile user) {
		try{
			return manager.discoverRelationship(user);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseDataSet.failed(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.RelationshipManager#findGroups(java.lang.String, int)
	 */
	@Override
	public ResponseDataSet<? extends SortedSet<Group>> findGroups(String namePrefix, int maxResults) {
		try{
			return manager.findGroups( namePrefix, maxResults);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseDataSet.failed(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.RelationshipManager#checkUniqueGroup(java.lang.String)
	 */
	@Override
	public ResponseData<Group> checkUniqueGroup(String name) {
		try{
			return manager.checkUniqueGroup( name);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseData.failed(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.RelationshipManager#checkUniqueProfile(java.lang.String)
	 */
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
