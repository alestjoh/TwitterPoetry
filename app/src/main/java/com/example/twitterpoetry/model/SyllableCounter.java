package com.example.twitterpoetry.model;

import java.util.ArrayList;

public abstract class SyllableCounter {
    private static final String vowels = "aeiouy";

    /**
     * Counts the number of syllables in one line of input
     * @param data should not contain endline characters
     * @return
     */
    public static int countInLine(String data) {
        String[] words = data.split(" ");
        int sum = 0;
        for (String word : words) {
            word = trimPunctuation(word);
            if (!word.isEmpty()) {
                sum += countInWord(word);
            }
        }
        return sum;
    }

    /**
     * Removes ending punctuation from a string which may affect syllable count
     * @param word
     * @return
     */
    private static String trimPunctuation(String word) {
        while (word.length() > 0 &&
                !Character.isAlphabetic(word.charAt(word.length() - 1))) {
            word = word.substring(0, word.length() - 1);
        }

        return word;
    }

    /**
     * Counts all the syllables in the given word.
     * @param word should not contain spaces
     * @return approximate number of syllables in this word
     */
    public static int countInWord(String word) {
        if (word.contains(" ")) {
            throw new IllegalArgumentException("Word cannot have spaces");
        }

        word = word.toLowerCase();
        ArrayList<String> vowelGroups = getVowelGroups(word);

        if (vowelGroups.size() == 0) {
            return 1;
        }
        removeEndingE(vowelGroups, word);

        fixMiddleY(vowelGroups);

        return vowelGroups.size();
    }

    /**
     * Replaces vowel groups like "ayo" in "sayonara" with separated
     * groups around the middle "y"
     * @param vowelGroups
     */
    private static void fixMiddleY(ArrayList<String> vowelGroups) {
        for (int i = 0; i < vowelGroups.size(); i++) {
            String group = vowelGroups.get(i);

            if (group.contains("y") && !group.endsWith("y")) {
                vowelGroups.remove(i);
                String[] parts = group.split("y");
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        vowelGroups.add(part);
                    }
                }

                i--;
            }
        }
    }

    /**
     * Removes ending e's for words like "like" and "obfuscate," but not
     * for very short words like "be"
     * @param vowelGroups
     */
    private static void removeEndingE(ArrayList<String> vowelGroups, String word) {
        // Ending e's don't count, except for very short words like "be"
        if (vowelGroups.size() > 1 &&
                vowelGroups.get(vowelGroups.size() - 1).equals("e") &&
                word.endsWith("e")) {
            vowelGroups.remove(vowelGroups.size() - 1);
        }
    }

    /**
     * Finds a very basic array of all the vowel groups in a word.
     * "Y" is counted as a vowel for this function.
     * @param word
     * @return a list containing all vowel groups within a word, including y
     */
    private static ArrayList<String> getVowelGroups(String word) {
        ArrayList<String> vowelGroups = new ArrayList<>();
        String group = "";

        for (int i = 0; i < word.length(); i++) {
            if (vowels.contains(word.substring(i, i + 1))) {
                group += word.charAt(i);
            } else if (group.length() > 0) {
                vowelGroups.add(group);
                group = "";
            }
        }
        if (group.length() > 0) {
            vowelGroups.add(group);
        }
        return vowelGroups;
    }
}
