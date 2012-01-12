package com.dstar.imate.remote.client.test;

import java.rmi.RMISecurityManager;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.dstar.imate.data.IGroup;
import com.dstar.imate.remote.RelationshipManager;
import com.dstar.imate.remote.data.Group;
import com.dstar.imate.remote.data.Relationship;
import com.dstar.imate.remote.data.UserProfile;
import com.dstar.imate.remote.facade.RelationshipManagerFacade;
import com.dstar.imate.transport.ResponseData;
import com.dstar.imate.web.json.base.gson.Json;

/**
 * @author Administrator
 * 
 */
public class RelationshipTest {

	public static void main(String[] args) throws Exception {
		System.setProperty("java.security.main","");
		System.setProperty("java.security.policy","AllPermission.policy");
		//Not required System.setProperty("java.rmi.server.codebase","rmi://localhost:3700");
		System.setSecurityManager(new RMISecurityManager());
		
		Properties env = new Properties();
		// jboss 5
		// env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		// jboss 7
		// env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.as.naming.InitialContextFactory");
		// env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		// env.put(Context.PROVIDER_URL, "127.0.0.1:1199,127.0.0.1:1399");
 
		// Glassfish 3.1
		//env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.impl.SerialInitContextFactory");
		//env.put("org.omg.CORBA.ORBInitialPort", "3700");
		
		//WL 12c
		env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		env.put(Context.PROVIDER_URL, "t3://localhost:7001");

		InitialContext ctx = new InitialContext(env);

		@SuppressWarnings("unchecked")
		RelationshipManager<Relationship, UserProfile, Group> managerRemote = (RelationshipManager<Relationship, UserProfile, Group>)
						//GF //JBOSS ctx.lookup("java:global/iMateEAR/iMate/RelationshipManagerBean");
						//WL   : as we deploy it in autodeply thus name changes
				ctx.lookup("java:global/_appsdir_iMateEAR_ear/iMate/RelationshipManagerBean!com.dstar.imate.remote.RelationshipManager");
		RelationshipManagerFacade manager = RelationshipManagerFacade.getInstance(managerRemote);
			
		createUser(manager);

		//fetch(manager);

		// find (manager);

		createGroup(manager);
		// createRelationship(manager);

	}

	private static void find(RelationshipManagerFacade manager) {
		ResponseData<UserProfile> found = manager.findProfiles("j", 10);
		System.out.println(found);
	}

	private static void createUser(RelationshipManagerFacade manager) {
		UserProfile userProfile = new UserProfile();
		userProfile.setUsername("yourfrienddhruv");
		userProfile.setFirstName("Dhurv");
		userProfile.setLastName("Gohil");
		userProfile.setPassword("new-password");
		userProfile.setBirthDate(new Date());
		ResponseData<UserProfile> created = manager.createProfile(userProfile);
		System.out.println(created);
	}

	private static void fetch(RelationshipManagerFacade manager) {
		ResponseData<UserProfile> found = manager.fetchProfile(null);
		System.out.println(Json.to(found));
		found = manager.fetchProfile("yourfrienddhruv");
		System.out.println(Json.to(found));
	}

	private static void createGroup(RelationshipManagerFacade manager) {
		Group group = new Group();
		group.setName("NoSQL");
		ResponseData<Group> created = manager.createGroup(group);
		System.out.println(created);
		if (!created.isSuccess()) {
			System.out.println("Group already exists with same name created on " + created.getData().getCreated());
		}
	}

	private static void createRelationship(RelationshipManagerFacade manager) {
		ResponseData<UserProfile> foundUser = manager.fetchProfile("yourfrienddhruv");
		System.out.println("fetched user" + foundUser);

		ResponseData<Group> groups = manager.findGroups("NoSQL", 1);
		System.out.println("found group" + groups.getDataSet()[0]);
		Relationship relationship = new Relationship();
		relationship.setUser(foundUser.getData());
		relationship.setGroup(groups.getDataSet()[0]);
		relationship.setRelationshipType("user_group");
		ResponseData<Relationship> created = manager.addRelationship(relationship);
		System.out.println("created relationship " + created);
	}
}
