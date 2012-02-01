package com.dstar.imate.data.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.DynamicComposite;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.contrib.utils.service.CassandraServiceDataCleaner;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.DynamicCompositeType;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.apache.thrift.transport.TTransportException;

import com.datastax.hectorjpa.index.IndexOperation;
import com.datastax.hectorjpa.meta.collection.AbstractCollectionField;
import com.dstar.imate.data.Coupon;
import com.dstar.imate.data.Group;
import com.dstar.imate.data.Relationship;
import com.dstar.imate.data.UserProfile;

public class CassandraService {
	protected static boolean cassandraStarted = false;
	protected static String keySpaceName = "iMateV3"; 
	protected static Keyspace keyspace;
	protected static Cluster cluster;

	public static void startCassandraInstance(String pathToDataDir) throws TTransportException, IOException, InterruptedException,
			SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (cassandraStarted) {
			return;
		}

		try {
			FileUtils.deleteRecursive(new File(pathToDataDir));
		} catch (AssertionError e) {
			// eat
		} catch (IOException e) {
			// eat
		}

		CassandraServiceDataCleaner cleaner = new CassandraServiceDataCleaner();
		cleaner.prepare();
		EmbeddedCassandraService cassandra = new EmbeddedCassandraService();

		cassandra.start();

		cassandraStarted = true;

		// Thread t = new Thread(new Runnable(){
		//
		// @Override
		// public void run() {
		// cassandra.
		//
		// }
		//
		// });
		// t.setName(cassandra.getClass().getSimpleName());
		// t.setDaemon(true);
		// t.start();

		System.out.println("Successfully started Cassandra");
	}

	public static void createKeyspace(Cluster cluster, String name, String strategy, int replicationFactor, List<CfDef> cfDefList) {
		try {
			KsDef ksDef = new KsDef(name, strategy, cfDefList);
			cluster.addKeyspace(new ThriftKsDef(ksDef));
			return;
		} catch (Throwable e) {
			System.out.println("exception while creating keyspace, " + name + " - probably already exists : " + e.getMessage());
		}
		/*
		 * for (CfDef cfDef : cfDefList) { try { cluster.addColumnFamily(new ThriftCfDef(cfDef)); } catch (Throwable e) {
		 * System.out.println("exception while creating CF, " + cfDef.getName() + " - probably already exists : " + e.getMessage()); } }
		 */
	}

	public static void setupKeyspace() throws TTransportException, SecurityException, IllegalArgumentException, IOException,
			InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		startCassandraInstance("/tmp/cassandra");
		System.out.println("Creating TestKeyspace and columnfamilies");
		ArrayList<CfDef> cfDefList = new ArrayList<CfDef>(2);

		// === user defined entities 
		cfDefList.add(new CfDef(keySpaceName, Coupon.class.getSimpleName()).setComparator_type(BytesType.class.getSimpleName())
				.setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
		cfDefList.add(new CfDef(keySpaceName, Group.class.getSimpleName()).setComparator_type(BytesType.class.getSimpleName())
				.setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
		cfDefList.add(new CfDef(keySpaceName, Relationship.class.getSimpleName()).setComparator_type(BytesType.class.getSimpleName())
				.setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
		cfDefList.add(new CfDef(keySpaceName, UserProfile.class.getSimpleName()).setComparator_type(BytesType.class.getSimpleName())
				.setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));

		// Required for Hector JPA
		
		// collection indexing
		cfDefList.add(new CfDef(keySpaceName, AbstractCollectionField.CF_NAME)
				.setComparator_type(DynamicCompositeType.class.getSimpleName() + DynamicComposite.DEFAULT_DYNAMIC_COMPOSITE_ALIASES)

				.setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));

		// search indexing
		cfDefList.add(new CfDef(keySpaceName, IndexOperation.CF_NAME)
				.setComparator_type(DynamicCompositeType.class.getSimpleName() + DynamicComposite.DEFAULT_DYNAMIC_COMPOSITE_ALIASES)
				.setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));

		cluster = HFactory.getOrCreateCluster("TestPool", "localhost:9160");

		createKeyspace(cluster, keySpaceName, "org.apache.cassandra.locator.SimpleStrategy", 1, cfDefList);
		keyspace = HFactory.createKeyspace(keySpaceName, cluster);

		System.out.println("TestKeyspace and columnfamilies creation is complete");
	}
}
