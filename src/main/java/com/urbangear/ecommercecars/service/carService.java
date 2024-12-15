// com/urbangear/ecommerceCars/service/CarService.java

package com.urbangear.ecommercecars.service;

import com.urbangear.ecommercecars.domain.car;
import com.urbangear.ecommercecars.repository.carRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class carService {

    @Autowired
    private carRepository carRepository;

    public List<car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public car saveCar(car car) {
        return carRepository.save(car);
    }

    public List<car> findByCategory(String category) {
        return carRepository.findByCategory(category);
    }

    public void deleteCarById(Long id) { carRepository.deleteById(id);}

    public Page<car> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }
}
