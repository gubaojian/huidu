package net.java.efurture.reader.clean;

public class CodeDetect {

	
	public static void main(String [] args){
		String code = "private void initializeBucket(int bucketIdx) {"
				+
     "int parentIdx = bucketIdx ^ Integer.highestOneBit(bucketIdx);  "
       + " if (getBucket(parentIdx) == null)         initializeBucket(parentIdx);      Node dummy = new Node();     dummy.hash = Integer.reverse(bucketIdx);     dummy.next = new AtomicReference&amp;lt;&amp;gt;();      setBucket(bucketIdx, listInsert(getBucket(parentIdx), dummy)); }";
     
		System.out.println("         ".length());
		
	}
}
