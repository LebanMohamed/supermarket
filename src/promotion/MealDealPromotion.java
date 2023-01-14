package promotion;

import java.util.*;

public class MealDealPromotion {
    private double promotionPrice;
    private List<Character> items;

    public MealDealPromotion(double promotionPrice, List<Character> items) {
        this.promotionPrice = promotionPrice;
        this.items = items;
    }

    public double getPromotionPrice() {
        return promotionPrice;
    }

    public List<Character> getItems() {
        return items;
    }


}