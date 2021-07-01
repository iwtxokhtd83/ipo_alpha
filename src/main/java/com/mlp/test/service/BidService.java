package com.mlp.test.service;

import java.util.*;

/**
 * @Author Bill Tu
 * @Time 2021-06-25
 */
public class BidService {
    public static List<Integer> getUnallottedUsers(List<List<Integer>> bids, int totalShares) {
        List<Integer> unAllottedUserList = new ArrayList<>();

        List<Bidder> allBidders = new ArrayList<>();
        //<Price,<UserId,Bidder>
        Map<Integer, Map<Integer, Bidder>> samePriceBidderMap = new HashMap<>();
        //<UserId,no.OfShares>
        Map<Integer, Integer> userOriginalSharesMap = new HashMap<>();

        for (List<Integer> bid : bids) {
            Bidder bidder = new Bidder(bid.get(0), bid.get(1), bid.get(2), bid.get(3));
            allBidders.add(bidder);
            Map<Integer, Bidder> bidderMap;
            int price = bidder.getPrice();
            if (samePriceBidderMap.containsKey(price)) {
                bidderMap = samePriceBidderMap.get(price);
            } else {
                bidderMap = new HashMap<>();
                samePriceBidderMap.put(price, bidderMap);
            }
            bidderMap.put(bidder.getUserId(), bidder);
            userOriginalSharesMap.put(bidder.getUserId(), bidder.getNoOfShares());
        }

        //Sort by price, if price equals, then sort by timestamp in desc
        Collections.sort(allBidders);

        /**
         *List<<Price,NoOfShare>>
         * 8,7
         * 5,5
         * 5,7
         * 3,9
         */
        List<List<Bidder>> sortAndGroupByPriceBidderList = new ArrayList<>();

        Set<Integer> priceAlreadyAddedSet = new HashSet<>();
        for (Bidder bidder : allBidders) {
            int price = bidder.getPrice();
            boolean hasSamePrice = samePriceBidderMap.containsKey(price) && samePriceBidderMap.get(price).keySet().size() > 1;
            List<Bidder> bidders = new ArrayList<>();
            if (!priceAlreadyAddedSet.contains(price)) {
                priceAlreadyAddedSet.add(price);
                if (!hasSamePrice) {
                    bidders.add(bidder);
                    sortAndGroupByPriceBidderList.add(bidders);
                } else {
                    List<Bidder> subBidderList = new ArrayList<>(samePriceBidderMap.get(price).values());
                    sortAndGroupByPriceBidderList.add(subBidderList);
                }
            }
        }

        for (List<Bidder> bidders : sortAndGroupByPriceBidderList) {
            if (totalShares <= 0) {
                break;
            } else {
                //Allocate shares.
                int size = bidders.size();
                if (size == 1) {
                    //Bidder with unique price in the list
                    Bidder bidder = bidders.get(0);
                    int allottedShares = bidder.getNoOfShares() <= totalShares ? bidder.getNoOfShares() : totalShares;
                    bidder.setNoOfShares(bidder.getNoOfShares() - allottedShares);
                    totalShares -= allottedShares;
                } else {
                    //Bidders with same prices in the list
                    boolean goOn = true;
                    int noOfZeroShares = 0;
                    while (goOn && totalShares > 0) {
                        Iterator<Bidder> bidderIterator = bidders.iterator();
                        boolean hasNext = bidderIterator.hasNext();
                        if (!hasNext) {
                            break;
                        }
                        while (bidderIterator.hasNext()) {
                            Bidder bidder = bidderIterator.next();
                            if (totalShares <= 0 || noOfZeroShares == size) {
                                goOn = false;
                                break;
                            }
                            if (bidder.getNoOfShares() <= 0) {
                                noOfZeroShares++;
                                bidderIterator.remove();
                            } else {
                                bidder.setNoOfShares(bidder.getNoOfShares() - 1);
                                totalShares -= 1;
                            }
                        }

                    }
                }
            }
        }

        for (Bidder bidder : allBidders) {
            if (bidder.getNoOfShares() == userOriginalSharesMap.get(bidder.getUserId())) {
                //If the no. of shares is not changed, means it's unAllotted.
                unAllottedUserList.add(bidder.getUserId());
            }
        }

        //Free up memory
        samePriceBidderMap.clear();
        userOriginalSharesMap.clear();
        sortAndGroupByPriceBidderList.clear();
        allBidders.clear();
        priceAlreadyAddedSet.clear();
        return unAllottedUserList;

    }

    static class Bidder implements Comparable<Bidder> {
        private final int userId;
        private final int price;
        private final int timestamp;
        private int noOfShares;

        public Bidder(int userId, int noOfShares, int price, int timestamp) {
            this.userId = userId;
            this.noOfShares = noOfShares;
            this.price = price;
            this.timestamp = timestamp;
        }

        public int getUserId() {
            return userId;
        }

        public int getNoOfShares() {
            return noOfShares;
        }

        public void setNoOfShares(int noOfShares) {
            this.noOfShares = noOfShares;
        }

        public int getPrice() {
            return price;
        }

        public int getTimestamp() {
            return timestamp;
        }


        @Override
        public int compareTo(Bidder o) {
            if (this.getPrice() != o.getPrice()) {
                return o.getPrice() - this.getPrice();
            } else {
                return this.getTimestamp() - o.getTimestamp();
            }
        }
    }

}
