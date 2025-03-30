package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.dto.ServicesSetDTO;
import lk.ijse.cleancopvt.entity.ServicesSet;
import lk.ijse.cleancopvt.repo.ServiceRepo;
import lk.ijse.cleancopvt.service.ServiceManagementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceManagementServiceImpl implements ServiceManagementService {

    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Add a new service
    @Override
    public void addService(ServicesSetDTO serviceDTO) {
        ServicesSet service = modelMapper.map(serviceDTO, ServicesSet.class);
        serviceRepo.save(service);
    }

    // Update an existing service
    @Override
    public void updateService(ServicesSetDTO serviceDTO) {
        ServicesSet service = modelMapper.map(serviceDTO, ServicesSet.class);
        serviceRepo.save(service);  // save operation will update if the entity already exists
    }

    // Delete a service by ID
    @Override
    public void deleteService(UUID id) {
        serviceRepo.deleteById(id);
    }

    // Get all services
    @Override
    public List<ServicesSetDTO> getAllServices() {
        List<ServicesSet> services = serviceRepo.findAll();
        return services.stream()
                .map(service -> modelMapper.map(service, ServicesSetDTO.class))
                .collect(Collectors.toList());
    }

    // Get a service by ID
    @Override
    public ServicesSetDTO getServiceById(UUID id) {
        return serviceRepo.findById(id)
                .map(service -> modelMapper.map(service, ServicesSetDTO.class))
                .orElse(null);
    }
}
