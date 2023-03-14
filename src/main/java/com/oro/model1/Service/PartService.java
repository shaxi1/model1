package com.oro.model1.Service;

import com.oro.model1.Model.Part;
import com.oro.model1.Repository.PartRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public List<Part> getAllParts() {
        return (List<Part>) partRepository.findAll();
    }

    public Part getPartById(Long id) {
        Optional<Part> part = partRepository.findById(id);
        if (part.isEmpty()) {
            throw new EntityNotFoundException("Part not found with id " + id);
        }
        return part.get();
    }

    public Part savePart(Part part) {
        return partRepository.save(part);
    }

    public void deletePartById(Long id) {
        partRepository.deleteById(id);
    }
}
