package org.encoder;

import org.encoder.function.Encoder;

public class Main {
    static void main() {

        var input1 = "";
        var input2 = "     ";
        var input3 = "   /n";
        var input4 = "abbbcdddd";
        var input5 = "aaaabbbcccaaa";
        var input6 = "aaaabbb0ccc---aaa";
        var input7 = "a";
        var input8 = "aa";
        var input9 = "1";
        var input10 = "aaaaa22bbb";
        var input11 = "aaaaaaaaaaa122";

        String[] inputs = {null, input1, input2, input3, input4, input5, input6, input7, input8, input9, input10, input11};

        for (String input : inputs) {
            try {
                System.out.println(Encoder.encode(input.toCharArray()));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
