package com.zhuanglide.micrboot.http;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

/**
 * Created by wwj on 17/3/2.
 */
public class HttpResponse{
    private FullHttpResponse httpResponse;
    private Map<String, String> header = new HashMap();
    private Charset charset;
    private String content;
    public HttpResponse(HttpVersion version, HttpResponseStatus status, Charset charset){
        httpResponse = new DefaultFullHttpResponse(version, status);
        this.charset = charset;
    }

    public HttpResponseStatus getStatus() {
        return httpResponse.status();
    }
    public void setStatus(HttpResponseStatus status) {
        httpResponse.setStatus(status);
    }

    public void addHeader(String name, String value) {
        httpResponse.headers().add(name, value);
    }

    public void addCookie(Cookie cookie){
        httpResponse.headers().add(HttpHeaders.Names.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setContent(String content){
        this.content = content;
        httpResponse.content().writeBytes(Unpooled.copiedBuffer(content.getBytes(charset)));
    }
    public String getContent(){return content;}


    public void setContent(byte[] bytes){
        httpResponse.content().writeBytes(Unpooled.copiedBuffer(bytes));
    }

    public void setFile(String fileName, byte[] bytes){
        httpResponse.headers().set(CONTENT_TYPE, "text/html; charset="+charset.displayName());
        if (null == fileName || fileName.length()==0) {
            httpResponse.headers().set("Content-Disposition", "attachment;filename=\""+fileName+"\"");
        }
        httpResponse.content().writeBytes(Unpooled.copiedBuffer(bytes));
    }


    public FullHttpResponse getHttpResponse(){
        return httpResponse;
    }


}
