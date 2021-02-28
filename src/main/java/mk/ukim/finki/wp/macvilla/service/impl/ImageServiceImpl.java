package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Image;
import mk.ukim.finki.wp.macvilla.repository.ImageRepository;
import mk.ukim.finki.wp.macvilla.service.ImageService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Optional<Image> findById(Long imageId) {
        return this.imageRepository.findById(imageId);
    }

    @Override
    public Optional<Image> findByURL(String URL) {
        return this.imageRepository.findByImageURL(URL);
    }

    @Override
    public Optional<Image> findByDescription(String description) {
        return this.imageRepository.findByDescription(description);
    }

    @Override
    public Image save(String imageURL) {
        return this.imageRepository.save(new Image(imageURL));
    }
}
