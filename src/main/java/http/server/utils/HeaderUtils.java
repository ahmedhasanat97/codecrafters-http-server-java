package http.server.utils;

import http.server.common.Constants;

import java.util.Map;

public class HeaderUtils {

    public static Map.Entry<String, String> parseHeaderLine(String headerLine) {
        int indexOfFirstColon = headerLine.indexOf(Constants.COLON);
        String key = headerLine.substring(0, indexOfFirstColon).trim().toLowerCase();
        String value = headerLine.substring(indexOfFirstColon + 1).trim();
        return Map.entry(key, value);
    }

}
