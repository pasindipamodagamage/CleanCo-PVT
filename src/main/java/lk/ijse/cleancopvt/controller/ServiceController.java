package lk.ijse.cleancopvt.controller;

import io.jsonwebtoken.Claims;
import lk.ijse.cleancopvt.Enum.Role;
import lk.ijse.cleancopvt.dto.CategoryServiceCartDTO;
import lk.ijse.cleancopvt.dto.ResponseDTO;
import lk.ijse.cleancopvt.dto.ServicesSetDTO;
import lk.ijse.cleancopvt.service.ServiceManagementService;
import lk.ijse.cleancopvt.util.JwtUtil;
import lk.ijse.cleancopvt.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service")
@CrossOrigin("*")
public class ServiceController {

    @Autowired
    private final ServiceManagementService serviceManagementService;

    @Autowired
    private final JwtUtil jwtUtil;

    public ServiceController(ServiceManagementService serviceManagementService, JwtUtil jwtUtil) {
        this.serviceManagementService = serviceManagementService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/saveService")
    public ResponseEntity<String> addService(@RequestHeader("Authorization") String authorization, @RequestBody ServicesSetDTO serviceDTO) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            serviceManagementService.addService(serviceDTO);
            return ResponseEntity.status(VarList.Created).body("Service added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error adding service: " + e.getMessage());
        }
    }

    @PutMapping("/updateService/{id}")
    public ResponseEntity<String> updateService(@RequestHeader("Authorization") String authorization, @PathVariable("id") UUID id, @RequestBody ServicesSetDTO serviceDTO) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            serviceDTO.setId(id);
            serviceManagementService.updateService(serviceDTO);
            return ResponseEntity.status(VarList.OK).body("Service updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error updating service: " + e.getMessage());
        }
    }

    @DeleteMapping("/removeService/{id}")
    public ResponseEntity<String> deleteService(@RequestHeader("Authorization") String authorization, @PathVariable("id") UUID id) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            serviceManagementService.deleteService(id);
            return ResponseEntity.status(VarList.OK).body("Service deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error deleting service: " + e.getMessage());
        }
    }

    @GetMapping("/getAllServices")
    public ResponseEntity<List<ServicesSetDTO>> getAllServices(@RequestHeader("Authorization") String authorization) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee, Role.Customer)) {
            return ResponseEntity.status(VarList.Forbidden).body(null);
        }

        try {
            List<ServicesSetDTO> services = serviceManagementService.getAllServices();
            return ResponseEntity.status(VarList.OK).body(services);
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body(null);
        }
    }

    @GetMapping("/getServiceById/{id}")
    public ResponseEntity<ServicesSetDTO> getServiceById(@RequestHeader("Authorization") String authorization, @PathVariable("id") UUID id) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee, Role.Customer)) {
            return ResponseEntity.status(VarList.Forbidden).body(null);
        }

        try {
            ServicesSetDTO serviceDTO = serviceManagementService.getServiceById(id);
            if (serviceDTO != null) {
                return ResponseEntity.status(VarList.OK).body(serviceDTO);
            } else {
                return ResponseEntity.status(VarList.Not_Found).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body(null);
        }
    }

    @GetMapping("/serviceCartDetail")
    public ResponseEntity<ResponseDTO> getServiceCartDetails() {
        try {
            List<CategoryServiceCartDTO> details = serviceManagementService.getAllServiceCartDetails();
            return ResponseEntity.ok(new ResponseDTO(200, "Success", details));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseDTO(500, "Server Error", null));
        }
    }


    private boolean hasRequiredRole(String authorization, Role... roles) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return false;
        }

        String token = authorization.substring(7);
        Claims claims = jwtUtil.getUserRoleCodeFromToken(token);
        String userRole = claims.get("role", String.class);

        for (Role role : roles) {
            if (role.name().equalsIgnoreCase(userRole)) {
                return true;
            }
        }

        return false;
    }
}
