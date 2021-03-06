package com.novel.recommen.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Time;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class OssClient {
    public static OSS client = OSSClientBuilder.GetClient();;
    public static String GetRandomBook() {
        String bookContent = "";
        int n = (int)(Math.random()*34);
        while (true){
            try{
                bookContent = "";
                OSSObject ossObject = client.getObject(OSSClientBuilder.bucketName, "book"+n+".txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    bookContent += line;
                    System.out.println(line);
                }
                break;
            }catch (Exception e){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
        return bookContent;
    }
    public static void UpdateImg(byte[] content,String name){
        PutObjectRequest putObjectRequest = new PutObjectRequest(OSSClientBuilder.bucketName, name, new ByteArrayInputStream(content));
        PutObjectResult res = client.putObject(putObjectRequest);
        System.out.println(res);
    }

}


