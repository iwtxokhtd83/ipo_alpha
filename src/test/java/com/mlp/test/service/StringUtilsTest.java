package com.mlp.test.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Bill Tu
 * @2021-07-01
 */
public class StringUtilsTest {

    @Test
    public void testSubString01() {
        String str = "aaaa";
        int longestLen = StringUtils.lengthOfLongestSubString(str);
        Assert.assertEquals(1, longestLen);
    }

    @Test
    public void testSubString02() {
        String str = "abdefgabef";
        int longestLen = StringUtils.lengthOfLongestSubString(str);
        Assert.assertEquals(6, longestLen);
    }

    @Test
    public void testSubString03() {
        String str = "testfortest";
        int longestLen = StringUtils.lengthOfLongestSubString(str);
        Assert.assertEquals(6, longestLen);
    }
}
