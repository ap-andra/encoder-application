package org.encoder.function;

import org.encoder.exception.InputFormatException;

public class Encoder {
    public static String encode(char[] input) {
        validateInputFormat(input);

        var result = new StringBuilder();
        int i = 0;

        while (i < input.length) {
            char current = input[i];
            int count = 0;
            while (i < input.length && input[i] == current) {
                count++;
                i++;
            }
            if (Character.isDigit(current) || Character.isSpaceChar(current)) {
                result.append('[').append(current).append(']').append(count);
            } else {
                result.append(current).append(count);
            }
        }

        return result.toString();
    }

    private static void validateInputFormat(char[] input) {
        if (null == input) {
            throw new InputFormatException("NULL input format is invalid to be encoded!");
        }
        if (input.length == 0 || new String(input).isBlank()) {
            throw new InputFormatException("Blank input format is invalid to be encoded!");
        }
    }

}
