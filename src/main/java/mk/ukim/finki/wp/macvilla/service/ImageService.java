package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Image;

import java.util.Optional;

public interface ImageService {
    Optional<Image> findById(Long imageId);
    Optional<Image> findByURL(String URL);
    Optional<Image> findByDescription(String description);
}
