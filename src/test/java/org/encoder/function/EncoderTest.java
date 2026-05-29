package org.encoder.function;

import org.encoder.exception.InputFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EncoderTest {

    @Test
    @DisplayName("Throws InputFormatException for null input")
    void throwsForNullInput() {
        var exception = assertThrows(InputFormatException.class,
                () -> Encoder.encode(null));
        assertEquals(Encoder.NULL_INPUT_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Throws InputFormatException for empty char array")
    void throwsForEmptyInput() {
        var exception = assertThrows(InputFormatException.class,
                () -> Encoder.encode(new char[0]));
        assertEquals(Encoder.BLANK_INPUT_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "     ", "\t", "\n", "  \t\n  "})
    @DisplayName("Throws InputFormatException for blank input")
    void throwsForBlankInput(String input) {
        var exception = assertThrows(InputFormatException.class,
                () -> Encoder.encode(input.toCharArray()));
        assertEquals(Encoder.BLANK_INPUT_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Encodes a single letter as letter followed by 1")
    void encodeSingleLetter() {
        assertEquals("a1", Encoder.encode("a".toCharArray()));
    }

    @Test
    @DisplayName("Encodes a single digit wrapped in brackets followed by 1")
    void encodeSingleDigit() {
        assertEquals("[1]1", Encoder.encode("1".toCharArray()));
    }

    @Test
    @DisplayName("Encodes two identical letters")
    void encodeTwoIdenticalLetters() {
        assertEquals("a2", Encoder.encode("aa".toCharArray()));
    }

    @Test
    @DisplayName("Encodes a long run of identical letters with count greater than 9")
    void encodeLongRunOfLetters() {
        assertEquals("a11", Encoder.encode("aaaaaaaaaaa".toCharArray()));
    }

    @Test
    @DisplayName("Encodes consecutive identical digits with brackets")
    void encodeRepeatedDigits() {
        assertEquals("[2]2", Encoder.encode("22".toCharArray()));
    }

    @ParameterizedTest
    @CsvSource({
            "abbbcdddd, a1b3c1d4",
            "aaaabbbcccaaa, a4b3c3a3"
    })
    @DisplayName("Encodes consecutive groups of letters")
    void encodeConsecutiveLetterGroups(String input, String expected) {
        assertEquals(expected, Encoder.encode(input.toCharArray()));
    }

    @Test
    @DisplayName("Encodes mixed letters and digits with bracket wrapping for digits")
    void encodeMixedLettersAndDigits() {
        assertEquals("a5[2]2b3", Encoder.encode("aaaaa22bbb".toCharArray()));
    }

    @Test
    @DisplayName("Encodes letters, a single digit, and special characters")
    void encodeLettersDigitsAndSpecialCharacters() {
        assertEquals("a4b3[0]1c3-3a3", Encoder.encode("aaaabbb0ccc---aaa".toCharArray()));
    }

    @Test
    @DisplayName("Encodes long letter run followed by multiple distinct digit groups")
    void encodeLongRunFollowedByDigitGroups() {
        assertEquals("a11[1]1[2]2", Encoder.encode("aaaaaaaaaaa122".toCharArray()));
    }

    @Test
    @DisplayName("Encodes spaces in brackets when mixed with non-blank characters")
    void encodeSpacesWithOtherCharacters() {
        assertEquals("[ ]3/1n1", Encoder.encode("   /n".toCharArray()));
    }

}