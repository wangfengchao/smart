package com.links.httpClient;

/**
 * Created by  fc.w on 2016/12/2.
 */
public interface LinkFilter {
    public boolean accept(String url);
}
