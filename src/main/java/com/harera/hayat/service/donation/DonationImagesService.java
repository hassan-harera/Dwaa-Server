package com.harera.hayat.service.donation;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.exception.FieldFormatException;
import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.image.DonationImage;
import com.harera.hayat.repository.donation.image.DonationImageRepository;
import com.harera.hayat.service.FileManager;
import com.harera.hayat.service.file.CloudFileService;
import com.harera.hayat.util.ErrorCode;

@Service
public class DonationImagesService {

    private final FileManager fileManager;
    private final DonationImageRepository donationImageRepository;
    private final CloudFileService cloudFileService;

    public DonationImagesService(FileManager fileManager,
                    DonationImageRepository donationImageRepository,
                    CloudFileService cloudFileService) {
        this.fileManager = fileManager;
        this.donationImageRepository = donationImageRepository;
        this.cloudFileService = cloudFileService;
    }

    public Donation insertImage(Donation donation, MultipartFile image) {
        File file = fileManager.convert(image);
        if (file == null) {
            throw new FieldFormatException(ErrorCode.FORMAT_DONATION_IMAGE_FILE, "image");
        }

        String fileUrl = cloudFileService.uploadFile(file);
        DonationImage donationImage = new DonationImage(fileUrl, donation);
        donationImageRepository.save(donationImage);

        List<DonationImage> images = donation.getImages();
        if (images == null) {
            images = new LinkedList<>();
        }

        images.add(new DonationImage(fileUrl, donation));
        donation.setImages(images);

        return donation;
    }
}
