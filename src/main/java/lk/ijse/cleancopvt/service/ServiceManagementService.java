package lk.ijse.cleancopvt.service;

import lk.ijse.cleancopvt.dto.ServicesSetDTO;

import java.util.List;
import java.util.UUID;

public interface  ServiceManagementService {

    public void addService(ServicesSetDTO serviceDTO);

    public void updateService(ServicesSetDTO serviceDTO);

    public void deleteService(UUID id);

    public List<ServicesSetDTO> getAllServices();

    public ServicesSetDTO getServiceById(UUID id);
}
