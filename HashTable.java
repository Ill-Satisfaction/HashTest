import java.util.Random;

public class HashTable {
	public enum probeMethod {LINEAR, DOUBLE};
	
	private HashObject[] table;
	private int numItems;	// n = number of items to be inserted
	private probeMethod pm;
	
	private double numElements;
	private int duplicates;
	private double sumProbeNum;
	
	public HashTable (int tableSize, probeMethod pm) {
		table = new HashObject[tableSize];
		this.pm = pm;
		duplicates=0;
		sumProbeNum=-1.0;
		numElements=0;
	}
	
	public void printDebug (int numItems, double loadFactor) {
		System.out.println(String.format("\nUsing %s Hashing....", pm.toString()));
		System.out.println("Input "+numItems+" elements, of which "+duplicates+" duplicates");
		System.out.println("load factor: "+loadFactor+", Avg. no. of probes: "+sumProbeNum/numItems);
	}
	
	public int size () {return table.length;}
	
	public void insert (int key, HashObject<?> h) {
		// TODO check number of items
		int index =hashValue(key);
		int count =0;
		
		while (table[index]!=null) {
			count++;
			h.iterateProbeCount();
			
			if (h.equals(table[index])) {
				duplicates++;
				table[index].iterateDupCount();
				return;
			}
			
			if (pm==probeMethod.LINEAR) {
				if (index==table.length-1) index=0;
				else index++;
			}
			if (pm==probeMethod.DOUBLE) {
				index=secondaryHashValue(key, count);
			}
			sumProbeNum++;
		}
		h.iterateProbeCount();
		sumProbeNum++;
		table[index]=h;
		numElements++;
		//System.out.println(numElements/table.length);
	}
	
	private int hashValue (int key) {
		int retval = key%table.length;
		return Math.abs( retval );
	}
	
	
	// math.absolute(keyvalu%tablesize
	// math.abs( 1 + (keyvalue%(tablesize-2))
	
	private int secondaryHashValue (int key, int iteration) {
		/*int retval = 1+(key%(table.length-2));
		retval = Math.abs(retval);
		
		retval = (hashValue(key) + iteration*retval)%table.length;*/
		int retval = 1 +(key % (table.length-2));
		return Math.abs( retval );
	}
	
	
	
	
	
	
	
	
	
	
	

}
