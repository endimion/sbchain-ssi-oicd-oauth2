package com.example.sbchainssioicdoauth2.service;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.repository.SsiApplicationRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DBService {

    @Autowired
    private SsiApplicationRepository ssiAppRepo;

    public void submit(SsiApplication ssiApp) {
        ssiApp.setStatus("active");

        ssiApp.setTime(LocalDate.now());

        Optional<SsiApplication> oldApp = ssiAppRepo.findByUuid(ssiApp.getUuid());
        if (oldApp.isPresent() && oldApp.get().getStatus().equals("temp")) {
            String id = oldApp.get().getId();
            ssiAppRepo.deleteById(id);
        }
        ssiAppRepo.save(ssiApp);
    }

    public void temp(SsiApplication ssiApp) {
        ssiApp.setStatus("temp");
        ssiApp.setTime(LocalDate.now());

        Optional<SsiApplication> oldApp = ssiAppRepo.findByUuid(ssiApp.getUuid());
        if (oldApp.isPresent()) {
            String id = oldApp.get().getId();
            ssiAppRepo.deleteById(id);
        }
        ssiAppRepo.save(ssiApp);
    }

    public Optional<SsiApplication> getByTaxisAfm(String taxisAfm) {
        return ssiAppRepo.findFirstByTaxisAfm(taxisAfm);
    }

    public Optional<SsiApplication> getByUuid(String id) {
        return ssiAppRepo.findByUuid(id);
    }

    public Optional<List<SsiApplication>> getByMunicipality(String municipality) {
        return Optional.of(ssiAppRepo.findBySubmittedMunicipality(municipality));
    }

    public List<SsiApplication> findAllByAFM(String afm) {
        return ssiAppRepo.findByTaxisAfm(afm);
    }

    public void expire(SsiApplication ssiApp) {
        ssiApp.setStatus("expired");
        Optional<SsiApplication> oldApp = ssiAppRepo.findById(ssiApp.getId());
        if (oldApp.isPresent()) {
            String id = oldApp.get().getId();
            ssiAppRepo.deleteById(id);
        }
        ssiAppRepo.save(ssiApp);
    }

    public void delete(SsiApplication ssiApp) {
        Optional<SsiApplication> oldApp = ssiAppRepo.findById(ssiApp.getId());
        if (oldApp.isPresent()) {
            String id = oldApp.get().getId();
            ssiAppRepo.deleteById(id);
        }
    }

}
