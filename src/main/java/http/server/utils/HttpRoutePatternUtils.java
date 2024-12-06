package http.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRoutePatternUtils {

    public static boolean isValidPattern(String pathPattern) {
        String regex = "^/?(\\{[a-zA-Z0-9_-]+\\}|[a-zA-Z0-9_]+)(/(\\{[a-zA-Z0-9_-]+\\}|[a-zA-Z0-9_-]+))*/*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pathPattern);
        return matcher.matches();
    }

    public static String toRegexPattern(String pathPattern) {
        return transformPathVariables(pathPattern);
    }

    private static String transformPathVariables(String pathPattern) {
        // Define the regex to match path variables within curly braces
        String pathVariableRegex = "\\{([a-zA-Z0-9_-]+)\\}";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(pathVariableRegex);
        Matcher matcher = pattern.matcher(pathPattern);

        // List to store extracted path variables
        List<String> pathVariables = new ArrayList<>();

        // Replace path variables with the regex pattern that matches any string except slashes
        StringBuilder regexBuilder = new StringBuilder("^"); // Start the regex with '^'

        // Traverse through the path pattern and build the regex
        int lastIndex = 0;
        while (matcher.find()) {
            // Append the part of the string before the path variable
            regexBuilder.append(Pattern.quote(pathPattern.substring(lastIndex, matcher.start())));

            // Extract the path variable name (e.g., id, name, timestamp)
            String pathVariable = matcher.group(1);
            pathVariables.add(pathVariable);

            // Append the regex for the path variable (any string except a slash)
            regexBuilder.append("([a-zA-Z0-9_-]+)");

            // Update the last matched index
            lastIndex = matcher.end();
        }

        // Append the remaining part of the path after the last path variable
        regexBuilder.append(Pattern.quote(pathPattern.substring(lastIndex)));

        // Allow for an optional trailing slash
        regexBuilder.append("/*$");

        // Print the extracted path variables
        System.out.println("Path Variables: " + pathVariables);

        // Return the generated regex pattern
        return regexBuilder.toString();
    }

}
