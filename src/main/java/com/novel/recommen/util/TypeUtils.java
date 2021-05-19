package com.novel.recommen.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import java.io.OutputStream;

public class TypeUtils {

    public int StringToInt(String a){
        int b = Integer.parseInt(a);
        return b;
    }
    public static byte[]  Base64ToImage(String content) { // 对字节数组字符串进行Base64解码并生成图片


        Decoder decoder = Base64.getDecoder();
        byte[] buffer = decoder.decode(content);
        return buffer;

    }
}
