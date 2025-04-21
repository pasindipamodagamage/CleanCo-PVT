package lk.ijse.cleancopvt.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lk.ijse.cleancopvt.Enum.BookingStatus;
import lk.ijse.cleancopvt.Enum.Role;
import lk.ijse.cleancopvt.dto.*;
import lk.ijse.cleancopvt.repo.UserRepo;
import lk.ijse.cleancopvt.service.impl.UserServiceImpl;
import lk.ijse.cleancopvt.util.JwtUtil;
import lk.ijse.cleancopvt.util.ResponseUtil;
import lk.ijse.cleancopvt.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*")
public class UserController {

    private final UserServiceImpl userService;

    private final JwtUtil jwtUtil;

    public UserController(UserServiceImpl userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/registerUser")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("register");
        try {
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<ResponseDTO> deleteAccount(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "User not logged in", null));
        }

        int result = userService.deleteAccount(userDTO.getEmail());

        if (result == VarList.Created) {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.Created, "Account deactivated successfully", null));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.Created, "Logout successful", null));
    }

    @GetMapping("/getRole")
    public ResponseEntity<ResponseDTO> getRole(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Missing or invalid token", null));
        }

        String token = authorization.substring(7);
        Claims claims = jwtUtil.getUserRoleCodeFromToken(token);
        String userRole = claims.get("role", String.class);

        if (userRole == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Role not found in token", null));
        }

        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Role fetched", userRole));
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<ResponseDTO> updateUserProfile(@RequestBody UpdateUserDTO updateUserDTO,
                                                         HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "No JWT token found", null));
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.getUsernameFromToken(token);

        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid token payload", null));
        }

        updateUserDTO.setEmail(email);
        int result = userService.updateUser(updateUserDTO);

        if (result == VarList.Created) {
            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Profile updated successfully", null));
        } else if (result == VarList.Unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid current password", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDTO> getLoggedUser(@RequestHeader("Authorization") String authorization) {
        try {
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO(VarList.Unauthorized, "Missing or invalid token", null));
            }

            String token = authorization.substring(7);
            String email = jwtUtil.getUsernameFromToken(token);

            if (email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO(VarList.Unauthorized, "Invalid token payload", null));
            }

            UserDTO userDTO = userService.searchUser(email);
            if (userDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
            }

            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User fetched successfully", userDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Users fetched successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PutMapping("/toggleStatus/{nicNumber}")
    public ResponseEntity<ResponseDTO> toggleUserStatus(@PathVariable String nicNumber) {
        try {
            int result = userService.toggleUserStatus(nicNumber);
            if (result == VarList.OK) {
                return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User status toggled", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<ResponseDTO> createEmployee(@RequestBody @Valid UserDTO userDTO,
                                                      @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Claims claims = jwtUtil.getUserRoleCodeFromToken(token);
        String userRole = claims.get("role", String.class);

        if (!"Administrator".equals(userRole)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO(VarList.Forbidden, "Only Administrators Can Manage Employees.", null));
        }

        userDTO.setRole(Role.Employee);
        try {
            int res = userService.saveUser(userDTO);
            if (res == VarList.Created) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Employee created successfully.", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/countCustomers")
    public ResponseEntity<ResponseUtil> getCustomerCount() {
        try {
            long countCustomer = userService.countCustomer();
            return ResponseEntity.ok(new ResponseUtil(VarList.OK, "Customer count fetched successfully", countCustomer));
        } catch (Exception e) {
            return ResponseEntity.status(VarList.Internal_Server_Error)
                    .body(new ResponseUtil(VarList.Internal_Server_Error, "Failed to retrieve customer count: " + e.getMessage(), null));
        }
    }

    @PutMapping("/deleteAccountByAdmin/{email}")
    public ResponseEntity<ResponseDTO> deleteAccountByAdmin(@PathVariable String email) {
        int result = userService.deleteAccount(email);

        if (result == VarList.Created) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.Created, "Account deactivated successfully", null));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
    }


}
