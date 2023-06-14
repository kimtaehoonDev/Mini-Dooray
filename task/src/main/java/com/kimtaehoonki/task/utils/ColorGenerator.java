package com.kimtaehoonki.task.utils;

import com.kimtaehoonki.task.colorcode.ColorCode;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class ColorGenerator {
    public static ColorCode generate() {
        ColorCode[] values = ColorCode.values();
        Random random = new Random();
        return values[random.nextInt(values.length)];
    }
}
