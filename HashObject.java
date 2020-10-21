
public class HashObject<T> {
	private T item;
	//private int key;
	private int dupCount;
	private int probeCount;
	
	//public HashObject () {}
	public HashObject (T item) {
		this.item=item;
		dupCount = 0;
		probeCount =0;
	}
	
	public int getKey () {
		return item.hashCode();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals (Object obj) {
		return item.equals(( (HashObject<T>)obj).getItem());
	}
	
	@Override
	public String toString() {
		return this.item.toString();
	}
	
	public void iterateDupCount () {
		this.dupCount++;
	}
	
	public void iterateProbeCount () {
		this.probeCount++;
	}
	
	public T getItem () {
		return item;
	}

}
