package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.ImageStore;
import org.sid.ebankingbackend.services.Store.CloudinaryService;
import org.sid.ebankingbackend.services.Store.ImageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin(origins = "http://localhost:4200")
public class ImageStoreController {
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImageStoreService imageService;

    @GetMapping("/list")
    public ResponseEntity<List<ImageStore>> list(){
        List<ImageStore> list = imageService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

   @PostMapping("/upload/{id}")
  //  @PostMapping("/upload")

    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile,@PathVariable("id") Long id) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image non valide!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        ImageStore image = new ImageStore((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));
        imageService.save(image,id);
        return new ResponseEntity<>("image ajoutée avec succès ! ", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Optional<ImageStore> imageOptional = imageService.getOne(id);
        if (imageOptional.isEmpty()) {
            return new ResponseEntity<>("n'existe pas", HttpStatus.NOT_FOUND);
        }
        ImageStore image = imageOptional.get();
        String cloudinaryImageId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete image from Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        imageService.delete(id);
        return new ResponseEntity<>("image supprimée !", HttpStatus.OK);
    }

}
