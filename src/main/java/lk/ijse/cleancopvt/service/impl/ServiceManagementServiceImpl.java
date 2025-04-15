package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.dto.ServicesSetDTO;
import lk.ijse.cleancopvt.entity.Category;
import lk.ijse.cleancopvt.entity.ServicesSet;
import lk.ijse.cleancopvt.repo.CategoryRepo;
import lk.ijse.cleancopvt.repo.ServiceRepo;
import lk.ijse.cleancopvt.service.ServiceManagementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceManagementServiceImpl implements ServiceManagementService {

    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addService(ServicesSetDTO serviceDTO) {
        ServicesSet service = modelMapper.map(serviceDTO, ServicesSet.class);

        // Ensure that serviceName is set
        if (serviceDTO.getServiceName() != null && !serviceDTO.getServiceName().isEmpty()) {
            service.setServiceName(serviceDTO.getServiceName());
        }

        List<Category> categories = serviceDTO.getCategoryIdList()
                .stream()
                .map(id -> categoryRepo.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        service.setCategories(categories);
        serviceRepo.save(service);
    }

    @Override
    public void updateService(ServicesSetDTO serviceDTO) {
        ServicesSet service = modelMapper.map(serviceDTO, ServicesSet.class);

        // Ensure that serviceName is set
        if (serviceDTO.getServiceName() != null && !serviceDTO.getServiceName().isEmpty()) {
            service.setServiceName(serviceDTO.getServiceName());
        }

        serviceRepo.save(service);
    }

    @Override
    public void deleteService(UUID id) {
        serviceRepo.deleteById(id);
    }

    @Override
    public List<ServicesSetDTO> getAllServices() {
        List<ServicesSet> services = serviceRepo.findAll();
        return services.stream()
                .map(service -> {
                    ServicesSetDTO dto = modelMapper.map(service, ServicesSetDTO.class);
                    if (service.getCategories() != null) {
                        List<String> categoryNames = service.getCategories()
                                .stream()
                                .map(Category::getName)
                                .collect(Collectors.toList());
                        dto.setCategoryNames(categoryNames);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ServicesSetDTO getServiceById(UUID id) {
        return serviceRepo.findById(id)
                .map(service -> modelMapper.map(service, ServicesSetDTO.class))
                .orElse(null);
    }
}
