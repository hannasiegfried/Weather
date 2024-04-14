package com.teamoptimization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;

public class LocatorClient {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient client = new OkHttpClient();

    @SuppressWarnings("unused") // Some things are only used by JSON deserialization
    public static class Location {
        @JsonProperty("lat")
        public BigDecimal latitude;
        @JsonProperty("long")
        public BigDecimal longitude;

        public Location(@JsonProperty("lat") BigDecimal latitude, @JsonProperty("long") BigDecimal longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Location() {
        }

        @Override
        public String toString() {
            return "Location(" + latitude + ", " + longitude + ")";
        }
    }

    public Location locationOf(String place) throws IOException {
        Request request = new Request.Builder()
                .url("https://jg2uit3u5j.execute-api.eu-west-2.amazonaws.com/api/location/" + place)
                .build();

        try (ResponseBody body = client.newCall(request).execute().body()) {
            return objectMapper.readValue(body.string(), Location.class);
        }
    }
}
