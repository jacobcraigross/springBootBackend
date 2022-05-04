package com.jakemarco.jakemarco;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
public class PhotosController {

    private Map<String, Photo> db = new HashMap<>() {
        {
            put("1", new Photo("1", "goodbye.png"));
            put("2", new Photo("2", "hell.png"));
            put("3", new Photo("3", "death.png"));

        }
    };

    // temporarily created a mock photo.
    // private List<Photo> db = List.of(new Photo("001", "hello.jpg"));

    @GetMapping("/")
    public String metaDataGen() {
        return "META DATA TAG: J938SN8373SB635384PS2NS";
    }


    @GetMapping("/photos")
    public Collection<Photo> getPhotos() {
        return db.values();
    }


    @GetMapping("/photos/{id}")
    public Photo getPhotoById(@PathVariable String id) {
        Photo photo = db.get(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return photo;
    }


    @DeleteMapping("/photos/{id}")
    public void deletePhotoById(@PathVariable String id) {
        Photo photo = db.remove(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/photos")
    public Photo createPhoto(@RequestPart("data") MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setId(UUID.randomUUID().toString());
        photo.setFileName(file.getOriginalFilename());
        photo.setData(file.getBytes());
        db.put(photo.getId(), photo);
        return photo;
    }

}
