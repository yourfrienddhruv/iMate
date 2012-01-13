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

import com.dstar.imate.entity.Group;
import com.dstar.imate.entity.Relationship;
import com.dstar.imate.entity.UserProfile;
import com.dstar.imate.remote.RelationshipManager;
import com.dstar.imate.transport.ResponseData;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Remote({RelationshipManager.class})
public class RelationshipManagerBean implements RelationshipManager {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(RelationshipManagerBean.class);
	
	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void init() {
		log.debug("Inted EntityManager = {}", em);
	}

	@Override
	public ResponseData<Relationship> addRelationship(Relationship relationship) {
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
	public ResponseData<Relationship> discoverRelationship(UserProfile user) {
		int maxResults=20;
		log.debug("finding first {} Relationship with user {}",maxResults,user);
		TypedQuery<Relationship> q = em.createNamedQuery("ByUserProfile", Relationship.class)
				.setParameter("userProfileId", user.getId()).setMaxResults(maxResults);
		List<Relationship> foundList = q.getResultList();
		log.info("Result found:{}", foundList);
		if (foundList == null) {
			return ResponseData.negativeSet("no.user.found",new Relationship[0]);
		} else {
			return ResponseData.positiveSet(foundList.toArray(new Relationship[foundList.size()]));
		}
	}
	
	@Override
	public ResponseData<UserProfile> fetchProfile(String username) {
		log.debug("trying fetch Profile using username {}", username);
		TypedQuery<UserProfile> q = em.createNamedQuery("ByUsername", UserProfile.class).setParameter("username", username)
				.setParameter("status", "A").setMaxResults(1); // active
		List<UserProfile> found = q.getResultList();
		log.info("Result found:{}", found);
		if (found.isEmpty()) {
			return ResponseData.negative("no.user.found",null);
		} else {
			return ResponseData.positive(found.get(0));
		}
	}

	@Override
	public ResponseData<UserProfile> checkUniqueProfile(String username) {
		log.debug("checkUniqueProfile using username {}", username);
		ResponseData<UserProfile> exists = fetchProfile(username);
		if(exists.isSuccess()){
			return ResponseData.negative("fail.nonunique.user.username", exists.getData());
		}else{
			return ResponseData.positive(null);
		}
	}
	
	@Override
	public ResponseData<UserProfile> createProfile(UserProfile userProfile) {
		log.debug("Before persist userProfile:{}", userProfile);
		ResponseData<UserProfile> exists = checkUniqueProfile(userProfile.getUsername());
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
	public ResponseData<UserProfile> findProfiles(String usernamePrefix, int maxResults) {
		log.debug("finding first {} user with name starting {}", maxResults, usernamePrefix);
		TypedQuery<UserProfile> q = em.createNamedQuery("LikeUserName", UserProfile.class)
				.setParameter("usernameStart", usernamePrefix)
				.setParameter("usernameEnd", usernamePrefix+END_OF_RANGE).setMaxResults(maxResults);
		List<UserProfile> foundList = q.getResultList();
		log.info("Result found:{}", foundList);
		if (foundList == null) {
			return ResponseData.negativeSet("no.user.found", new UserProfile[0]);
		} else {
			return ResponseData.positiveSet(foundList.toArray(new UserProfile[foundList.size()]));
		}
	}
	
	@Override
	public ResponseData<Group> findGroups(String namePrefix, int maxResults) {
		log.debug("finding first {} group with name starting {}", maxResults, namePrefix);
		TypedQuery<Group> q = em.createNamedQuery("LikeName", Group.class)
				.setParameter("nameStart", namePrefix)
				.setParameter("nameEnd", namePrefix+END_OF_RANGE).setMaxResults(maxResults);
		List<Group> foundList = q.getResultList();
		log.info("Result found:{}", foundList);
		if (foundList == null) {
			return ResponseData.negativeSet("no.user.found", new Group[0]);
		} else {
			return ResponseData.positiveSet(foundList.toArray(new Group[foundList.size()]));
		}
	}

	/* (non-Javadoc)
	 * @see com.dstar.imate.remote.RelationshipManager#createGroup(com.dstar.imate.data.Group)
	 */
	@Override
	public ResponseData<Group> createGroup(Group group) {
		log.debug("Before persist group:{}", group);
		ResponseData<Group> unique = checkUniqueGroup(group.getName());
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
	public ResponseData<Group> checkUniqueGroup(String name) {
		log.debug("checkUniqueGroupName with name {}", name);
		TypedQuery<Group> q = em.createNamedQuery("ByName", Group.class)
				.setParameter("name", name).setMaxResults(1);
		List<Group> found = q.getResultList();	//Can't use getSingleResult() as will throw Exception if zero rows.
		log.info("Unique found:{}", found);
		if (found.isEmpty()) {
			return ResponseData.positive(null);
		} else {
			return ResponseData.negative("fail.nonunique.group.name", found.get(0));
		}
	}
}