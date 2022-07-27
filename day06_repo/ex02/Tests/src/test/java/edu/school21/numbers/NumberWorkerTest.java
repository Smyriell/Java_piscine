package edu.school21.numbers;

import edu.school21.numbers.IllegalNumberException;
import edu.school21.numbers.NumberWorker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {

    NumberWorker entity = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 11, 19, 113, 3361})
    void isPrimeForPrimes(int number) {
        Assertions.assertTrue(entity.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {15, 144, 80, 9999})
    void isPrimeForNotPrimes(int number) {
        Assertions.assertFalse(entity.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -5, -100, Integer.MIN_VALUE})
    void isPrimeForIncorrectNumbers(int number) {
        Assertions.assertThrows(IllegalNumberException.class, () -> entity.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/data.csv"}, delimiter = ',', numLinesToSkip = 1)
    void checkDigitsSum(int num, int expected) {
        Assertions.assertEquals(expected, entity.digitsSum(num));
    }
}