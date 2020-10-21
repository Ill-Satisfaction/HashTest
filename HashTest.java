import java.util.Random;

public class HashTest {

	public static void main(String[] args) {
		
		double[] loadFactor = {0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.98, 0.99};
		int[] twinPrimes = FindTwinPrimes.getArray(95500, 96000);
		double currLoadFactor = loadFactor[7];
		
		System.out.println("A good size table is found: "+twinPrimes[1]);
		System.out.println("Data source type: "+"java.util.Random");
		
		// load tables
		HashTable htLinear = new HashTable(twinPrimes[1], HashTable.probeMethod.LINEAR);
		HashTable htDouble = new HashTable(twinPrimes[1], HashTable.probeMethod.DOUBLE);
		
		// random ints
		int numItems = (int) (htLinear.size() *currLoadFactor);
		Random rand = new Random();
		for (int i=0; i<=numItems; i++) {
			HashObject<Integer> tmp = new HashObject<Integer>(rand.nextInt());
			htLinear.insert( tmp.getKey(), tmp );
			htDouble.insert( tmp.getKey(), tmp );
		}
		
		
		htLinear.printDebug(numItems, currLoadFactor);
		htDouble.printDebug(numItems, currLoadFactor);
	}
}