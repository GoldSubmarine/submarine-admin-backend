package com.htnova.security.util;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Set;

/**
 * 签名工具类（用于验证签名）
 * 依赖 SignatureClient
 */
public final class SignatureUtil {

    private final SignatureClient signatureClient;

    private SignatureUtil(SignatureClient signatureClient) {
        this.signatureClient = signatureClient;
    }

    public static SignatureUtil getInstance(String secretKey, Set<String> ignoreParams){
        return new SignatureUtil(SignatureClient.getInstance(secretKey,ignoreParams));
    }

    public static SignatureUtil getInstance(String secretKey){
        return new SignatureUtil(SignatureClient.getInstance(secretKey));
    }

    public boolean verifySignature(HttpServletRequest request, String signature){
        if(signature == null || signature.isEmpty()){
            return false;
        }
        String fullUrl = request.getRequestURL().toString() + (request.getQueryString() == null ? "" : ("?"+request.getQueryString()));
        String sign = signatureClient.sign(request.getMethod(), fullUrl, getBytesFromRequest(request));
        return signature.equals(sign);
    }

    public boolean verifySignature(String strToSign, String signature){
        if(signature == null || signature.isEmpty() || strToSign == null){
            return false;
        }
        try {
            return signature.equals(signatureClient.sign(strToSign));
        }catch (Exception e){
            return false;
        }
    }

    private byte[] getBytesFromRequest(HttpServletRequest request) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            ServletInputStream inputStream = request.getInputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        }catch (Exception e){
            // ignore
        }
        return buffer.toByteArray();
    }
}