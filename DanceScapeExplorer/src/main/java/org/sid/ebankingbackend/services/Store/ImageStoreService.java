package org.sid.ebankingbackend.services.Store;


import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.ImageStore;
import org.sid.ebankingbackend.repository.ImageStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageStoreService {


    @Autowired
    ImageStoreRepo imageRepository;

    public List<ImageStore> list(){
        return imageRepository.findByOrderById();
    }

    public Optional<ImageStore> getOne(int id){
        return imageRepository.findById(id);
    }

    public void save(ImageStore image){
        imageRepository.save(image);
    }

    public void delete(int id){
        imageRepository.deleteById(id);
    }

    public boolean exists(int id){
        return imageRepository.existsById(id);
    }
}