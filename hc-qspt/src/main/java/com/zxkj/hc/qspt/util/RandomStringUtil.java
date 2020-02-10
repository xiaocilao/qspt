package com.zxkj.hc.qspt.util;

import java.util.Random;

public class RandomStringUtil {
    private final static Random random=new Random();
    private final static char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private final static int len=chars.length;

    public static String getRandomString(int length){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<length;i++){
            stringBuilder.append(chars[random.nextInt(len)]);
        }
        return stringBuilder.toString();
    }

}
