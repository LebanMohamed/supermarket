import logic.Item;
import logic.Store;
import promotion.BuyNGet1FreePromotion;
import promotion.MealDealPromotion;
import promotion.MultiPricedPromotion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Supermarket {

    public static void main(String[] args) {

        Store myStore = new Store();

        myStore.addItem(new Item('A', 0.50));
        myStore.addItem(new Item('B', 0.75));
        myStore.addItem(new Item('C', 0.25));
        myStore.addItem(new Item('D', 1.50));
        myStore.addItem(new Item('E', 2.00));

        myStore.setPromotionQuantity('B', 2);
        myStore.setMultiPricedPromotion('B', new MultiPricedPromotion(2, 1.25));

        myStore.setPromotionQuantity('C', 3);
        myStore.setBuyNGet1FreePromotion('C', new BuyNGet1FreePromotion(3, 3));

        myStore.setMealDealPromotion(Arrays.asList('D', 'E'),
                new MealDealPromotion(3.00, Arrays.asList('D', 'E')));

        Map<Character, Integer> basket = new HashMap<>();
        basket.put('D', 1);
        basket.put('E', 1);
        basket.put('C', 3);
        basket.put('B', 2);

        double totalCost = myStore.getTotalCost(basket);
        System.out.println("The total cost of the basket is: £" + totalCost);

    }
}