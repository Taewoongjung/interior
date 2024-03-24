package com.interior.adapter.config.cors;

import static com.interior.adapter.common.util.Util.convertInputStreamToByteArray;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ReadableRequestWrapper extends HttpServletRequestWrapper {

    private final Charset encoding;
    private byte[] rawData;

    public ReadableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String charEncoding = request.getCharacterEncoding();
        this.encoding = StringUtils.isBlank(charEncoding) ? StandardCharsets.UTF_8
                : Charset.forName(charEncoding);

        try {
            InputStream is = request.getInputStream();
            this.rawData = convertInputStreamToByteArray(is);
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);
        ServletInputStream servletInputStream = new ServletInputStream() {
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isReady() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                // TODO Auto-generated method stub

            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }
}
