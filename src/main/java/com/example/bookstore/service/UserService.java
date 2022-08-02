package com.example.bookstore.service;

import com.example.bookstore.dto.LoginDTO;
import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.exception.BookStoreException;
import com.example.bookstore.exception.UserException;
import com.example.bookstore.model.Email;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRegistrationRepository;
import com.example.bookstore.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements iUserService {
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    iEmailService iEmailService;
    @Override
    public List<User> getUserData() {
        return userRegistrationRepository.findAll();
    }

    @Override
    public User  getUserDataById(String token) {
        int id = tokenUtil.decodeToken(token);
        return userRegistrationRepository.findById(id).orElseThrow(() -> new UserException
                ("Employee id  " + id + " note Found "));
    }

    @Override
    public User createUserData(UserDTO userDTO) {
        User userData = new User(userDTO);
        userRegistrationRepository.save(userData);
        String token = tokenUtil.createToken(userData.getId());
        userData.setToken(token);
        Email email = new Email(userData.getEmail(),"User Is Registered",userData.getUserName()  +
                userData.getEmail()+"\n" + userData.getAddress() + "\n" + userData.getToken()+"\n"+userData.getPassword() + "=>" + iEmailService.getLink(token));
        iEmailService.sendMail(email);
        return userRegistrationRepository.save(userData);
    }

    @Override
    public User updateUserData(String token, UserDTO userDTO){
        int id = tokenUtil.decodeToken(token);
        User updatedData = this.getUserDataById(id);
        updatedData.updateUser(userDTO);
        return userRegistrationRepository.save(updatedData);
    }
    private User getUserDataById(int id) {
        return userRegistrationRepository.findById(id).
                orElseThrow(() -> new UserException("User  with id " + id + " does not exist in database..!"));
    }




    @Override
    public String deleteUserData(String token) {
        int id = tokenUtil.decodeToken(token);
        Optional<User> user = userRegistrationRepository.findById(id);
        if (user.isPresent()){
            userRegistrationRepository.deleteById(id);
            return "Data Deleted";
        }
        throw new BookStoreException("User id " + id +" is not found");
    }

    @Override
    public User Search(LoginDTO loginDTO) {
        return userRegistrationRepository.find(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @Override
    public String forgotPassword(LoginDTO loginDTO){
        User user =  userRegistrationRepository.forgotPassword(loginDTO.email);
        if(user != null){
            return user.getPassword();
        }else {
            return "Invalid Email id:-";
        }

    }
    @Override
    public User verifyUser(String token) {
        User user = this.getUserDataById(token);
        user.setVerified(true);
        userRegistrationRepository.save(user);
        return user;
    }
}
