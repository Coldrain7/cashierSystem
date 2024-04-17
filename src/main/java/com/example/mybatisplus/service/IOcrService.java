package com.example.mybatisplus.service;

import java.io.InputStream;

public interface IOcrService {
    String recognizeText(InputStream sbs);
}
