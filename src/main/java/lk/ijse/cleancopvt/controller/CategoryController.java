package lk.ijse.cleancopvt.controller;

import io.jsonwebtoken.Claims;
import lk.ijse.cleancopvt.Enum.Role;
import lk.ijse.cleancopvt.dto.CategoryDTO;
import lk.ijse.cleancopvt.dto.ResponseDTO;
import lk.ijse.cleancopvt.service.CategoryService;
import lk.ijse.cleancopvt.util.JwtUtil;
import lk.ijse.cleancopvt.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final ResponseDTO responseDTO;

    public CategoryController(CategoryService categoryService, JwtUtil jwtUtil, ResponseDTO responseDTO) {
        this.categoryService = categoryService;
        this.jwtUtil = jwtUtil;
        this.responseDTO = responseDTO;
    }

    // Add a new category
    @PostMapping("/saveCategory")
    public ResponseEntity<String> addCategory(@RequestHeader("Authorization") String authorization, @RequestBody CategoryDTO categoryDTO) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            categoryService.addCategory(categoryDTO);
            return ResponseEntity.status(VarList.Created).body("Category added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error adding category: " + e.getMessage());
        }
    }

    // Update an existing category
    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<String> updateCategory(@RequestHeader("Authorization") String authorization, @PathVariable("id") UUID id, @RequestBody CategoryDTO categoryDTO) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            categoryDTO.setId(id);  // Set the ID for the category
            categoryService.updateCategory(categoryDTO);
            return ResponseEntity.status(VarList.OK).body("Category updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error updating category: " + e.getMessage());
        }
    }

    // Delete a category by ID
    @DeleteMapping("/removeCategory/{id}")
    public ResponseEntity<String> deleteCategory(@RequestHeader("Authorization") String authorization, @PathVariable("id") UUID id) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body("Access denied: You do not have the required role.");
        }

        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.status(VarList.OK).body("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body("Error deleting category: " + e.getMessage());
        }
    }

    // View all categories
    @GetMapping("getAllCategory")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@RequestHeader("Authorization") String authorization) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body(null);
        }

        try {
            List<CategoryDTO> categories = categoryService.getAllCategories();
            return ResponseEntity.status(VarList.OK).body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body(null);
        }
    }

    // View a category by ID
    @GetMapping("/getEachOneCategory/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@RequestHeader("Authorization") String authorization, @PathVariable("id") UUID id) {
        if (!hasRequiredRole(authorization, Role.Administrator, Role.Employee)) {
            return ResponseEntity.status(VarList.Forbidden).body(null);
        }

        try {
            CategoryDTO categoryDTO = categoryService.getCategoryById(id);
            if (categoryDTO != null) {
                return ResponseEntity.status(VarList.OK).body(categoryDTO);
            } else {
                return ResponseEntity.status(VarList.Not_Found).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error).body(null);
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
