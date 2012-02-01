package com.dstar.imate.data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.apache.openjpa.persistence.Persistent;

import com.eaio.uuid.UUID;
import com.google.gson.annotations.Expose;

@MappedSuperclass
public abstract class BaseEntity implements IEntity{
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	
	// create column family Collection_Container with comparator = 'DynamicCompositeType(a=>AsciiType,b=>BytesType,i=>IntegerType,x=>LexicalUUIDType,l=>LongType,t=>TimeUUIDType,s=>UTF8Type,u=>UUIDType,A=>AsciiType(reversed=true),B=>BytesType(reversed=true),I=>IntegerType(reversed=true),X=>LexicalUUIDType(reversed=true),L=>LongType(reversed=true),T=>TimeUUIDType(reversed=true),S=>UTF8Type(reversed=true),U=>UUIDType(reversed=true))';
	@Expose
	@Id
	@Persistent
	@SequenceGenerator(name = "timeuuid", allocationSize = 100, sequenceName = "com.datastax.hectorjpa.sequence.TimeUuid()")
	@GeneratedValue(generator = "timeuuid", strategy = GenerationType.SEQUENCE)
	private UUID id;

	/**
	 * Get the Id
	 * 
	 * @return UUID
	 */
	public UUID getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseEntity))
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		// if both entity Id's are null we revert to instance equality
		if (id == null && other.id == null) {
			return this == obj;
		}
		return true;
	}
}
