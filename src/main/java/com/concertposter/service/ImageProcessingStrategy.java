package com.concertposter.service;
import java.io.File;
import java.io.IOException;

public interface ImageProcessingStrategy {
    File process(File image) throws IOException;
}
