import java.util.Random;

public class HashTest {
	public enum inputType {RANDOM, SYSTEM_TIME, WORD_LIST};

	public static void main(String[] args) {
		
		// validate arguments
		if (args.length<2 || args.length>3) {
			System.out.println("please use correct arguments: <input type> <load factor> [<debug level>]");
			return;
		}
		try {
			if (Integer.parseInt(args[0])<0 || Integer.parseInt(args[0])>3) {}
			Double.parseDouble(args[1]);
			if (args.length==3)
				if (Integer.parseInt(args[2])!=0 && Integer.parseInt(args[2])!=1)
					throw new Exception();
		} catch (Exception e) {
			System.out.println("please use correct argument types: <int> <double> <0 or 1(optional)>");
			return;
		}
		
		// parse arguments
		inputType in;
		switch (Integer.parseInt(args[0])) {
			case 1 :
				in = inputType.RANDOM;
				break;
			case 2 :
				in = inputType.SYSTEM_TIME;
				break;
			case 3 :
				in = inputType.WORD_LIST;
				break;
		}
		double loadFactor = Double.parseDouble(args[1]);
		boolean generateDumpFiles = (args.length==3 && args[2].equals("1"));
		
		// begin doing stuff
		int[] twinPrimes = FindTwinPrimes.getArray(95500, 96000);
		System.out.println("A good size table is found: "+twinPrimes[1]);
		System.out.println("Data source type: "+"java.util.Random");
		
		// load tables
		HashTable htLinear = new HashTable(twinPrimes[1], HashTable.probeMethod.LINEAR, generateDumpFiles);
		HashTable htDouble = new HashTable(twinPrimes[1], HashTable.probeMethod.DOUBLE, generateDumpFiles);
		
		// random ints
		int numItems = (int) (htLinear.size() *loadFactor);
		Random rand = new Random();
		for (int i=0; i<=numItems; i++) {
			HashObject<Integer> tmp = new HashObject<Integer>(rand.nextInt());
			htLinear.insert( tmp );
			htDouble.insert( tmp );
		}
		
		
		htLinear.printDebug();
		htDouble.printDebug();
	}
}