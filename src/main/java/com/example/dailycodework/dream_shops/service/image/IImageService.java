package com.example.dailycodework.dream_shops.service.image;

import com.example.dailycodework.dream_shops.dto.ImageDto;
import com.example.dailycodework.dream_shops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void deleteImageById(Long id);
    void updateImage(MultipartFile file, Long imageId);
}
