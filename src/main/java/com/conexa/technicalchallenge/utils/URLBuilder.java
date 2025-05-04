package com.conexa.technicalchallenge.utils;

/**
 * <p>Construye las URL para hacer las queries a SWAPI</p>
 *
 * En teoria tendria que poder usar {@link org.springframework.web.util.UriBuilder},
 * pero las queries que tenian whitespaces como por ejemplo <strong> Darth Vader</strong> terminaban en un resultado vacio
 */
public class URLBuilder {
    public static String replace(String value) {
            return value.replace("+", "%20");
        }


    public static String buildUrlWithParam(String baseUrl, String paramName, String paramValue) {
        return baseUrl + "?" + paramName + "=" + replace(paramValue);
    }
}
