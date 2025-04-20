package lk.ijse.cleancopvt.service;


import lk.ijse.cleancopvt.dto.UpdateUserDTO;
import lk.ijse.cleancopvt.dto.UserDTO;
import lk.ijse.cleancopvt.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {
    int saveUser(UserDTO userDTO);

    UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException;

    Set<SimpleGrantedAuthority> getAuthority(User user);

    UserDTO searchUser(String username);

    int deleteAccount(String username);

    List<UserDTO> getAllUsers();

    int updateUser(UpdateUserDTO userDTO);

    int toggleUserStatus(String nicNumber);
}