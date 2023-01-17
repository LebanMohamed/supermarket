package test;

import main.exception.NegativeQuantityException;
import main.logic.Item;
import main.logic.Store;
import main.promotion.BuyNGet1FreePromotion;
import main.promotion.MealDealPromotion;
import main.promotion.MultiPricedPromotion;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SupermarketMainTest {
    private Store myStore;

    @Before
    public void setUp() {
        myStore = new Store();

        myStore.addItem(new Item('A', 0.50));
        myStore.addItem(new Item('C', 0.25));
        myStore.addItem(new Item('B', 0.50));
        myStore.addItem(new Item('D', 2.50));
    }


    @Test
    public void testGetTotal_hasBuyNGet1FreePromotion() throws NegativeQuantityException {
        setUp();
        myStore.setBuyNGet1FreePromotion('A', new BuyNGet1FreePromotion(3, 3));

        Map<Character, Integer> basket = new HashMap<>();
        basket.put('A', 3);

        double expectedTotal = 1.00;
        double actualTotal = myStore.getTotalCost(basket);
        assertEquals(expectedTotal, actualTotal, 0.001);
    }

    @Test
    public void testGetTotal_multiPriced() throws NegativeQuantityException {
        setUp();
        myStore.setPromotionQuantity('B', 2);
        myStore.setMultiPricedPromotion('B', new MultiPricedPromotion(2, 1.25));

        Map<Character, Integer> basket = new HashMap<>();
        basket.put('B', 3);

        double expectedCost = 1.75;
        double actualCost = myStore.getTotalCost(basket);
        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void testGetTotal_mealDealPromotion() throws NegativeQuantityException {
        setUp();
        myStore.setMealDealPromotion(Arrays.asList('A', 'C'),
                new MealDealPromotion(0.2, Arrays.asList('A', 'C')));

        Map<Character, Integer> basket = new HashMap<>();
        basket.put('A', 1);
        basket.put('C', 1);

        double expectedTotalCost = 0.2;
        double actualTotalCost = myStore.getTotalCost(basket);
        assertEquals(expectedTotalCost, actualTotalCost, 0.001);
    }

    @Test
    public void testGetTotal_mealDealPromotionMultipleCharacters() throws NegativeQuantityException {
        setUp();
        myStore.setMealDealPromotion(Arrays.asList('A', 'C'), new MealDealPromotion(0.2, Arrays.asList('A', 'C')));

        Map<Character, Integer> basket = new HashMap<>();
        basket.put('A', 1);
        basket.put('C', 111);

        double expectedTotalCost = 27.7;
        double actualTotalCost = myStore.getTotalCost(basket);
        assertEquals(expectedTotalCost, actualTotalCost, 0.001);
    }

    @Test
    public void testGetTotal_allPromotionCombination() throws NegativeQuantityException {
        setUp();
        myStore.setMealDealPromotion(Arrays.asList('A', 'C'), new MealDealPromotion(0.2, Arrays.asList('A', 'C')));

        myStore.setPromotionQuantity('B', 2);
        myStore.setMultiPricedPromotion('B', new MultiPricedPromotion(2, 1.25));

        myStore.setPromotionQuantity('D', 3);
        myStore.setBuyNGet1FreePromotion('D', new BuyNGet1FreePromotion(3, 3));

        Map<Character, Integer> basket = new HashMap<>();
        basket.put('A', 1);
        basket.put('C', 111);
        basket.put('B', 3);
        basket.put('D', 3);

        double expectedTotalCost = 34.45;
        double actualTotalCost = myStore.getTotalCost(basket);
        assertEquals(expectedTotalCost, actualTotalCost, 0.001);
    }

}