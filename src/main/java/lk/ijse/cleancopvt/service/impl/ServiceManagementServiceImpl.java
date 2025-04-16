package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.Enum.Duration;
import lk.ijse.cleancopvt.dto.CategoryServiceCartDTO;
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

import java.util.*;
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

    @Override
    public List<CategoryServiceCartDTO> getAllServiceCartDetails() {
        List<ServicesSet> allServices = serviceRepo.findAll();

        Map<String, CategoryServiceCartDTO> resultMap = new HashMap<>();

        for (ServicesSet service : allServices) {
            if (service.getCategories() != null) {
                for (Category category : service.getCategories()) {
                    String key = category.getName();

                    Duration duration = category.getDuration();
                    int multiplier = switch (duration) {
                        case Day -> 1;
                        case Week -> 7;
                        case Month -> 30;
                        case SixMonth -> 180;
                        case Year -> 360;
                    };

                    double serviceTotal = service.getUnitPrice() * multiplier;

                    resultMap.computeIfAbsent(key, k -> {
                        CategoryServiceCartDTO dto = new CategoryServiceCartDTO();
                        dto.setCategoryName(k);
                        dto.setDuration(duration);
                        dto.setServiceNames(new ArrayList<>());
                        dto.setTotal(0.0);
                        return dto;
                    });

                    CategoryServiceCartDTO existing = resultMap.get(key);
                    existing.getServiceNames().add(service.getServiceName());
                    existing.setTotal(existing.getTotal() + serviceTotal);
                }
            }
        }

        return new ArrayList<>(resultMap.values());
    }
}
