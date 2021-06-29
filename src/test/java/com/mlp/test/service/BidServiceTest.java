package com.mlp.test.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author Bill Tu
 * @Time 2021-06-25
 */
public class BidServiceTest {
    private int[][] bids;

    @Before
    public void setup() {
        bids = new int[][]{
                {3, 7, 5, 1},
                {2, 7, 8, 1},
                {1, 5, 5, 0},
                {4, 10, 3, 3}
        };
    }

    @Test
    public void allotted2ExpectedShares01() {
        int[] userIds = BidService.getUnallottedUsers(bids, 7);
        Assert.assertEquals(3, userIds.length);
        Assert.assertEquals(1, userIds[0]);
        Assert.assertEquals(3, userIds[1]);
        Assert.assertEquals(4, userIds[2]);
    }

    @Test
    public void allotted2ExpectedShares02() {
        int[] userIds = BidService.getUnallottedUsers(bids, 8);
        Assert.assertEquals(2, userIds.length);
        Assert.assertEquals(3, userIds[0]);
        Assert.assertEquals(4, userIds[1]);
    }

    @Test
    public void allotted2ExpectedShares03() {
        int[] userIds = BidService.getUnallottedUsers(bids, 12);
        Assert.assertEquals(1, userIds.length);
        Assert.assertEquals(4, userIds[0]);
    }

    @Test
    public void allotted2ExpectedShares04() {
        int[] userIds = BidService.getUnallottedUsers(bids, 19);
        Assert.assertEquals(1, userIds.length);
        Assert.assertEquals(4, userIds[0]);
    }

    @Test
    public void allotted2ExpectedShares05() {
        int[] userIds = BidService.getUnallottedUsers(bids, 20);
        Assert.assertEquals(0, userIds.length);
    }
}
