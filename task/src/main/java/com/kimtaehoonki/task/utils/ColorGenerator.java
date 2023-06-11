package com.kimtaehoonki.task.utils;

import com.kimtaehoonki.task.colorcode.ColorCode;
import java.util.Random;

public class ColorGenerator {
    public ColorCode get() {
        // 랜덤하게 ColorCode 한개 가져온다

        ColorCode[] values = ColorCode.values();
        Random random = new Random();
        return values[random.nextInt(values.length)];
    }
}
