package com.novel.recommen.oss;


import com.aliyun.oss.OSS;

public class OSSClientBuilder {
    static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    static String accessKeyId = "LTAI5tHoo71QuWLfP4Gdg62b";
    static String accessKeySecret = "jy3qsGgYUdTG5Zb6aT5gWOaIBnyOdU";
    static String bucketName = "yyx-novel";

    public static OSS GetClient(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。

// 创建OSSClient实例。
        OSS ossClient = new com.aliyun.oss.OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        return ossClient;
// 关闭OSSClient。
    }
}
