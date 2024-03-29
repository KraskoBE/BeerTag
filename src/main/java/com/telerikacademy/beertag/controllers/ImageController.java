package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Image;
import com.telerikacademy.beertag.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @PostMapping
    public ResponseEntity<Image> save(@RequestParam(name = "image") final MultipartFile imageData) {
        return imageService.save(imageData)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }


    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<byte[]> get(@PathVariable final int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return imageService.findById(id)
                .map(value -> new ResponseEntity<>(value.getBytes(), headers, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

}
