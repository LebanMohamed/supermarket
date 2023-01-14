package logic;

import promotion.BuyNGet1FreePromotion;
import promotion.MealDealPromotion;
import promotion.MultiPricedPromotion;

import java.util.*;

public class Store {
    private ArrayList<Item> items;

    private Map<Character, Item> itemMap;
    Set<Character> appliedPromos;

    public Store() {
        items = new ArrayList<>();
        itemMap = new HashMap<>();
        appliedPromos = new HashSet<>();
    }

    public void addItem(Item item) {
        items.add(item);
        itemMap.put(item.getSku(), item);
    }

    public Item getItem(char sku) {
        for (Item item : items) {
            if (item.getSku() == sku) {
                return item;
            }
        }
        return null;
    }

    public void setPromotionQuantity(char sku, int promotionQuantity) {
        Item item = getItem(sku);
        if (item != null) {
            item.setPromotionQuantity(promotionQuantity);
        }
    }

    public void setBuyNGet1FreePromotion(char sku, BuyNGet1FreePromotion promotion) {
        Item item = getItem(sku);
        if (item != null) {
            item.setBuyNGet1FreePromotion(promotion);
        }
    }

    public void setMultiPricedPromotion(char sku, MultiPricedPromotion promotion) {
        Item item = getItem(sku);
        if (item != null) {
            item.setMultiPricedPromotion(promotion);
        }
    }

    public void setMealDealPromotion(List<Character> dealItems, MealDealPromotion promotion) {
        for (Character dealItem : dealItems) {
            Item item = itemMap.get(dealItem);
            if (item != null) {
                item.setMealDealPromotion(promotion);
            }
        }
    }

    public double getTotalCost(Map<Character, Integer> basket) {
        double totalCost = 0.0;
        for (Map.Entry<Character, Integer> entry : basket.entrySet()) {
            Item item = getItem(entry.getKey());
            if (item != null) {
                int quantity = entry.getValue();
                double price = item.getPrice();
                MealDealPromotion itemPromo = item.getMealDealPromotion();

                if (item.hasBuyNGet1FreePromotion()) {
                    int promotionQuantity = item.getPromotionQuantity();
                    if (quantity >= promotionQuantity) {
                        int fullPromotions = quantity / promotionQuantity;
                        int finalQuantity = quantity - fullPromotions;
                        totalCost += finalQuantity * price;
                    } else {
                        totalCost += quantity * price;
                    }
                } else if (item.hasMultiPricedPromotion()) {
                    int promotionQuantity = item.getPromotionQuantity();
                    double promotionPrice = item.getMultiPricedPromotion().getPromotionPrice();
                    if (quantity >= promotionQuantity) {
                        int fullPromotions = quantity / promotionQuantity;
                        int remainingQuantity = quantity % promotionQuantity;
                        totalCost += fullPromotions * promotionPrice + remainingQuantity * price;
                    } else {
                        totalCost += quantity * price;
                    }
                } else if (item.hasMealDealPromotion() && !appliedPromos.contains(entry.getKey())) {
                    List<Character> dealItems = item.getMealDealPromotion().getItems();
                    double dealPrice = item.getMealDealPromotion().getPromotionPrice();
                    boolean hasAllItems = true;
                    for (char dealItem : dealItems) {
                        if (!basket.containsKey(dealItem) || basket.get(dealItem) < 1) {
                            hasAllItems = false;
                            break;
                        }
                    }
                    if (hasAllItems) {
                        for (char dealItem : dealItems) {
                            basket.put(dealItem, basket.get(dealItem) - 1);
                        }
                        appliedPromos.add(entry.getKey());
                        totalCost += dealPrice;
                    } else {
                        totalCost += quantity * price;
                    }
                }
                else {
                    totalCost += quantity * price;
                }
            }
        }
        return totalCost;
    }

}