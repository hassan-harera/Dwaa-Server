package com.harera.hayat.stub.donation.medicine;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.harera.hayat.model.donation.Donation;
import com.harera.hayat.model.donation.medicine.MedicineDonation;
import com.harera.hayat.model.medicine.Medicine;
import com.harera.hayat.model.medicine.unit.MedicineUnit;
import com.harera.hayat.repository.donation.medicine.MedicineDonationRepository;
import com.harera.hayat.stub.donation.DonationStubs;

@Service
public class MedicineDonationStubs {

    private final MedicineDonationRepository medicineDonationRepository;
    private final DonationStubs donationStubs;

    public MedicineDonationStubs(MedicineDonationRepository medicineDonationRepository,
                    DonationStubs donationStubs) {
        this.medicineDonationRepository = medicineDonationRepository;
        this.donationStubs = donationStubs;
    }

    public MedicineDonation insert(MedicineUnit unit, Float amount, Medicine medicine,
                    OffsetDateTime medicineExpirationDate, Donation donation) {
        MedicineDonation medicineDonation =
                        create(unit, amount, medicine, medicineExpirationDate, donation);
        medicineDonation.setId(0L);
        return medicineDonationRepository.save(medicineDonation);
    }

    public MedicineDonation create(MedicineUnit unit, Float amount, Medicine medicine,
                    OffsetDateTime medicineExpirationDate, Donation donation) {
        MedicineDonation medicineDonation = new MedicineDonation();
        medicineDonation.setUnit(unit);
        medicineDonation.setMedicine(medicine);
        medicineDonation.setMedicineExpirationDate(medicineExpirationDate);
        medicineDonation.setAmount(amount);
        medicineDonation.setDonation(donation);
        return medicineDonation;
    }
}
