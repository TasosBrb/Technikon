package com.team04.technikon.services.impl;

import com.team04.technikon.services.IoServices;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class IoServiceImplTest {

    @Test
    public void readPropertyCsvAndCheckIfAllLinesAreLoaded() {
        IoServices ioService = new IoServiceImpl();
        List<String[]> listOfLines;
        listOfLines = ioService.readCsvFile("property.csv");
        assertEquals(20, listOfLines.size());
    }

    @Test
    public void readRepairCsvAndCheckIfAllLinesAreLoaded() {
        IoServices ioService = new IoServiceImpl();
        List<String[]> listOfLines;
        listOfLines = ioService.readCsvFile("repairs.csv");
        assertEquals(20, listOfLines.size());
    }

    @Test
    public void readPropertyOwnerCsvAndCheckIfAllLinesAreLoaded() {
        IoServices ioService = new IoServiceImpl();
        List<String[]> listOfLines;
        listOfLines = ioService.readCsvFile("owners.csv");
        assertEquals(20, listOfLines.size());
    }

}
