package com.mlp.test.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Bill Tu
 * @2021-07-01
 */
public final class StringUtils {
    private StringUtils() {

    }

    public static int lengthOfLongestSubString(String s) {

        Map<String, Integer> alreadyAppearedMap = new HashMap<>();
        char[] strCharArray = s.toCharArray();
        int charArrayLen = strCharArray.length;

        int startIndex = 0;
        int longestLength = 0;

        for (int endIndex = 0; endIndex < charArrayLen; endIndex++) {
            String chStr = Character.toString(strCharArray[endIndex]);
            //Already visited
            if (alreadyAppearedMap.containsKey(chStr)) {
                startIndex = Math.max(startIndex, alreadyAppearedMap.get(chStr) + 1);

            }
            //Remember the last visited character
            alreadyAppearedMap.put(chStr, endIndex);
            longestLength = Math.max(longestLength, endIndex - startIndex + 1);
        }
        //Free up memory
        alreadyAppearedMap.clear();
        return longestLength;
    }
}
