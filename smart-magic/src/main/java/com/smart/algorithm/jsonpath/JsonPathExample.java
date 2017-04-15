package com.smart.algorithm.jsonpath;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.List;
import java.util.Map;

/**
 * Created by  fc.w on 2016/12/6.
 */
public class JsonPathExample {

    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}";

        List<String> authors = JsonPath.read(jsonStr, "$.store.book[*].author");
        System.out.println(authors);


        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonStr);
        String author1 = JsonPath.read(document, "$.store.book[0].author");
        System.out.println(author1);


        ReadContext ctx = JsonPath.parse(jsonStr);
        List<String> authorsOfBooksWithISBN = ctx.read("$.store.book[?(@.isbn)].author");
        List<Map<String, Object>> expensiveBooks = JsonPath
                .using( Configuration.defaultConfiguration())
                .parse(jsonStr)
                .read("$.store.book[?(@.price > 10)]", List.class);
        System.out.println(expensiveBooks);
    }
}
