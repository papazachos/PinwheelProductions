package com.example.spiros.myapplication;


import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by Spiros2 on 20/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilTest {

    @Test
    public void checkDistanceMethod()  {
        float distanceActual=DistanceUtils.distance(10,10,20,20);
        float distanceExpected=1879.1654f;
        assertEquals("Distance method now working good..", distanceExpected, distanceActual, 0.001);
    }
}
