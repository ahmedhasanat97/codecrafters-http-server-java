package http.server.routes;

import http.server.utils.HttpRoutePatternUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRoutePattern {

    private final String pathPattern;
    private final String regexPathPattern;

    public HttpRoutePattern(String pathPattern) {
        if (!HttpRoutePatternUtils.isValidPattern(pathPattern)) {
            throw new RuntimeException("invalid pattern " + pathPattern);
        }
        this.pathPattern = pathPattern;
        this.regexPathPattern = HttpRoutePatternUtils.toRegexPattern(pathPattern);
    }

    public boolean match(String path) {
        Pattern pattern = Pattern.compile(regexPathPattern);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }

    public String getPathPattern() {
        return pathPattern;
    }

}
