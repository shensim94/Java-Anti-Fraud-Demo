package com.example.AntiFraudDemo.util;

import java.util.List;

public class Util {

    public static String constructString(List<String> items) {
        StringBuilder resultBuilder = new StringBuilder();

        // Loop through the list
        for (String item : items) {
            // Append the item to the result
            resultBuilder.append(item);

            // Append a comma if it's not the last element
            if (items.indexOf(item) < items.size() - 1) {
                resultBuilder.append(", ");
            }
        }

        // Convert StringBuilder to String
        return resultBuilder.toString();
    }
}
