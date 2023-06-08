package com.tope.http_lib;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;

public class OSSClientUtils {
    private static OSS oss;

    public static OSS getOssClient(Context context) {
        if (oss == null) {
            synchronized (OSSClientUtils.class) {
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                String endpoint = "http://oss-cn-zhangjiakou.aliyuncs.com";
//        OSSAuthCredentialsProvider credentialProvider = new OSSAuthCredentialsProvider("stsServer");
                OSSStsTokenCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("", "",
                        "CAIS7QF1q6Ft5B2yfSjIr5fbI4iDlZt74ZebW3TLqFQfdbtdjZeZmjz2IHxMenBpCOsbt/o3nmFW7foYlq1vRoRZHZkfjXH7tsY5yxioRqacke7XhOV2pf/IMGyXDAGBr622Su7lTdTbV+6wYlTf7EFayqf7cjPQND7Mc+f+6/hdY88QQxOzYBdfGd5SPXECksIBMmbLPvvfWXyDwEioVRox6lot2D4mtv/vmZ3DsCCz1gOqlrUnwK3qOYWhYsVWO5Nybsy4xuQedNCajHYOskEbr/ov0vQeqGmc4ouHYUNY5hKdKPbZ6cF/hK7n0jia7sUagAEphna4DKnhJfXMb2SFjmmJSQVSzLKJ8LHeJQJb968JQBST929vdPPFWo4759gShxEoz65sQHVoLf40zN6xcgHnf7koHUudMVYzZhUiz7Sv9gDh43R8Y4b5ch2od+3ZMYMJukFgyQ+fzAUhABPYGIIx/Yhde9K+SSZKSj+OCl0J9A==");
                oss = new OSSClient(context, endpoint, credentialProvider, conf);
            }
        }
        return oss;
    }
}

