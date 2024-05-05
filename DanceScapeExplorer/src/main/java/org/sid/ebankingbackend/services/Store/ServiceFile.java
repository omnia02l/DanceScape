package org.sid.ebankingbackend.services.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
public class ServiceFile {
    @Value("${upload.path}")
    private String uploadPath;

    public void saveFile(MultipartFile file, String fileName) throws IOException {
        Path filePath = Paths.get(uploadPath + "/" + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

      /*  @Autowired
        private StorageImgRepository repository;

        public BookImg uploadImage(MultipartFile file) throws IOException {

            return repository.save(BookImg.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImgUtils.compressImage(file.getBytes())).build());

        }

        public byte[] downloadImage(Long id){
            Optional<BookImg> dbImageData = repository.findById(id);
            byte[] images=ImgUtils.decompressImage(dbImageData.get().getImageData());
            return images;
        }

        public void deleteImage(Long id)throws IOException
        {
            repository.deleteById(id);
        }*/
    }