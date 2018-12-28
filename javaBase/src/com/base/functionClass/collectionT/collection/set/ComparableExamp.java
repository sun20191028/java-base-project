package com.base.functionClass.collectionT.collection.set;

import java.util.Comparator;

public class ComparableExamp  implements Comparator<Entity>{



	@Override
	public int compare(Entity o1, Entity o2) {
		int sum = o1.num1 - o1.num2;
		int sum2 = o2.num1 - o2.num2;
		return sum - sum2;
		
	}

}
