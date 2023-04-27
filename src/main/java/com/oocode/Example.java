package com.oocode;

import java.time.DayOfWeek;

public class Example {
    public static void main(String[] args) throws UnableToForecast {
        if (args.length != 2) {
            throw new RuntimeException("Must specify Day and Place");
        }
        Forecaster forecaster = new CachingForecaster(new MetOfficeForecaster());
        forecast(args[0], args[1], forecaster);
        forecast(args[0], args[1], forecaster);
        forecast(args[0], args[1], forecaster);
    }

    private static void forecast(String day, String place, Forecaster forecaster) throws UnableToForecast {
        Forecast forecast = forecaster.forecastFor(DayOfWeek.valueOf(day.toUpperCase()), place);
        System.out.printf("forecaster: %s day=%s min=%s max=%s description=%s%n",
                place, day, forecast.minTemperatureCelcius, forecast.maxTempCelcius, forecast.description);
    }
}
