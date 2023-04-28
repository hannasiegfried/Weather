package com.oocode;

import org.junit.Test;

import java.time.DayOfWeek;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AveragingForecasterTest {
    @Test
    public void shouldAverageOverTwoForecasters() throws UnableToForecast {
        Forecaster forecaster1 = mock(Forecaster.class);
        given(forecaster1.forecastFor(DayOfWeek.FRIDAY, "somewhere"))
                .willReturn(new Forecast(0, 20, "hot"));

        Forecaster forecaster2 = mock(Forecaster.class);
        given(forecaster2.forecastFor(DayOfWeek.FRIDAY, "somewhere"))
                .willReturn(new Forecast(10, 18, "nice"));

        Forecast actual = new AveragingForecaster(forecaster1, forecaster2)
                .forecastFor(DayOfWeek.FRIDAY, "somewhere");

        assertThat(actual.minTemperatureCelcius, equalTo(5));
        assertThat(actual.maxTempCelcius, equalTo(19));
        assertThat(actual.description, anyOf(equalTo("hot"), equalTo("nice")));
    }

    @Test(expected = UnableToForecast.class)
    public void ifBothForecastersThrowExceptionThenPropagate() throws UnableToForecast {
        Forecaster forecaster1 = mock(Forecaster.class);
        given(forecaster1.forecastFor(DayOfWeek.FRIDAY, "somewhere"))
                .willThrow(new UnableToForecast("for test", new RuntimeException("for test")));

        Forecaster forecaster2 = mock(Forecaster.class);
        given(forecaster2.forecastFor(DayOfWeek.FRIDAY, "somewhere"))
                .willThrow(new UnableToForecast("for test", new RuntimeException("for test")));

        new AveragingForecaster(forecaster1, forecaster2).forecastFor(DayOfWeek.FRIDAY, "somewhere");
    }

    @Test(expected = UnableToForecast.class)
    public void ifEitherForecasterThrowsExceptionThenPropagate() throws UnableToForecast {
        // I might want it to return a forecast if either returns a forecast, but I chose this for simplicity
        Forecaster forecaster1 = mock(Forecaster.class);
        given(forecaster1.forecastFor(DayOfWeek.FRIDAY, "somewhere"))
                .willThrow(new UnableToForecast("for test", new RuntimeException("for test")));

        Forecaster forecaster2 = mock(Forecaster.class);
        given(forecaster2.forecastFor(DayOfWeek.FRIDAY, "somewhere"))
                .willReturn(new Forecast(10, 18, "nice"));

        new AveragingForecaster(forecaster1, forecaster2).forecastFor(DayOfWeek.FRIDAY, "somewhere");
    }
}
