package com.kimtaehoonki.task.utils;

import com.kimtaehoonki.task.tag.ColorCode;
import java.util.Random;

public class ColorGenerator {
    public static ColorCode generate(Random random) {
        ColorCode[] values = ColorCode.values();
        return values[random.nextInt(values.length)];
    }
}
