/*
edX
"Learn to Program in Java" by Microsoft: DEV276x

Module Project - Crypto
For this project you will be writing methods of encrypting and decrypting text.

Part 1 - Normalize Text
The first thing we will do is normalize our input message so that it’s easier to work with.

Write a method called normalizeText() which does the following:

1. Removes all the spaces from your text
2. Remove any punctuation (. , : ; ’ ” ! ? ( ) )
3. Turn all lower-case letters into upper-case letters
4. Return the result.
The call

normalizeText("This is some \"really\" great. (Text)!?")
should return

“THISISSOMEREALLYGREATTEXT”
Part 2 - Caesar Cipher
Next we’ll be writing a Caesar Cipher. The Caesar cipher is just about the simplest encryption algorithm out there. A Caesar encription "shifts" each individual character forward by a certain number or "key". Each letter in the alphabet is shifted to the letter in the alphabet that is "key" places past the original letter. With a shift value of +1, the string “ILIKEZOOS” would be rendered as “JMJLFAPPT.”

Write a method called caesarify that takes two parameters. The first argument is a string you want to encrypt, and the second is an integer that contains the shift value or "key". The function should return a string, which is the input string encrypted with the Caesar cypher using the shift value passed in its second argument. You may assume that the input string is normalized.

Note that the alphabet “wraps around”, so with a shift value of +1 the “Z” in ZOOS became an A.
You can also have negative shift values, which cause the alphabet to previous letters. With a -1 shift, the string “ILIKEAPPLES” would turn into “HKHJDZOOKDR.”
We will provide you with a function called shiftAlphabet. This function takes one argument, an integer to specify the shift value, and returns a string, which is the uppercase alphabet shifted by the shift value. So if you call shiftAlphabet(2), you will get back the following string: “CDEFGHIJKLMNOPQRSTUVWXYZAB”

Here is the implementation for shiftAlphabet, which you can just paste into your java file:

public static String shiftAlphabet(int shift) {
    int start = 0;
    if (shift < 0) {
        start = (int) 'Z' + shift + 1;
    } else {
        start = 'A' + shift;
    }
    String result = "";
    char currChar = (char) start;
    for(; currChar <= 'Z'; ++currChar) {
        result = result + currChar;
    }
    if(result.length() < 26) {
        for(currChar = 'A'; result.length() < 26; ++currChar) {
            result = result + currChar;
        }
    }
    return result;
}
Part 3 - Codegroups
Traditionally, encrypted messages are broken into equal-length chunks, separated by spaces and called “code groups.”

Write a method called groupify which takes two parameters. The first parameter is the string that you want to break into groups. The second argument is the number of letters per group. The function will return a string, which consists of the input string broken into groups with the number of letters specified by the second argument. If there aren’t enough letters in the input string to fill out all the groups, you should “pad” the final group with x’s. So groupify(“HITHERE”, 2) would return “HI TH ER Ex”.

You may assume that the input string is normalized.
Note that we use lower-case ‘x’ here because it is not a member of the (upper-case) alphabet we’re working with. If we used upper-case ‘X’ here we would not be able to distinguish between an X that was part of the code and a padding X.
Part 4 - Putting it all together
Write a function called encryptString which takes three parameters: a string to be encrypted, an integer shift value, and a code group size. Your method should return a string which is its cyphertext equivalent. Your function should do the following:

Call normalizeText on the input string.
Call obify to obfuscate the normalized text.
Call caesarify to encrypt the obfuscated text.
Call groupify to break the cyphertext into groups of size letters.
Return the result
Part 5 - Hacker Problem - Decrypt
This part is not required for course credit.

Write a method called ungroupify which takes one parameter, a string containing space-separated groups, and returns the string without any spaces. So if you call ungroupify(“THI SIS ARE ALL YGR EAT SEN TEN CEx”) you will return “THISISAREALLYGREATSENTENCE”

Now write a function called decryptString which takes three parameters: a string to be decrypted and the integer shift value used to encrypt the string, and returns a string which contains the (normalized) plaintext. You can assume the string was encrypted by a call to encryptString().

So if you were to call

String cyphertext = encryptString(“Who will win the election?”,  5, 3);
String plaintext = decryptString(cyphertext, 5);
… then you’ll get back the normalized input string “WHOWILLWINTHEELECTION”.
 */

import java.util.Scanner;

public class Crypto {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a text to encrypt: ");
        String text = input.nextLine();
        System.out.print("Enter a key (an integer) for the encryption: ");
        int key = input.nextInt();
        System.out.print("How many characters should there be per group? Enter an integer: ");
        int noOfLettersPerGroup = input.nextInt();

        String groupedEncryptedText = encryptString(text, key, noOfLettersPerGroup);
        System.out.println(groupedEncryptedText);

        String decryptedText = decryptString(ungroupify(groupedEncryptedText), key);
        System.out.print("Decrypted text: ");
        System.out.println(decryptedText);
    }

    public static String encryptString(String text, int key, int noOfLettersPerGroup) {
        text = normalizeText(text);
        String encryptedText = caesarify(text, key);
        String groupedEncryptedText = groupify(encryptedText, noOfLettersPerGroup);

        return groupedEncryptedText;
    }

    public static String normalizeText(String text) {
        text = text.replace(" ", "");
        text = text.replace(".", "");
        text = text.replace(",", "");
        text = text.replace(";", "");
        text = text.replace(":", "");
        text = text.replace("?", "");
        text = text.replace("!", "");
        text = text.replace("(", "");
        text = text.replace(")", "");
        text = text.replace("\'", "");
        text = text.replace("\"", "");

        text = text.toUpperCase();

        return text;
    }

    public static String caesarify(String text, int key) {
        String encryptedText = "";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet = shiftAlphabet(key);
        System.out.println(shiftedAlphabet);
        for (int i = 0; i < text.length(); i++) {
            /*
            Text is encoded:
            1. i-character is extracted.
            2. Character's index in normal alphabet is calculated.
            3. Character with the same index, but on shifted alphabet is calculated.
            4. Character is added to encryption string.
             */
            encryptedText = encryptedText + shiftedAlphabet.charAt(alphabet.indexOf(text.charAt(i)));
        }

        return encryptedText;
    }

    public static String shiftAlphabet(int shift) {
        int start = 0;
        if (shift < 0) {
            start = (int) 'Z' + shift + 1;
        } else {
            start = 'A' + shift;
        }
        String result = "";
        char currChar = (char) start;
        for(; currChar <= 'Z'; ++currChar) {
            result = result + currChar;
        }
        if(result.length() < 26) {
            for(currChar = 'A'; result.length() < 26; ++currChar) {
                result = result + currChar;
            }
        }
        return result;
    }

    public static String groupify(String encryptedText, int noOfLettersPerGroup) {
        int quotient = encryptedText.length() / noOfLettersPerGroup;
        int remainder = encryptedText.length() % noOfLettersPerGroup;
        int fill = 0;
        if (remainder != 0) {
            fill = noOfLettersPerGroup - encryptedText.length() % noOfLettersPerGroup;
        }

        String groupedEncryptedText = "";

        //Divide the text to groups separated with a comma.
        for (int i = quotient; i > 0; i--) {
            groupedEncryptedText = groupedEncryptedText + encryptedText.substring(0, noOfLettersPerGroup) + ' ';
            encryptedText = encryptedText.substring(noOfLettersPerGroup);
        }
        //Adding remaining text.
        groupedEncryptedText = groupedEncryptedText + encryptedText;
        //Adding x's to make the last group even with the other ones.
        for (int j = fill; j > 0; j--) {
            groupedEncryptedText = groupedEncryptedText + 'x';
        }
        groupedEncryptedText = groupedEncryptedText.trim();

        return groupedEncryptedText;
    }

    public static String ungroupify(String groupedEncryptedText) {
        groupedEncryptedText = groupedEncryptedText.replace(" ", "").replace("x", "");
        return groupedEncryptedText;
    }

    public static String decryptString(String encryptedText, int key) {
        String text = "";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet = shiftAlphabet(key);
        System.out.println(shiftedAlphabet);
        for (int i = 0; i < encryptedText.length(); i++) {
            /*
            Text is decoded:
            1. i-character is extracted.
            2. Character's index in shifted normal alphabet is calculated.
            3. Character with the same index, but in alphabet is calculated.
            4. Character is added to decryption string.
             */
            text = text + alphabet.charAt(shiftedAlphabet.indexOf(encryptedText.charAt(i)));
        }
        System.out.println(text);

        return text;
    }

}
