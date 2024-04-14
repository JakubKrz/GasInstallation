/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;



/**
 *Test class for Model class 
 * 
 * @author Jakub Krzywoń
 * @version 1.1
 */
public class ModelTest {
    
    private Model model;
    
    /**
     * Tests the updatePressure method with an invalid pressure value.
     * Expects a PressureOverflowException to be thrown.
     */
    @Test
    public void testUpdatePressure()
    {
      model = new Model();
      this.model.setPressure(this.model.getMaxAllowedPressure() + 1);
      try
      {
          model.updatePressure();
          fail("An exception should be thrown when the pressure is higher than max allowed value");
      } catch (PressureOverflowException e) {
      }
          
    }
    
    /**
     * Provides arguments for testing getMaxPressure method.
     * 
     * @return Stream of arguments containing test cases and expected results.
     */
    private static Stream<Arguments> providePressureHistoryForMaxPressure() {
        return Stream.of(
                Arguments.of("Doesn't chose max value from list (1,2,3)",new ArrayList(Arrays.asList(1.0, 2.0, 3.0)), 3.0),
                Arguments.of("Doesn't chose max value from list (0)",new ArrayList(Arrays.asList(0.0)), 0.0),
                Arguments.of("Doesn't return -1 when ArrayList is empty", new ArrayList<Double>(), -1.0)
        );
    }
    
    /**
     * Tests the getMaxPressure method with various pressureHistory values .
     * 
     * @param message Test case description.
     * @param pressureHistory List of pressure values.
     * @param expectedMax Expected maximum pressure value.
     */
    @ParameterizedTest
    @MethodSource("providePressureHistoryForMaxPressure")
    public void testGetMaxPressure( String message, ArrayList<Double> pressureHistory, double expectedMax) {
        model = new Model();
        model.setPressureHistory(pressureHistory);
        double maxPressure = model.getMaxPressure();
        assertEquals(expectedMax, maxPressure, message);
    }

    /**
     * Provides arguments for testing getMinPressure method.
     * 
     * @return Stream of arguments containing test cases and expected results.
     */   
    private static Stream<Arguments> providePressureHistoryForMinPressure() {
        return Stream.of(
            Arguments.of("Doesn't chose min value from list (1,2,3)", new ArrayList(Arrays.asList(1.0, 2.0, 3.0)), 1.0),
            Arguments.of("Doesn't chose min value from list (0)", new ArrayList(Arrays.asList(0.0)), 0.0),
            Arguments.of("Doesn't return -1 when ArrayList is empty", new ArrayList<Double>(), -1.0)
        );
    }
    
    /**
     * Tests the getMinPressure method with various pressureHistory values .
     * 
     * @param message Test case description.
     * @param pressureHistory List of pressure values.
     * @param expectedMin Expected minimum pressure value.
     */
    @ParameterizedTest
    @MethodSource("providePressureHistoryForMinPressure")
    public void testGetMinPressure(String message, ArrayList<Double> pressureHistory, double expectedMin) {
        model = new Model();
        model.setPressureHistory(pressureHistory);
        double minPressure = model.getMinPressure();
        assertEquals(expectedMin, minPressure, message);
    }
    
    /**
     * Provides arguments for testing getAveragePressure method.
     * 
     * @return Stream of arguments containing test cases and expected results.
     */
    private static Stream<Arguments> providePressureHistoryForGetAveragePressure() {
        return Stream.of(
            Arguments.of("Caltulates wrong average from list (1,2,3)", new ArrayList(Arrays.asList(1.0, 2.0, 3.0)), 2.0),
            Arguments.of("Caltulates wrong average from list (0)", new ArrayList(Arrays.asList(0.0)), 0.0),
            Arguments.of("Doesn't return -1 when ArrayList is empty", new ArrayList<Double>(), -1.0)
        );
    }
    
    /**
     * Tests the getAveragePressure method with various pressureHistory values .
     * 
     * @param message Test case description.
     * @param pressureHistory List of pressure values.
     * @param expectedAverage Expected average pressure value.
     */
    @ParameterizedTest
    @MethodSource("providePressureHistoryForGetAveragePressure")
    public void testGetAveragePressure(String message, ArrayList<Double> pressureHistory, double expectedAverage) {
        model = new Model();
        model.setPressureHistory(pressureHistory);
        double averagePressure = model.getAveragePressure();
        assertEquals(expectedAverage, averagePressure, message);
    }
}