package com.oocode;

import com.teamoptimization.NavyForecastingClient;

import java.io.IOException;
import java.time.DayOfWeek;

public class NavyForecaster implements Forecaster {
    @Override
    public Forecast forecastFor(DayOfWeek dayOfWeek, String place) throws UnableToForecast {
        try {
            NavyForecastingClient forecasting = new NavyForecastingClient();
            int minTemp = forecasting.min(dayOfWeek, place);
            int maxTemp = forecasting.max(dayOfWeek, place);
            String description = forecasting.desc(dayOfWeek, place);
            return new Forecast(minTemp, maxTemp, description);
        } catch (IOException e) {
            throw new UnableToForecast("Unable to get forecast from Navy", e);
        }
    }
}
