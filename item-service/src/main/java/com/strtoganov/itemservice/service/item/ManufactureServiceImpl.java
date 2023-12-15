package com.strtoganov.itemservice.service.item;

import com.strtoganov.itemservice.domain.model.item.Manufacture;
import com.strtoganov.itemservice.repository.ManufactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManufactureServiceImpl implements ManufactureService {
    @Autowired
    private ManufactureRepository manufactureRepository;

    @Override
    public Optional<Manufacture> findByNameAndDescription(String name, String description) {
        return manufactureRepository.findByNameAndDescription(name, description);
    }

    @Override
    public List<Manufacture> findAllManufacture() {
        return manufactureRepository.findAll();
    }
}
