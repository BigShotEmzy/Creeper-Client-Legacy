package me.spruce.creeperclient.util;

import java.awt.*;
import java.util.ArrayList;

/*
* Skidded from SalHack
*/
public class RainbowUtils {
    public RainbowUtils(int p_Timer) {
        m_Timer = p_Timer;

        /// Populate the RainbowArrayList
        for (int l_I = 0; l_I < 360; l_I++) {
            RainbowArrayList.add(GetRainbowColor(l_I, 90.0f, 50.0f, 1.0f).getRGB());
            CurrentRainbowIndexes.add(l_I);
        }
    }

    private final ArrayList<Integer> CurrentRainbowIndexes = new ArrayList<Integer>();
    private final ArrayList<Integer> RainbowArrayList = new ArrayList<Integer>();
    private final Timer RainbowSpeed = new Timer();

    private int m_Timer;

    public int GetRainbowColorAt(int p_Index) {
        if (p_Index > CurrentRainbowIndexes.size() - 1)
            p_Index = CurrentRainbowIndexes.size() - 1;

        return RainbowArrayList.get(CurrentRainbowIndexes.get(p_Index));
    }

    public void SetTimer(int p_NewTimer) {
        m_Timer = p_NewTimer;
    }

    /// Call this function in your render/update function.
    public void OnRender() {
        if (RainbowSpeed.passed(m_Timer)) {
            RainbowSpeed.reset();
            MoveListToNextColor();
        }
    }

    private void MoveListToNextColor() {
        if (CurrentRainbowIndexes.isEmpty())
            return;

        CurrentRainbowIndexes.remove(CurrentRainbowIndexes.get(0));

        int l_Index = CurrentRainbowIndexes.get(CurrentRainbowIndexes.size() - 1) + 1;

        if (l_Index >= RainbowArrayList.size() - 1)
            l_Index = 0;

        CurrentRainbowIndexes.add(l_Index);
    }

    private static Color GetRainbowColor(float p_Hue, float p_Saturation, float p_Lightness, final float p_Alpha)
    {
        if (p_Saturation < 0.0f || p_Saturation > 100.0f)
        {
            throw new IllegalArgumentException("Color parameter outside of expected range - Saturation");
        }
        if (p_Lightness < 0.0f || p_Lightness > 100.0f)
        {
            throw new IllegalArgumentException("Color parameter outside of expected range - Lightness");
        }
        if (p_Alpha < 0.0f || p_Alpha > 1.0f)
        {
            throw new IllegalArgumentException("Color parameter outside of expected range - Alpha");
        }
        p_Hue = (p_Hue %= 360.0f) / 360.0f;
        p_Saturation /= 100.0f;
        p_Lightness /= 100.0f;
        float n5;
        if (p_Lightness < 0.0)
        {
            n5 = p_Lightness * (1.0f + p_Saturation);
        }
        else
        {
            n5 = p_Lightness + p_Saturation - p_Saturation * p_Lightness;
        }
        p_Saturation = 2.0f * p_Lightness - n5;
        p_Lightness = Math.max(0.0f, FutureClientColorCalculation(p_Saturation, n5, p_Hue + 0.33333334f));
        final float max = Math.max(0.0f, FutureClientColorCalculation(p_Saturation, n5, p_Hue));
        p_Saturation = Math.max(0.0f, FutureClientColorCalculation(p_Saturation, n5, p_Hue - 0.33333334f));
        p_Lightness = Math.min(p_Lightness, 1.0f);
        final float min = Math.min(max, 1.0f);
        p_Saturation = Math.min(p_Saturation, 1.0f);
        return new Color(p_Lightness, min, p_Saturation, p_Alpha);
    }

    private static float FutureClientColorCalculation(final float n, final float n2, float n3)
    {
        if (n3 < 0.0f)
        {
            ++n3;
        }
        if (n3 > 1.0f)
        {
            --n3;
        }
        if (6.0f * n3 < 1.0f)
        {
            return n + (n2 - n) * 6.0f * n3;
        }
        if (2.0f * n3 < 1.0f)
        {
            return n2;
        }
        if (3.0f * n3 < 2.0f)
        {
            return n + (n2 - n) * 6.0f * (0.6666667f - n3);
        }
        return n;
    }
}
