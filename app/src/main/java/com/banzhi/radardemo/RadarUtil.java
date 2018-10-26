package com.banzhi.radardemo;

import android.graphics.Paint;
import android.graphics.Rect;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRendererRadarChart;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/6/13.
 * @desciption :
 * @version :
 * </pre>
 */

public class RadarUtil {
    static Rect mDrawTextRectBuffer;
    static Paint.FontMetrics mFontMetricsBuffer;
    /**
     * 计算文字绘制起点
     * @param text
     * @param x
     * @param y
     * @param paint
     * @param anchor
     * @param angleDegrees
     * @return
     */
    private static RadarPointBean computeStartPoint(String text, float x, float y,
                                                    Paint paint,
                                                    MPPointF anchor, float angleDegrees) {
        mDrawTextRectBuffer = new Rect();
        mFontMetricsBuffer = new Paint.FontMetrics();
        float drawOffsetX = 0.f;
        float drawOffsetY = 0.f;
        final float lineHeight = paint.getFontMetrics(mFontMetricsBuffer);
        paint.getTextBounds(text, 0, text.length(), mDrawTextRectBuffer);

        drawOffsetX -= mDrawTextRectBuffer.left;

        drawOffsetY += -mFontMetricsBuffer.ascent;

        if (angleDegrees != 0.f) {
            drawOffsetX -= mDrawTextRectBuffer.width() * 0.5f;
            drawOffsetY -= lineHeight * 0.5f;
        } else {
            if (anchor.x != 0.f || anchor.y != 0.f) {
                drawOffsetX -= mDrawTextRectBuffer.width() * anchor.x;
                drawOffsetY -= lineHeight * anchor.y;
            }
            drawOffsetX += x;
            drawOffsetY += y;
        }
        return new RadarPointBean(drawOffsetX, drawOffsetY, mDrawTextRectBuffer);
    }

    /**
     * 计算位置
     * @param compositeRadar
     * @return
     */
    public static List<RadarPointBean> computePosition(RadarChart compositeRadar) {
        List<RadarPointBean> pointBeans = new ArrayList<>();
        XAxis xAxis = compositeRadar.getXAxis();
        final float labelRotationAngleDegrees = xAxis.getLabelRotationAngle();
        final MPPointF drawLabelAnchor = MPPointF.getInstance(0.5f, 0.25f);
        XAxisRendererRadarChart mXAxisRenderer = getXAxisRendererRadarChart(compositeRadar);
        float sliceangle = compositeRadar.getSliceAngle();
        float factor = compositeRadar.getFactor();
        MPPointF center = compositeRadar.getCenterOffsets();
        MPPointF pOut = MPPointF.getInstance(0, 0);

        for (int i = 0; i < compositeRadar.getData().getMaxEntryCountSet().getEntryCount(); i++) {
            String label = xAxis.getValueFormatter().getFormattedValue(i, xAxis);

            float angle = (sliceangle * i + compositeRadar.getRotationAngle()) % 360f;

            Utils.getPosition(center, compositeRadar.getYRange() * factor
                    + xAxis.mLabelRotatedWidth / 2f, angle, pOut);

            pointBeans.add(computeStartPoint(label, pOut.x, pOut.y - xAxis.mLabelRotatedHeight / 2.f,
                    mXAxisRenderer.getPaintAxisLabels(), drawLabelAnchor, labelRotationAngleDegrees));
        }
        return pointBeans;
    }

    /**
     * 反射获取XAxisRendererRadarChart
     * @param compositeRadar
     * @return
     */
    private static XAxisRendererRadarChart getXAxisRendererRadarChart(RadarChart compositeRadar) {
        try {
            Field field = compositeRadar.getClass().getDeclaredField("mXAxisRenderer");
            field.setAccessible(true);
            XAxisRendererRadarChart mXAxisRenderer = (XAxisRendererRadarChart) field.get(compositeRadar);
            return mXAxisRenderer;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }
}
