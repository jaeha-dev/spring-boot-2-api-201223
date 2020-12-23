package com.github.devsjh.model;

public class JsonBuilder {

    // 2020-01-01(1시) 데이터 생성
    public static String buildNewData() {
        return
            "{\n" +
            "  \"total_date\": \"2020-01-01\",\n" +
            "  \"total_hours\": [\n" +
            "    {\n" +
            "      \"hour\": 1,\n" +
            "      \"request\": 10,\n" +
            "      \"response\": 10,\n" +
            "      \"click\": 10\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    }

    // 2020-01-01(3시) 데이터 생성
    public static String buildPartialData() {
        return
            "{\n" +
            "  \"total_date\": \"2020-01-01\",\n" +
            "  \"total_hours\": [\n" +
            "    {\n" +
            "      \"hour\": 3,\n" +
            "      \"request\": 30,\n" +
            "      \"response\": 30,\n" +
            "      \"click\": 30\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    }

    // 2020-01-01(2시) 데이터 갱신
    public static String buildUpdateData() {
        return
            "{\n" +
            "  \"total_date\": \"2020-01-01\",\n" +
            "  \"total_hours\": [\n" +
            "    {\n" +
            "      \"hour\": 2,\n" +
            "      \"request\": 200,\n" +
            "      \"response\": 200,\n" +
            "      \"click\": 200\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    }
}