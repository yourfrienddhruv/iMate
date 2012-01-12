package com.dstar.imate.business;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import com.dstar.imate.entity.GroupEntity;
import com.dstar.imate.entity.RelationshipEntity;
import com.dstar.imate.entity.UserProfileEntity;
import com.dstar.imate.remote.RelationshipManager;
import com.dstar.imate.transport.ResponseData;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Remote({RelationshipManager.class})
public class RelationshipManagerBean implements RelationshipManager<RelationshipEntity,UserProfileEntity,GroupEntity> {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(RelationshipManagerBean.class);

	private static final String END_OF_RANGE="\uffff";
	
	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void init() {
		log.debug("Inted EntityManager = {}", em);
	}

	@Override
	public ResponseData<RelationshipEntity> addRelationship(RelationshipEntity relationship) {
		log.debug("Before persist Relationship:{}", relationship);
		if(relationship.getStarted()==null){	relationship.setStarted(new Date());	}

		// CascadeType.PERSIST on Relationship is not working in Hector-JPA, thus manually merging referenced fields.
		{	relationship.setUser(em.merge(relationship.getUser()));
			relationship.setGroup(em.merge(relationship.getGroup()));
		}
		em.persist(relationship);
		if (relationship.getId() != null) {
			return ResponseData.positive(relationship);
		} else {
			return ResponseData.negative("fail.creation.relationship", relationship);
		}
	}

	@Override
	public ResponseData<RelationshipEntity> discoverRelationship(UserProfileEntity user) {
		int maxResults=20;
		log.debug("finding first {} Relationship with user {}",maxResults,user);
		TypedQuery<RelationshipEntity> q = em.createNamedQuery("ByUserProfile", RelationshipEntity.class)
				.setParameter("userProfileId", user.getId()).setMaxResults(maxResults);
		List<RelationshipEntity> foundList = q.getResultList();
		log.info("Result found:{}", foundList);
		if (foundList == null) {
			return ResponseData.negativeSet("no.user.found",new RelationshipEntity[0]);
		} else {
			return ResponseData.positiveSet(foundList.toArray(new RelationshipEntity[foundList.size()]));
		}
	}
	
	@Override
	public ResponseData<UserProfileEntity> fetchProfile(String username) {
		log.debug("trying fetch Profile using username {}", username);
		TypedQuery<UserProfileEntity> q = em.createNamedQuery("ByUsername", UserProfileEntity.class).setParameter("username", username)
				.setParameter("status", "A").setMaxResults(1); // active
		List<UserProfileEntity> found = q.getResultList();
		log.info("Result found:{}", found);
		if (found.isEmpty()) {
			return ResponseData.negative("no.user.found",null);
		} else {
			return ResponseData.positive(found.get(0));
		}
	}

	@Override
	public ResponseData<UserProfileEntity> checkUniqueProfile(String username) {
		log.debug("checkUniqueProfile using username {}", username);
		ResponseData<UserProfileEntity> exists = fetchProfile(username);
		if(exists.isSuccess()){
			return ResponseData.negative("fail.nonunique.user.username", exists.getData());
		}else{
			return ResponseData.positive(null);
		}
	}
	
	@Override
	public ResponseData<UserProfileEntity> createProfile(UserProfileEntity userProfile) {
		log.debug("Before persist userProfile:{}", userProfile);
		ResponseData<UserProfileEntity> exists = checkUniqueProfile(userProfile.getUsername());
		if(!exists.isSuccess()){
			return ResponseData.negative("fail.nonunique.user.username", exists.getData());
		}else{
			em.persist(userProfile);
			if (userProfile.getId() != null) {
				return ResponseData.positive(userProfile);
			} else {
				return ResponseData.negative("fail.creation.userProfile", userProfile);
			}
		}
	}

	@Override
	public ResponseData<UserProfileEntity> findProfiles(String usernamePrefix, int maxResults) {
		log.debug("finding first {} user with name starting {}", maxResults, usernamePrefix);
		TypedQuery<UserProfileEntity> q = em.createNamedQuery("LikeUserName", UserProfileEntity.class)
				.setParameter("usernameStart", usernamePrefix)
				.setParameter("usernameEnd", usernamePrefix+END_OF_RANGE).setMaxResults(maxResults);
		List<UserProfileEntity> foundList = q.getResultList();
		log.info("Result found:{}", foundList);
		if (foundList == null) {
			return ResponseData.negativeSet("no.user.found", new UserProfileEntity[0]);
		} else {
			return ResponseData.positiveSet(foundList.toArray(new UserProfileEntity[foundList.size()]));
		}
	}
	
	@Override
	public ResponseData<GroupEntity> findGroups(String namePrefix, int maxResults) {
		log.debug("finding first {} group with name starting {}", maxResults, namePrefix);
		TypedQuery<GroupEntity> q = em.createNamedQuery("LikeName", GroupEntity.class)
				.setParameter("nameStart", namePrefix)
				.setParameter("nameEnd", namePrefix+END_OF_RANGE).setMaxResults(maxResults);
		List<GroupEntity> foundList = q.getResultList();
		log.info("Result found:{}", foundList);
		if (foundList == null) {
			return ResponseData.negativeSet("no.user.found", new GroupEntity[0]);
		} else {
			return ResponseData.positiveSet(foundList.toArray(new GroupEntity[foundList.size()]));
		}
	}

	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.RelationshipManager#createGroup(com.dstar.imate.data.Group)
	 */
	@Override
	public ResponseData<GroupEntity> createGroup(GroupEntity group) {
		log.debug("Before persist group:{}", group);
		ResponseData<GroupEntity> unique = checkUniqueGroup(group.getName());
		if(!unique.isSuccess()){
			return unique;
		}else{
			if(group.getCreated()==null){	group.setCreated(new Date());	}
			em.persist(group);
			if (group.getId() != null) {
				return ResponseData.positive(group);
			} else {
				return ResponseData.negative("fail.creation.group", group);
			}
		}
	}
	
	@Override
	public ResponseData<GroupEntity> checkUniqueGroup(String name) {
		log.debug("checkUniqueGroupName with name {}", name);
		TypedQuery<GroupEntity> q = em.createNamedQuery("ByName", GroupEntity.class)
				.setParameter("name", name).setMaxResults(1);
		List<GroupEntity> found = q.getResultList();	//Can't use getSingleResult() as will throw Exception if zero rows.
		log.info("Unique found:{}", found);
		if (found.isEmpty()) {
			return ResponseData.positive(null);
		} else {
			return ResponseData.negative("fail.nonunique.group.name", found.get(0));
		}
	}
}