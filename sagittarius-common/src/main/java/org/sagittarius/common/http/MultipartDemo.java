package org.sagittarius.common.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

/**
 * @author JasonZhang 2018 - 04 - 19 - 11:03
 */
public class MultipartDemo {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String uri = "http://192.169.2.19:8485/services/upload";
            HttpPost httppost = new HttpPost(uri);

            String path = "C:\\Users\\kzdatd\\Desktop\\logo.png";
            File file = new File(path);
            FileBody bin = new FileBody(file, ContentType.MULTIPART_FORM_DATA);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", bin)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());

            httppost.addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6IjRXQHNvaHUuY29tIiwiZXhwIjoxNTI0MTEwODM5LCJpZCI6IjRXQHNvaHUuY29tIiwib3JpZ19pYXQiOjE1MjQxMDkwMzksInVzZXJfaWQiOiJ1c3ItNjk2MTZkYTI1NDUwNjNkNDY0YzgzNjkwNTE1Y2FhNzcxMTA3YzYiLCJ1c2VyX3R5cGUiOiJzdXBwbGllciJ9.SIhL4PNDsnaijSVkb5Gid-AebgH5N3HCXLU8464qdBUOealqAwyWQSXo_imKTi8M_IhU87lGQZXd-qPbkg9vpw");

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    String content = HttpUtil.getContent(resEntity.getContent());
                    System.out.println(content);
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
