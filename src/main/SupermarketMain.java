package main;

import main.exception.NegativeQuantityException;
import main.logic.Item;
import main.logic.Store;
import main.promotion.BuyNGet1FreePromotion;
import main.promotion.MealDealPromotion;
import main.promotion.MultiPricedPromotion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


class SupermarketMain {

    public static void main(String[] args) throws NegativeQuantityException {

        Store myStore = new Store();

        myStore.addItem(new Item('B', 0.75));
        myStore.addItem(new Item('D', 1.50));
        myStore.addItem(new Item('E', 2.00));
        myStore.addItem(new Item('A', 0.50));
        myStore.addItem(new Item('C', 0.25));

        myStore.setPromotionQuantity('B', 2);
        myStore.setMultiPricedPromotion('B', new MultiPricedPromotion(2, 1.25));

        myStore.setPromotionQuantity('C', 3);
        myStore.setBuyNGet1FreePromotion('C', new BuyNGet1FreePromotion(3, 3));

        myStore.setMealDealPromotion(Arrays.asList('A', 'C'),
                new MealDealPromotion(0.2, Arrays.asList('A', 'C')));

        Map<Character, Integer> basket = new HashMap<>();
        basket.put('A', 1);
        basket.put('C', 111);


        double totalCost = myStore.getTotalCost(basket);
        System.out.println("The total cost of the basket is: £" + totalCost);
    }
}