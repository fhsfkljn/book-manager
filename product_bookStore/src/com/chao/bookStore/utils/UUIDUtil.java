package com.chao.bookStore.utils;

import java.util.UUID;

public class UUIDUtil {
	
	//随机得到一个字符串
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
}
