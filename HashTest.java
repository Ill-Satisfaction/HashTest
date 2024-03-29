import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;
import java.util.Scanner;

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
		inputType in = inputType.RANDOM;
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
		
		
		
		int numItems = (int) (twinPrimes[1] *loadFactor);
		
		
		
		
		
		System.out.println("A good size table is found: "+twinPrimes[1]);
		System.out.println("Data source type: "+in);
		
		// load tables
		HashTable htLinear = new HashTable(twinPrimes[1], HashTable.probeMethod.LINEAR, generateDumpFiles, loadFactor, numItems);
		HashTable htDouble = new HashTable(twinPrimes[1], HashTable.probeMethod.DOUBLE, generateDumpFiles, loadFactor, numItems);
		
		switch (in) {
		case RANDOM :
			// random ints
			Random rand = new Random();
			while (!htLinear.isFull()) {
				HashObject<Integer> tmp = new HashObject<Integer>(rand.nextInt());
				htLinear.insert( tmp );
				htDouble.insert( tmp );
			}
			break;
		case SYSTEM_TIME:
			while (!htLinear.isFull()) {
				HashObject<Long> tmp = new HashObject<>(System.currentTimeMillis());
				htLinear.insert( tmp );
				htDouble.insert( tmp );
			}
			break;
		case WORD_LIST:
			// word list
			// TODO fix number of elements and duplicates? something wrong here
			try {
				BufferedReader br = new BufferedReader(new FileReader("word-list"));
				String word="";
				while (((word=br.readLine())!=null && !htLinear.isFull())) {
					HashObject<String> tmp = new HashObject<String>(word);
					htLinear.insert(tmp);
					htDouble.insert(tmp);
				}
				br.close();
			} catch (Exception e) {
				System.out.println("cannot access file 'word-list'");
				return;
			}
			break;
			
		}
		
		
		htLinear.printDebug();
		htDouble.printDebug();
	}
}