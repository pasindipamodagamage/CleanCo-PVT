package lk.ijse.cleancopvt.service;


import lk.ijse.cleancopvt.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
}