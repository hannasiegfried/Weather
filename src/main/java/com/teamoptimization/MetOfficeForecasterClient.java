package com.teamoptimization;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;

public class MetOfficeForecasterClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient client = new OkHttpClient();

    @SuppressWarnings("unused") // Some things are only used by JSON deserialization
    public static class Forecast {
        public int minTemp;
        public int maxTemp;
        public String description;

        public Forecast(int minTemp, int maxTemp, String description) {
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
            this.description = description;
        }

        public Forecast() {
        }

        @Override
        public String toString() {
            return "Forecast(" + minTemp + ", " + maxTemp + ", " + description + ")";
        }
    }

    public MetOfficeForecasterClient.Forecast forecast(int day, BigDecimal latitude, BigDecimal longitude) throws IOException {
        Request request = new Request.Builder()
                .url("https://k7kic7lc35.execute-api.eu-west-2.amazonaws.com/api/forecaster/" + day + "/" + latitude + "/" + longitude)
                .build();

        try (ResponseBody body = client.newCall(request).execute().body()) {
            return objectMapper.readValue(body.string(), MetOfficeForecasterClient.Forecast.class);
        }
    }
}
