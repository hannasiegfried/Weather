package com.oocode;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class CachingForecasterTest {
    private final Random random = new Random();
    private final Forecaster delegate = mock(Forecaster.class);
    private final CachingForecaster underTest = new CachingForecaster(delegate);

    @Test
    public void delegatesIfForecastNotSeenBefore() throws UnableToForecast {
        Forecast expected = randomForecast("place");
        DayOfWeek dayOfWeek = randomDayOfWeek();
        String place = randomPlace();
        given(delegate.forecastFor(dayOfWeek, place)).willReturn(expected);

        Forecast actual = underTest.forecastFor(dayOfWeek, place);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void doesNotAskDelegateAgainIfSeenBefore() throws UnableToForecast {
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
    public void cachesForecastsForDifferentPlaces() throws UnableToForecast {
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

    @Test
    public void cachesForecastsForDifferentDays() throws UnableToForecast {
        Forecast expectedForOneDay = randomForecast("one day");
        Forecast expectedForAnotherDay = randomForecast("another day");
        DayOfWeek oneDay = randomDayOfWeek();
        DayOfWeek anotherDay = randomDayOfWeek();
        String place = randomPlace();
        given(delegate.forecastFor(oneDay, place)).willReturn(expectedForOneDay);
        given(delegate.forecastFor(anotherDay, place)).willReturn(expectedForAnotherDay);

        underTest.forecastFor(oneDay, place);

        Forecast actual = underTest.forecastFor(anotherDay, place);
        assertThat(actual, equalTo(expectedForAnotherDay));
    }

    @Test
    public void propagatesExceptionFromDelegateWhenUnableToForecast() throws UnableToForecast {
        given(delegate.forecastFor(any(), any()))
                .willThrow(new UnableToForecast("oops", new RuntimeException("bad")));

        assertThrows(
                UnableToForecast.class,
                () -> underTest.forecastFor(randomDayOfWeek(), randomPlace()));
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