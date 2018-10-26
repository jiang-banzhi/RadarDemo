package com.banzhi.radardemo;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * <pre>
 * @author : No.1
 * @time : 2017/12/26.
 * @desciption :
 * @version :
 * </pre>
 */

public class XAxisFormatter implements IAxisValueFormatter {
    List<String> list;

    public XAxisFormatter(List<String> list) {
        this.list = list;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (list == null || list.size() == 0) return "";
        int position = (int) Math.abs(value) % list.size();
        if (position < list.size()) {
            if (list.size() > 1) {
                return list.get(position);
            } else {
                if (value == 0) {
                    return list.get(0);
                } else {
                    return "";
                }
            }
        }
        return String.valueOf(value);
    }
}
