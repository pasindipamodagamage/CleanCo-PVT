package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.dto.UpdateUserDTO;
import lk.ijse.cleancopvt.dto.UserDTO;
import lk.ijse.cleancopvt.entity.User;
import lk.ijse.cleancopvt.repo.UserRepo;
import lk.ijse.cleancopvt.service.UserService;
import lk.ijse.cleancopvt.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepo userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(getAuthority(user))
                .build();
    }

    @Override
    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String username) {
        if (userRepository.existsByEmail(username)) {
            User user=userRepository.findByEmail(username);
            return modelMapper.map(user,UserDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    @Override
    public int deleteAccount(String username) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            user.setActive(false);
            user.setPassword(null);
            userRepository.save(user);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int updateUser(UpdateUserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (user != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (userDTO.getCurrentPassword() != null &&
                    !encoder.matches(userDTO.getCurrentPassword(), user.getPassword())) {
                return VarList.Unauthorized;
            }

            user.setProfilePic(userDTO.getProfilePic());
            user.setName(userDTO.getName());
            user.setAddress(userDTO.getAddress());
            user.setPrimaryContact(userDTO.getPrimaryContact());
            user.setSecondaryContact(userDTO.getSecondaryContact());
            user.setNicNumber(userDTO.getNicNumber());

            if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
                user.setPassword(encoder.encode(userDTO.getPassword()));
            }

            userRepository.save(user);
            return VarList.Created;
        }

        return VarList.Not_Found;
    }

    @Override
    public int toggleUserStatus(String nicNumber) {
        User user = userRepository.findByNicNumber(nicNumber);
        if (user != null) {
            user.setActive(!user.isActive());
            userRepository.save(user);
            return VarList.OK;
        }
        return VarList.Not_Found;
    }

}
