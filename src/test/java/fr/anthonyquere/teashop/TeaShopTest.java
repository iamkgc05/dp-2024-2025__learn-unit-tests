package fr.anthonyquere.teashop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TeaShopTest {


    @Test
    void should_throw_exception_when_tea_is_not_available() {
        // Arrange
        TeaShop shop = new TeaShop(85);
        String nonExistentTea = "larmes de licornes";

        // Act & Assert
        assertThatThrownBy(() -> shop.prepareTea(nonExistentTea))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tea not available");
    }
    @Test
    void should_prepare_tea_cup_with_correct_tea_when_available() {
        TeaShop shop = new TeaShop(85);
        Tea tea = new Tea("larmes de licornes", 180, 85, true);
        shop.addTea(tea);

        TeaCup cup = shop.prepareTea("larmes de licornes");

        // Assert
        assertThat(cup).isNotNull();
    }

    @Test
    void should_prepare_tea_with_shop_default_water_temperature() {
        int defaultTemp = 85;
        TeaShop shop = new TeaShop(defaultTemp);
        Tea tea = new Tea("larmes de licornes", 180, defaultTemp, true);
        shop.addTea(tea);

        TeaCup cup = shop.prepareTea("larmes de licornes");

        assertThat(cup).isNotNull();
        assertThat(cup.isReadyToDrink()).isFalse();
    }

    @Test
    void should_throw_exception_when_setting_invalid_water_temperature() {
        // Test that verifies an exception is thrown when setting water temperature outside 0-100°C
        int Temp = 80;
        TeaShop shop = new TeaShop(Temp);

        assertThatThrownBy(() -> shop.setWaterTemperature(-15))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Water temperature must be between 0 and 100°C");

        assertThatThrownBy(() -> shop.setWaterTemperature(120))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Water temperature must be between 0 and 100°C");

    }

    @Test
    void should_throw_exception_when_adding_tea_to_empty_cup() {
        // Arrange
        var cup = new TeaCup();
        var tea = new Tea("Oolong", 150, 85, true);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                cup.addTea(tea)
        );

        // Using AssertJ for message verification
        assertThat(exception.getMessage()).contains("empty cup");
    }
}