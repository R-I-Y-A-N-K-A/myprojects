package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtService;

    public Users addUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword())); //this encrypts password
        if (user.getRole()==null) {
            user.setRole(Role.USER); //default role set to user
        }
        return userRepository.save(user);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found!"));
    }

    
    public Users updateUser(Long id, Users updatedUser){
        Users user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found!"));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setPhno(updatedUser.getPhno());
        return userRepository.save(user);
    }
    
    public String deleteUser(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "User deleted successfully!";
        } else{
            throw new UserNotFoundException("User with ID " + id + " not found!");
        }
    }

    public String loginUser(String username, String password){
        Optional<Users> user=userRepository.findByUsername(username);
        if(user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())){
            return jwtService.generateToken(user.get().getUsername());
        } else{
            throw new UserNotFoundException("Invalid username or password");
        }
    }
}
