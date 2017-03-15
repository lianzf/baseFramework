package com.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;

public class HttpClientUtil {

    /**
     * 
     * @Author lianzhifei 2016年9月7日 sendGet 方法描述: 逻辑描述:
     * @param path
     * @return
     * @throws Exception
     */
    public static String sendGet(String path) throws Exception {
        HttpURLConnection httpConn = null;
        BufferedReader in = null;
        try {
            URL url = new URL(path);
            httpConn = (HttpURLConnection) url.openConnection();

            // 读取响应
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = "";
                in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                while ((tempStr = in.readLine()) != null) {
                    content.append(tempStr);
                }
                return content.toString();
            } else {
                throw new Exception("请求出现了问题!,请求状态码=" + httpConn.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
            httpConn.disconnect();
        }
        return null;
    }

    /**
     * 
     * @param urlStr
     * @param range
     *            格式 "bytes=0-"
     * @return
     * @throws Exception
     */
    public static HttpURLConnection getHttpURLConnection(String urlStr, String range)
            throws Exception {
        URL url;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            setHeader(conn, range);
            // printHeader(conn);
            // 设置连接超时时间为10000ms
            conn.setConnectTimeout(5 * 60 * 1000);
            // 设置读取数据超时时间
            conn.setReadTimeout(5 * 60 * 1000);
            return conn;
        } catch (IOException e) {
            throw new Exception("获取HTTP链接错误: ", e);
        }
    }

    /**
     * 
     * @Author lianzhifei 2016年9月7日 downLoadFromUrl 方法描述: 从网络Url中下载文件 逻辑描述:
     * @param urlStr
     * @param fileName
     * @param absolutePath
     * @throws IOException
     */
    public static String downLoadFromUrl(HttpURLConnection conn, String fileName,
            String absolutePath) throws Exception {
        String format = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            int contentLength = conn.getContentLength();
            if(contentLength == -1){
            	throw new Exception("获取文件内容失败");
            }
            LogFactory
                    .getInstance()
                    .getSysInfoLogger()
                    .info("文件名称 【 " + fileName + "】 | 大小 【" + contentLength + "】读取超时时间【"
                            + conn.getReadTimeout() + "】");
            format = getFileFormat(conn, format);
            // 得到输入流
            inputStream = conn.getInputStream();
            // 获取自己数组
            byte[] getData = readInputStream(inputStream);

            File file = new File(absolutePath + File.separator + fileName + "." + format);
            fos = new FileOutputStream(file);
            fos.write(getData);

        } catch (MalformedURLException e) {
            throw new Exception("URL协议、格式或者路径错误", e);
        } catch (IOException e) {
            throw new Exception("读取IO错误", e);
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return format;
    }

    /**
     * 
     * downLoadImgFromUrl 方法描述: 逻辑描述:
     * 
     * @param conn
     * @param fileName
     * @param absolutePath
     * @return
     * @throws Exception
     * @since Ver 1.00
     */
    public static String downLoadImgFromUrl(HttpURLConnection conn, String fileName,
            String absolutePath) throws Exception {
        String format = "jpg";
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            int contentLength = conn.getContentLength();
            LogFactory
                    .getInstance()
                    .getSysInfoLogger()
                    .info("文件名称 【 " + fileName + "】 | 大小 【" + contentLength + "】读取超时时间【"
                            + conn.getReadTimeout() + "】");
            try {
                format = getFileFormat(conn, format);
            } catch (Exception e) {

            }
            // 得到输入流
            inputStream = conn.getInputStream();
            // 获取自己数组
            byte[] getData = readInputStream(inputStream);

            File file = new File(absolutePath + File.separator + fileName + "." + format);
            fos = new FileOutputStream(file);
            fos.write(getData);

        } catch (MalformedURLException e) {
            throw new Exception("URL协议、格式或者路径错误", e);
        } catch (IOException e) {
            throw new Exception("读取IO错误", e);
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return format;
    }

    private static String getFileFormat(HttpURLConnection conn, String format) throws Exception {
        String contentType = conn.getContentType();
        if (StringUtils.isBlank(contentType)) {
            throw new Exception("HTTP视频格式错误");
        } else {
            format = contentType.substring(contentType.indexOf("/") + 1);
        }
        return format;
    }

    /**
     * 
     * @Author lianzhifei 2016年9月7日 readInputStream 方法描述: 从输入流中获取字节数组 逻辑描述:
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[8 * 1024];
        int len = 0;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }

    /**
     * 
     * @Author lianzhifei 2016年9月18日 setHeader 方法描述: 设置URLConnection的头部信息，伪装请求信息 逻辑描述:
     * @param conn
     */
    public static void setHeader(URLConnection conn, String range) {
        conn.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3");
        conn.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
        if (StringUtils.isNotBlank(range)) {
            conn.setRequestProperty("RANGE", range);
        }
        conn.setRequestProperty("Accept-Encoding", "utf-8");
        conn.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        conn.setRequestProperty("Keep-Alive", "300");
        conn.setRequestProperty("connnection", "keep-alive");
        conn.setRequestProperty("If-Modified-Since", "Fri, 02 Jan 2009 17:00:05 GMT");
        conn.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
        conn.setRequestProperty("Cache-conntrol", "max-age=0");
        conn.setRequestProperty("Referer", "http://www.baidu.com");
    }

    /**
     * 
     * @Author lianzhifei 2016年9月18日 printHeader 方法描述: 打印下载文件头部信息 逻辑描述:
     * @param conn
     */
    public static void printHeader(URLConnection conn) {
        int i = 1;
        while (true) {
            String header = conn.getHeaderFieldKey(i);
            i++;
            if (header != null) {
                LogFactory.getInstance().getSysInfoLogger()
                        .info(header + ":" + conn.getHeaderField(i));
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            HttpURLConnection conn = getHttpURLConnection(
                    "http://www.wex5.com/wp-content/uploads/2015/10/tuniu-1.png", null);
            String contentType = conn.getContentType();

            String format = null;

            if (StringUtils.isBlank(contentType)) {
                throw new Exception("格式错误");
            } else {
                format = contentType.substring(contentType.indexOf("/") + 1);
            }
            System.out.println(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
