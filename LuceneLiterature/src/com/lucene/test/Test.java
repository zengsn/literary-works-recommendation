package com.lucene.test;

import com.lucene.index.operation.LiteraIndex;
import com.lucene.init.IndexInit;

public class Test {

	public static void main(String[] args) {
		IndexInit.init();
		
		new LiteraIndex().putLiteraToIndex("C:/Users/CJP/Desktop/Lucene/毕设相关/419部世界名著合集txt书籍");
		new LiteraIndex().commit();
	}
}
