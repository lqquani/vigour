/**
 * 
 */
package org.qql.vigour;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeSet;

/**
 * @author kevin
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Hashtable ht=new Hashtable();
		ht.put(1, 1);
		System.out.println(ht);
		HashMap hm=new HashMap();
		hm.put(null, null);
		System.out.println(hm);
		HashSet hs=new HashSet();
		hs.add(null);
		hs.add(null);
		hs.add(1);
		hs.add(1);
		System.out.println(hs);
		TreeSet ts=new TreeSet();
		ts.add(10);
		ts.add(2);
		ts.add(4);
		ts.add(3);
		System.out.println(ts);
	}

}
