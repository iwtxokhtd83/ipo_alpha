package com.mlp.test.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Bill Tu
 * @Time 2021-06-25
 */
public class BidServiceTest {
    private List<List<Integer>> bids;

    @Before
    public void setup() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(3);
        list1.add(7);
        list1.add(5);
        list1.add(1);

        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(7);
        list2.add(8);
        list2.add(1);

        List<Integer> list3 = new ArrayList<>();
        list3.add(1);
        list3.add(5);
        list3.add(5);
        list3.add(0);

        List<Integer> list4 = new ArrayList<>();
        list4.add(4);
        list4.add(10);
        list4.add(3);
        list4.add(3);

        bids = new ArrayList<>();
        bids.add(list1);
        bids.add(list2);
        bids.add(list3);
        bids.add(list4);
    }

    @Test
    public void allotted2ExpectedShares01() {
        List<Integer> userIds = BidService.getUnallottedUsers(bids, 7);
        Assert.assertEquals(3, userIds.size());
        Assert.assertEquals(1, (int) userIds.get(0));
        Assert.assertEquals(3, (int) userIds.get(1));
        Assert.assertEquals(4, (int) userIds.get(2));
    }

    @Test
    public void allotted2ExpectedShares02() {
        List<Integer> userIds = BidService.getUnallottedUsers(bids, 8);
        Assert.assertEquals(2, userIds.size());
        Assert.assertEquals(3, (int) userIds.get(0));
        Assert.assertEquals(4, (int) userIds.get(1));
    }

    @Test
    public void allotted2ExpectedShares03() {
        List<Integer> userIds = BidService.getUnallottedUsers(bids, 12);
        Assert.assertEquals(1, userIds.size());
        Assert.assertEquals(4, (int) userIds.get(0));
    }

    @Test
    public void allotted2ExpectedShares04() {
        List<Integer> userIds = BidService.getUnallottedUsers(bids, 19);
        Assert.assertEquals(1, userIds.size());
        Assert.assertEquals(4, (int) userIds.get(0));
    }

    @Test
    public void allotted2ExpectedShares05() {
        List<Integer> userIds = BidService.getUnallottedUsers(bids, 20);
        Assert.assertEquals(0, userIds.size());
    }
}
