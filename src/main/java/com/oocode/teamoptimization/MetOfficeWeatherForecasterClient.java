package com.oocode.teamoptimization;

import com.oocode.teamoptimization.LocatorClient.Location;

import java.io.IOException;

public class MetOfficeWeatherForecasterClient {
    public MetOfficeForecasterClient.Forecast getForecast(String place, int dayNumber) throws IOException {
        Location location = new LocatorClient().locationOf(place);
        return new MetOfficeForecasterClient().forecast(dayNumber, location.latitude, location.longitude);
    }
}
