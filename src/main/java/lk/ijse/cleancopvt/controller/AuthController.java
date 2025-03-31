package lk.ijse.cleancopvt.controller;


import jakarta.servlet.http.HttpSession;
import lk.ijse.cleancopvt.dto.AuthDTO;
import lk.ijse.cleancopvt.dto.ResponseDTO;
import lk.ijse.cleancopvt.dto.UserDTO;
import lk.ijse.cleancopvt.service.impl.UserServiceImpl;
import lk.ijse.cleancopvt.util.JwtUtil;
import lk.ijse.cleancopvt.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin("*")
public class AuthController {

    private final JwtUtil jwtUtil;

    private final UserServiceImpl userService;


    //constructor injection
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        System.out.println("Authenticating user: " + userDTO.getEmail());

        try {
            UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());
            if (loadedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO(VarList.Unauthorized, "User not found", null));
            }

            // Compare passwords
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(userDTO.getPassword(), loadedUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", null));
            }

            // Generate Token
            String token = jwtUtil.generateToken(loadedUser);
            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.Created, "Login Successful", authDTO));

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


}

