package com.interior.adapter.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util {
    public static byte[] convertInputStreamToByteArray(final InputStream inputStream)
            throws IOException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // 버퍼 크기 지정 (원하는 크기로 조정 가능)

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // ByteArrayOutputStream을 byte 배열로 변환
        byte[] byteArray = outputStream.toByteArray();

        // 스트림 닫기
        inputStream.close();
        outputStream.close();

        return byteArray;
    }
}
