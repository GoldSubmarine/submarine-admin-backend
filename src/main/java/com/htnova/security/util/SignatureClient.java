package com.htnova.security.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SignatureException;
import java.util.*;

/**
 * 签名客户端
 * 1. Java6语法，方便在android上使用
 * 2. 不依赖其他包，java原生就可以用
 */
public final class SignatureClient {

    private final String secretKey;

    private Set<String> ignoreParams = Collections.EMPTY_SET;

    private SignatureClient(String secretKey) {
        this.secretKey = secretKey;
    }

    private SignatureClient(String secretKey, Set<String> ignoreParams) {
        this.secretKey = secretKey;
        this.ignoreParams = ignoreParams;
    }

    public static SignatureClient getInstance(String secretKey){
        return new SignatureClient(secretKey);
    }

    public static SignatureClient getInstance(String secretKey, Set<String> ignoreParams){
        return new SignatureClient(secretKey, ignoreParams);
    }

    public String sign(String method, String url){
        try {
            String stringToSign = computeStringToSign(method, url, Collections.EMPTY_MAP, "");
            return calculateHMAC(stringToSign, secretKey);
        }catch (Exception e){
            return null;
        }
    }

    public String sign(String method, String url, Map<String, String> reqParams){
        try {
            String stringToSign = computeStringToSign(method, url, reqParams, "");
            return calculateHMAC(stringToSign, secretKey);
        }catch (Exception e){
            return null;
        }
    }

    public String sign(String method, String url, String jsonBody){
        try {
            String stringToSign = computeStringToSign(method, url, Collections.EMPTY_MAP, jsonBody);
            return calculateHMAC(stringToSign, secretKey);
        }catch (Exception e){
            return null;
        }
    }

    public String sign(String method, String url, byte[] jsonBody){
        try {
            String stringToSign = computeStringToSign(method, url, Collections.EMPTY_MAP, jsonBody);
            return calculateHMAC(stringToSign, secretKey);
        }catch (Exception e){
            return null;
        }
    }

    public String sign(String method, String url, Map<String, String> reqParams, String jsonBody){
        try {
            String stringToSign = computeStringToSign(method, url, reqParams, jsonBody);
            return calculateHMAC(stringToSign, secretKey);
        }catch (Exception e){
            return null;
        }
    }

    public String sign(String method, String url, Map<String, String> reqParams, byte[] jsonBody){
        try {
            String stringToSign = computeStringToSign(method, url, reqParams, jsonBody);
            return calculateHMAC(stringToSign, secretKey);
        }catch (Exception e){
            return null;
        }
    }

    public String sign(String stringToSign){
        try {
            return calculateHMAC(stringToSign, secretKey);
        }catch (Exception e){
            return null;
        }
    }

    private String computeStringToSign(String method, String url, Map<String, String> reqParams, String jsonBody) throws MalformedURLException, UnsupportedEncodingException{
        byte[] reqEntity = (jsonBody == null ? "" : jsonBody.trim()).getBytes("utf-8");
        return computeStringToSign(method,url,reqParams,reqEntity);
    }
    private String computeStringToSign(String method, String url, Map<String, String> reqParams, byte[] reqEntity) throws MalformedURLException {
        URL reqUrl = new URL(url);
        //1. request method
        StringBuilder strToSign = new StringBuilder(method);
        strToSign.append("\n");
        //2. request path
        strToSign.append(reqUrl.getPath());
        strToSign.append("\n");
        //3. sorted request param and value
        List<Pair<String, String>> sortedParamList = getSortedParamList(reqUrl, reqParams);
        for(Pair<String, String> param : sortedParamList) {
            if(!ignoreParams.contains(param.left)){
                strToSign.append(param.left);
                strToSign.append("=");
                strToSign.append(param.right);
                strToSign.append("&");
            }
        }
        strToSign.deleteCharAt(strToSign.length()-1);
        strToSign.append("\n");
        //4. request entity
        strToSign.append(Encoder.RFC4648_URLSAFE.encodeToString(reqEntity));
        return strToSign.toString();
    }

    private List<Pair<String,String>> getSortedParamList(URL reqUrl, Map<String, String> reqParams) {
        List<Pair<String,String>> reqParamList = new ArrayList<Pair<String, String>>();
        if(reqUrl.getQuery() != null && !reqUrl.getQuery().isEmpty()){
            String[] params = reqUrl.getQuery().split("&");
            for(String param : params) {
                String[] pair = param.split("=");
                reqParamList.add(Pair.of(pair[0],pair.length == 1 ? "" : pair[1]));
            }
        }
        if(reqParams != null && !reqParams.isEmpty()){
            for(Map.Entry<String, String> entry : reqParams.entrySet()){
                reqParamList.add(Pair.of(entry.getKey(),entry.getValue()));
            }
        }
        Collections.sort(reqParamList, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                int res = o1.left.compareTo(o2.left);
                return res != 0 ? res : o1.right.compareTo(o2.right);
            }
        });
        return reqParamList;
    }

    private String calculateHMAC(String data, String key) throws SignatureException {
        try {
            SecretKeySpec e = new SecretKeySpec(key.getBytes("UTF8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(e);
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF8"));
            return Encoder.RFC4648_URLSAFE.encodeToString(rawHmac);
        } catch (Exception var6) {
            throw new SignatureException("Failed to generate HMAC : " + var6.getMessage());
        }
    }

    private static class Pair<L,R>{
        private L left;
        private R right;

        public static <L,R> Pair<L,R> of(L left, R right){
            return new Pair<L,R>(left, right);
        }

        private Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Java8 拷出来的base64 encoder
     */
    private static class Encoder {

        private final byte[] newline;
        private final int linemax;
        private final boolean isURL;
        private final boolean doPadding;

        private Encoder(boolean isURL, byte[] newline, int linemax, boolean doPadding) {
            this.isURL = isURL;
            this.newline = newline;
            this.linemax = linemax;
            this.doPadding = doPadding;
        }

        /**
         * This array is a lookup table that translates 6-bit positive integer
         * index values into their "Base64 Alphabet" equivalents as specified
         * in "Table 1: The Base64 Alphabet" of RFC 2045 (and RFC 4648).
         */
        private static final char[] toBase64 = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
        };

        /**
         * It's the lookup table for "URL and Filename safe Base64" as specified
         * in Table 2 of the RFC 4648, with the '+' and '/' changed to '-' and
         * '_'. This table is used when BASE64_URL is specified.
         */
        private static final char[] toBase64URL = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
        };

        private static final int MIMELINEMAX = 76;
        private static final byte[] CRLF = new byte[] {'\r', '\n'};

        static final Encoder RFC4648 = new Encoder(false, null, -1, true);
        static final Encoder RFC4648_URLSAFE = new Encoder(true, null, -1, true);

        private int outLength(int srclen) {
            int len = 0;
            if (doPadding) {
                len = 4 * ((srclen + 2) / 3);
            } else {
                int n = srclen % 3;
                len = 4 * (srclen / 3) + (n == 0 ? 0 : n + 1);
            }
            if (linemax > 0)                                  // line separators
                len += (len - 1) / linemax * newline.length;
            return len;
        }

        public byte[] encode(byte[] src) {
            int len = outLength(src.length);          // dst array size
            byte[] dst = new byte[len];
            int ret = encode0(src, 0, src.length, dst);
            if (ret != dst.length)
                return Arrays.copyOf(dst, ret);
            return dst;
        }

        public String encodeToString(byte[] src) {
            byte[] encoded = encode(src);
            return new String(encoded, 0, 0, encoded.length);
        }

        private int encode0(byte[] src, int off, int end, byte[] dst) {
            char[] base64 = isURL ? toBase64URL : toBase64;
            int sp = off;
            int slen = (end - off) / 3 * 3;
            int sl = off + slen;
            if (linemax > 0 && slen  > linemax / 4 * 3)
                slen = linemax / 4 * 3;
            int dp = 0;
            while (sp < sl) {
                int sl0 = Math.min(sp + slen, sl);
                for (int sp0 = sp, dp0 = dp ; sp0 < sl0; ) {
                    int bits = (src[sp0++] & 0xff) << 16 |
                            (src[sp0++] & 0xff) <<  8 |
                            (src[sp0++] & 0xff);
                    dst[dp0++] = (byte)base64[(bits >>> 18) & 0x3f];
                    dst[dp0++] = (byte)base64[(bits >>> 12) & 0x3f];
                    dst[dp0++] = (byte)base64[(bits >>> 6)  & 0x3f];
                    dst[dp0++] = (byte)base64[bits & 0x3f];
                }
                int dlen = (sl0 - sp) / 3 * 4;
                dp += dlen;
                sp = sl0;
                if (dlen == linemax && sp < end) {
                    for (byte b : newline){
                        dst[dp++] = b;
                    }
                }
            }
            if (sp < end) {               // 1 or 2 leftover bytes
                int b0 = src[sp++] & 0xff;
                dst[dp++] = (byte)base64[b0 >> 2];
                if (sp == end) {
                    dst[dp++] = (byte)base64[(b0 << 4) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                        dst[dp++] = '=';
                    }
                } else {
                    int b1 = src[sp++] & 0xff;
                    dst[dp++] = (byte)base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                    dst[dp++] = (byte)base64[(b1 << 2) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                    }
                }
            }
            return dp;
        }
    }
}