import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    private void createMockRestaurant() {
        LocalTime timeToOpen = LocalTime.parse("10:30:00");
        LocalTime timeToClose = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",timeToOpen,timeToClose);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        createMockRestaurant();
        assertEquals(restaurant.getName(), service.findRestaurantByName("Amelie's cafe").getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        //WRITE UNIT TEST CASE HERE
        createMockRestaurant();
        assertThrows(restaurantNotFoundException.class, () -> {service.findRestaurantByName("not cafe baraco").getName()});
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        createMockRestaurant();
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        createMockRestaurant();
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        createMockRestaurant();
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void select_items_from_menu_and_total_cost_should_be_the_sum_of_cost_of_individual_items(){
        createMockRestaurant();
        List<String> order = new ArrayList<String>();
        order.add("Sweet corn soup");
        order.add("Vegetable lasagne");
        int totalCost = restaurant.getTotalCost();
        assertEquals(388,totalCost);
    }

    @Test
    public void when_no_item_is_selected_from_menu_and_total_cost_should_be_zero(){
        createMockRestaurant();
        List<String> order = new ArrayList<String>();
        int totalCost = restaurant.getTotalCost(order);
        assertEquals(0,totalCost);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<CALCULATE TOTAL COST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}