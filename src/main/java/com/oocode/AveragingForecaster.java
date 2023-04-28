package com.oocode;

import java.time.DayOfWeek;

public class AveragingForecaster implements Forecaster {
    private final Forecaster forecaster1;
    private final Forecaster forecaster2;

    public AveragingForecaster(Forecaster forecaster1, Forecaster forecaster2) {
        this.forecaster1 = forecaster1;
        this.forecaster2 = forecaster2;
    }

    @Override
    public Forecast forecastFor(DayOfWeek dayOfWeek, String place) throws UnableToForecast {
        Forecast forecast1 = forecaster1.forecastFor(dayOfWeek, place);
        Forecast forecast2 = forecaster2.forecastFor(dayOfWeek, place);
        return new Forecast(
                average(forecast1.minTemperatureCelcius, forecast2.minTemperatureCelcius),
                average(forecast1.maxTempCelcius, forecast2.maxTempCelcius),
                forecast1.description);
    }

    private int average(int i1, int i2) {
        return (i1 + i2) / 2;
    }
}
