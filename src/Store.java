import promotion.BuyNGet1FreePromotion;
import promotion.MultiPricedPromotion;

import java.util.ArrayList;
import java.util.Map;

class Store {
    private ArrayList<Item> items;

    public Store() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
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

    public double getTotalCost(Map<Character, Integer> basket) {
        double totalCost = 0.0;
        for (Map.Entry<Character, Integer> entry : basket.entrySet()) {
            Item item = getItem(entry.getKey());
            if (item != null) {
                int quantity = entry.getValue();
                double price = item.getPrice();

                if (item.hasBuyNGet1FreePromotion()) {
                    int promotionQuantity = item.getPromotionQuantity();
//                    int triggerQuantity = item.getBuyNGet1FreePromotion().getTriggerQuantity();
                    int freeQuantity = item.getBuyNGet1FreePromotion().getFreeQuantity();
                    if (quantity >= promotionQuantity) {
                        int fullPromotions = quantity / promotionQuantity;
//                        int remainingQuantity = quantity % promotionQuantity;
//                        int freeItems = fullPromotions / triggerQuantity;
//                        int finalQuantity = remainingQuantity + fullPromotions - freeItems * freeQuantity;
                        int finalQuantity = quantity - (fullPromotions * freeQuantity);
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
                } else {
                    totalCost += quantity * price;
                }
            }
        }
        return totalCost;
    }

}