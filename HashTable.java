import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class HashTable {
	public enum probeMethod {LINEAR, DOUBLE};
	
	private HashObject[] table;
	private probeMethod pm;
	private boolean dump;
	private double loadFactor;
	
	private int numElements;
	private long duplicates;
	private long sumProbeNum;
	
	public HashTable (int tableSize, probeMethod pm, boolean dump, double loadFactor) {
		table = new HashObject[tableSize];
		this.pm = pm;
		this.dump = dump;
		this.loadFactor = loadFactor;
		duplicates=0;
		sumProbeNum=0;
		numElements=0;
	}
	
	public void insert (HashObject<?> h) {
		int index =hashValue( h.getKey()) ;//% table.length;
		int count =0;
		numElements++;
		int tmpProbeCount =1;
		
		while (table[index]!=null) {
			tmpProbeCount = count+1;
			
			if (h.equals(table[index])) {
				duplicates++;
				table[index].iterateDupCount();
				sumProbeNum += tmpProbeCount;
				return;
			}
			if (pm==probeMethod.LINEAR)
				index = calcPrimaryHash(h.getKey(), count);
			else if (pm==probeMethod.DOUBLE)
				index = calcSecondaryHash(h.getKey(), count);
			
			count++;
		}
		
		h.setProbeCount(h.getProbeCount()+tmpProbeCount);
		sumProbeNum += tmpProbeCount;
		table[index]=h;
		
		
	}
	
	public void printDebug () {
		System.out.println(String.format("\nUsing %s Hashing....", pm.toString()));
		System.out.println("Input "+numElements+" elements, of which "+duplicates+" duplicates");
		System.out.println("load factor: "+loadFactor
				+", Avg. no. of probes: "+(double)sumProbeNum/numElements);
		if (dump) dump();
	}
	
	public boolean isFull () {
		double currLoadFactor = numElements-duplicates;
		return currLoadFactor/table.length >=loadFactor;
	}
	
	public int size () {return table.length;}
	
	private int calcPrimaryHash (int keyHash, int count) {
		return (hashValue(keyHash) + count)% table.length;
	}
	
	private int calcSecondaryHash (int keyHash, int count) {
		return Math.abs((hashValue(keyHash)+(count*secondaryHashValue(keyHash))) % table.length);
	}
	
	private int hashValue (int keyHash) {
		return Math.abs( keyHash % table.length );
	}
	
	private int secondaryHashValue (int keyHash) {
		return 1 + Math.abs(keyHash % (table.length-2) );
	}
	
	private void dump () {
		File newFile = new File (pm+"-DUMP");
		try {
			newFile.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter (newFile.getPath()));
			for (int i=0; i<table.length; i++) {
				if(table[i]!=null) 
					out.append( "table["+i+"] "
							+table[i].toString()+" "
							+table[i].getDupCount()+" "
							+table[i].getProbeCount()+"\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	

}
