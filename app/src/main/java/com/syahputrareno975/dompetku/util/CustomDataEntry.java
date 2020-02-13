package com.syahputrareno975.dompetku.util;

import com.anychart.chart.common.dataentry.ValueDataEntry;

public class CustomDataEntry extends ValueDataEntry {
    public CustomDataEntry(String x, Number value, Number value2) {
        super(x, value);
        setValue("value2", value2);
    }
}
