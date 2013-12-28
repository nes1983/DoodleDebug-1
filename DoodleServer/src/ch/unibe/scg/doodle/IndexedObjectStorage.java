package ch.unibe.scg.doodle;

import ch.unibe.scg.doodle.hbase.HBaseMap;
import ch.unibe.scg.doodle.helperClasses.Nullable;

/**
 * Storage for objects to be possibly rendered later (clickables). Every stored
 * object maps exactly to one ID.
 * 
 * @author Cedric Reichenbach
 * 
 */
public final class IndexedObjectStorage {
	static final int CAPACITY = 10000;
	private final Object[] ringBuffer;
	private int nextID;

	private HBaseMap hBaseMap;

	public IndexedObjectStorage() {
		this.nextID = 0;
		this.ringBuffer = new Object[CAPACITY];
		
		this.hBaseMap = new HBaseMap();
	}

	/** @return Id of stored object. */
	public int store(Object o) {
		hBaseMap.put(nextID, o);
		
		ringBuffer[nextID % CAPACITY] = o;
		return nextID++;
	}

	public @Nullable Object get(int id) {
		if (id < nextID - CAPACITY || id >= nextID)
			return hBaseMap.get(nextID);
		
		return ringBuffer[id % CAPACITY];
	}
}
