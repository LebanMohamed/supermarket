package main.logic;

import main.exception.BasketNegativeException;
import main.exception.NegativePromotionQuantityException;
import main.exception.NegativeQuantityException;
import main.promotion.BuyNGet1FreePromotion;
import main.promotion.MealDealPromotion;
import main.promotion.MultiPricedPromotion;

import java.util.*;

import static main.util.SuperMarketStaticText.*;

public class Store {
    private ArrayList<Item> items;

    private Map<Character, Item> itemMap;

    public Store() {
        items = new ArrayList<>();
        itemMap = new HashMap<>();
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

    public void setPromotionQuantity(char sku, int promotionQuantity) throws NegativePromotionQuantityException {
        if (promotionQuantity < 0)
            throw new NegativePromotionQuantityException(PROMOTION_QUANTITY_ZERO_EXCEPTION_MESSAGE);

        Item item = getItem(sku);

        if (item != null) {
            item.setPromotionQuantity(promotionQuantity);
        }
    }

    public void setBuyNGet1FreePromotion(char sku, BuyNGet1FreePromotion promotion) throws NegativePromotionQuantityException {
        if (promotion.getPromotionQuantity() < 0)
            throw new NegativePromotionQuantityException(BUY1_GET_ONE_FREE_ZERO_EXCEPTION_MESSAGE);

        Item item = getItem(sku);

        if (item != null) {
            item.setBuyNGet1FreePromotion(promotion);
        }
    }

    public void setMultiPricedPromotion(char sku, MultiPricedPromotion promotion) throws NegativePromotionQuantityException {
        if (promotion.getPromotionQuantity() < 0)
            throw new NegativePromotionQuantityException(MULTI_PRICED_ZERO_EXCEPTION_MESSAGE);

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

    public double getTotalCost(Map<Character, Integer> basket) throws NegativeQuantityException, BasketNegativeException {

        checkBasketNegative(basket);

        double totalCost = 0.0;
        Set<Character> appliedPromos = new HashSet<>();

        for (Map.Entry<Character, Integer> entry : basket.entrySet()) {
            Item item = getItem(entry.getKey());
            if (item != null) {
                int quantity = entry.getValue();
                double price = item.getPrice();
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

                    for (char dealItem : dealItems) {
                        basket.put(dealItem, basket.get(dealItem) - 1);
                        appliedPromos.add(dealItem);
                    }

                    totalCost += dealPrice;
                } else {
                    totalCost += quantity * price;
                }
            }
        }

        if (totalCost < 0)
            throw new NegativeQuantityException(TOTAL_COST_ZERO_EXCEPTION_MESSAGE);

        return totalCost;
    }

    private void checkBasketNegative(Map<Character, Integer> basket) throws BasketNegativeException {
        for (Map.Entry<Character, Integer> basketItems : basket.entrySet())
            if (basketItems.getValue() <= 0)
                throw new BasketNegativeException(BASKET_ZERO_EXCEPTION_MESSAGE);
    }

}
