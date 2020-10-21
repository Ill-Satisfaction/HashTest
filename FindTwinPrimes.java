public class FindTwinPrimes {
	
	public static int[] getArray (int lowerBound, int upperBound) {
		int[] retval = {-1, -1};
		if (lowerBound%2==0) lowerBound++;
		
		for (int i=lowerBound; i<=upperBound; i+=2) {	//check all odd numbers in range for primeness
			if (checkPrime(i)) {
				// if a set of twin primes exists use that, otherwise fill retval 
				// with first two primes found
				if (checkPrime(i+2)) {
					retval[0]=i;
					retval[1]=i+2;
					return retval;
				}
				if (retval[0]<0) retval[0]=i;
				else if (retval[1]<0) retval[1]=i;
			}
		}
			
		return retval;
	}
	
	//	---
	
	private static boolean checkPrime (int num) {
		int a = 2; //replace with random later
		int b = 3;
		int aVal = alg(a, num);
		int bVal = alg(b, num);
		
		if (aVal==1 && bVal==1) return true;
		else return false;
		
	}
	
	private static int alg (int a, int p) {
		// calculates a^(p-1) % p for large numbers
		int retval =1;
		for(int i=1; i<p; i++) {
			retval = (a*retval) % p;
		}
        return retval;
	}
}
