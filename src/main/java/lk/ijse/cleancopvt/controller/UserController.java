package lk.ijse.cleancopvt.controller;

import jakarta.validation.Valid;
import lk.ijse.cleancopvt.dto.AuthDTO;
import lk.ijse.cleancopvt.dto.ResponseDTO;
import lk.ijse.cleancopvt.dto.UserDTO;
import lk.ijse.cleancopvt.service.UserService;
import lk.ijse.cleancopvt.util.JwtUtil;
import lk.ijse.cleancopvt.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final ResponseDTO responseDTO;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil, ResponseDTO responseDTO) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.responseDTO = responseDTO;
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

    @PostMapping("/{email}")
    public ResponseEntity<ResponseDTO> deactiveUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("deactiveUser");
        try {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
