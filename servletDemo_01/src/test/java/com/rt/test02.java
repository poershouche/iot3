package com.rt;

import com.rt.utils.DriverOptions;
import sun.java2d.pipe.SpanIterator;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class test02 {
    public static void main(String[] args) {
//        DriverOptions chrome = DriverOptions.CHROME;
//        System.out.println(chrome.getWebdriver());
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        if (String.valueOf(i).length() <= 2) {
            String s = String.valueOf(i);
            s = "0" + s;
            System.out.println(new Integer(s));
        }
    }
}
