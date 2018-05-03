package com.recharge.protal.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by Juphoon on 2018/1/17.
 */
@Controller
public class FileController {


    @RequestMapping("/toFile")
    public String toFile() {
        return "file";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file, MultipartHttpServletRequest request) throws IOException {


        int remotePort = request.getRemotePort();
        String remoteAddr = request.getRemoteAddr();
        InputStream in = file.getInputStream();
        byte[] buff = new byte[in.available()];
        in.read(buff);


        String sb = getFileRequestText(file, request, buff, "");
        System.out.println(sb);


        return "ss";
    }

    @RequestMapping("/ss")
    @ResponseBody
    public String tt(HttpServletRequest request) {

        String result = getRequestText(request);

        System.out.println(result);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("ss", "yy");
        httpHeaders.set("ss", "yy");
        ResponseEntity responseEntity = new ResponseEntity(httpHeaders, HttpStatus.OK);

        String sb2 = getResponseText(request, responseEntity);
        System.out.println(sb2);
        return "sss";
    }

    private String getRequestText(HttpServletRequest request) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        sb.append(request.getMethod()).append(" ").append(request.getRequestURI());
        if (request.getMethod().equals("GET")) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap.size() > 0) {
                sb.append("?");
            }
            parameterMap.forEach((key, value) -> {
                sb.append(key).append("=").append(value[0]);
            });
            sb.append(" ").append(request.getProtocol()).append("\r\n");
            Enumeration<String> e = request.getHeaderNames();
            while (e.hasMoreElements()) {
                String value = e.nextElement();//调用nextElement方法获得元素
                sb.append(value).append(": ").append(request.getHeader(value)).append("\r\n");
            }
            sb.append("\r\n");
        } else if (request.getMethod().equals("POST")) {
            sb.append(" ").append(request.getProtocol()).append("\r\n");
            Enumeration<String> e = request.getHeaderNames();
            while (e.hasMoreElements()) {
                String value = e.nextElement();//调用nextElement方法获得元素
                sb.append(value).append(": ").append(request.getHeader(value)).append("\r\n");
            }
            sb.append("\r\n");

            Map<String, String[]> parameterMap = request.getParameterMap();
            parameterMap.forEach((key, value) -> {
                sb.append(key).append("=").append(value[0]).append("&");
            });
            result = sb.substring(0, sb.length() - 1);
            result += "\r\n";
        }
        return result;
    }

    private String getResponseText(HttpServletRequest request, ResponseEntity responseEntity) {
        StringBuffer sb2 = new StringBuffer();
        sb2.append(request.getProtocol()).append(" ").append(responseEntity.getStatusCode()).append(" ").append(responseEntity.getStatusCode().getReasonPhrase()).append("\r\n");
        HttpHeaders headers = responseEntity.getHeaders();
        headers.forEach((key, value) -> {
            sb2.append(key).append(": ").append(value.get(0)).append("\r\n");
        });
        if (responseEntity.getBody() != null) {
            sb2.append(responseEntity.getBody());
        }
        return sb2.toString();
    }

    private String getFileRequestText(MultipartFile file, HttpServletRequest request, byte[] buff, String paramName) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getMethod()).append(" ").append(request.getRequestURI()).append(" ").append(request.getProtocol()).append("\r\n");
        Enumeration<String> e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String value = (String) e.nextElement();//调用nextElement方法获得元素
            sb.append(value).append(": ").append(request.getHeader(value)).append("\r\n");
        }
        sb.append("\r\n");

        String header = request.getHeader("content-type");
        String spl = header.split("=")[1];
        sb.append("--").append(spl).append("\r\n");

        Map<String, String[]> map = request.getParameterMap();
        map.forEach((key, value) -> {


            sb.append("Content-Disposition: form-data; name=").append(key).append("\r\n").append("\r\n");
            if (!StringUtils.isEmpty(value)) {
                sb.append(value[0]);
            }
            sb.append("\r\n");
        });

        sb.append("--").append(spl).append("\r\n");
        sb.append("Content-Disposition: form-data; name=\""+paramName+"\"; filename=\"")
                .append(file.getOriginalFilename()).append("\"").append("\r\n")
                .append("Content-Type: ").append(file.getContentType()).append("\r\n").append("\r\n");

        //String s = sb.substring(0, sb.length() - 1);

        sb.append(new String(buff)).append("\r\n");
        sb.append("--").append(spl).append("--");
        return sb.toString();
    }


}
