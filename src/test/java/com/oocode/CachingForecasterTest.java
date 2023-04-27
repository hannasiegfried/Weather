package com.oocode;

import org.junit.Test;

import java.time.DayOfWeek;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CachingForecasterTest {
    private final Random random = new Random();
    private final Forecaster delegate = mock(Forecaster.class);
    private final CachingForecaster underTest = new CachingForecaster(delegate);

    @Test
    public void delegatesIfForecastNotSeenBefore() {
        Forecast expected = randomForecast("place");
        DayOfWeek dayOfWeek = randomDayOfWeek();
        String place = randomPlace();
        given(delegate.forecastFor(dayOfWeek, place)).willReturn(expected);

        Forecast actual = underTest.forecastFor(dayOfWeek, place);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void doesNotAskDelegateAgainIfSeenBefore() {
        Forecast expected = randomForecast("place");
        DayOfWeek dayOfWeek = randomDayOfWeek();
        String place = randomPlace();
        given(delegate.forecastFor(dayOfWeek, place)).willReturn(expected);

        underTest.forecastFor(dayOfWeek, place);

        Forecast actual = underTest.forecastFor(dayOfWeek, place);
        assertThat(actual, equalTo(expected));
        verify(delegate, times(1)).forecastFor(dayOfWeek, place);
    }

    @Test
    public void cachesForecastsForDifferentPlaces() {
        Forecast expectedForOnePlace = randomForecast("one place");
        Forecast expectedForAnotherPlace = randomForecast("another place");
        DayOfWeek dayOfWeek = randomDayOfWeek();
        String onePlace = randomPlace();
        String anotherPlace = randomPlace();
        given(delegate.forecastFor(dayOfWeek, onePlace)).willReturn(expectedForOnePlace);
        given(delegate.forecastFor(dayOfWeek, anotherPlace)).willReturn(expectedForAnotherPlace);

        underTest.forecastFor(dayOfWeek, onePlace);

        Forecast actual = underTest.forecastFor(dayOfWeek, anotherPlace);
        assertThat(actual, equalTo(expectedForAnotherPlace));
    }

    private DayOfWeek randomDayOfWeek() {
        return DayOfWeek.of(1 + random.nextInt(7));
    }

    private String randomPlace() {
        return "place-" + random.nextInt();
    }

    private Forecast randomForecast(String hint) {
        return new Forecast(random.nextInt(),
                random.nextInt(),
                hint + "-" + random.nextInt());
    }
}