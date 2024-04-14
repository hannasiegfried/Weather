package com.oocode.teamoptimization;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.time.DayOfWeek;

public class NavyForecastingClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient client = new OkHttpClient();

    public String desc(DayOfWeek day, String place) throws IOException {
        return forecastingResult(day, place, "desc").value;
    }
    public int min(DayOfWeek day, String place) throws IOException {
        return Integer.parseInt(forecastingResult(day, place, "min").value);
    }
    public int max(DayOfWeek day, String place) throws IOException {
        return Integer.parseInt(forecastingResult(day, place, "max").value);
    }

    @SuppressWarnings("unused") // Some things are only used by JSON deserialization
    private static class ForecastingResult {
        public String name;
        public String value;

        public ForecastingResult(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public ForecastingResult() {
        }

        @Override
        public String toString() {
            return "ForecastingResult(" + name + ", " + value + ")";
        }
    }

    private ForecastingResult forecastingResult(DayOfWeek dayOfWeek, String place, String itemName) throws IOException {
        String day = dayOfWeek.toString().toLowerCase();
        day = day.substring(0, 1).toUpperCase() + day.substring(1);
        Request request = new Request.Builder()
                .url("https://sdaxdtn6ha.execute-api.eu-west-2.amazonaws.com/api/forecasting/" + day + "/" + place + "/" + itemName)
                .build();

        try (ResponseBody body = client.newCall(request).execute().body()) {
            return objectMapper.readValue(body.string(), ForecastingResult.class);
        }
    }

    public static void exampleUse() throws IOException {
        String day = "Wednesday";
        String place = "Oxford";
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
        NavyForecastingClient forecasting = new NavyForecastingClient();
        int minTemp = forecasting.min(dayOfWeek, place);
        int maxTemp = forecasting.max(dayOfWeek, place);
        String description = forecasting.desc(dayOfWeek, place);
        System.out.printf("Navy forecast for: %s day=%s min=%s max=%s description=%s%n",
                place, day, minTemp, maxTemp, description);
    }
}
