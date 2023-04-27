package com.oocode;

import java.util.Objects;

public class Forecast {
    public final int minTemperatureCelcius;
    public final int maxTempCelcius;
    public final String description;

    public Forecast(int minTemperatureCelcius, int maxTempCelcius, String description) {
        this.minTemperatureCelcius = minTemperatureCelcius;
        this.maxTempCelcius = maxTempCelcius;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "minTemperatureCelcius=" + minTemperatureCelcius +
                ", maxTempCelcius=" + maxTempCelcius +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecast forecast = (Forecast) o;
        return minTemperatureCelcius == forecast.minTemperatureCelcius && maxTempCelcius == forecast.maxTempCelcius && Objects.equals(description, forecast.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minTemperatureCelcius, maxTempCelcius, description);
    }
}
