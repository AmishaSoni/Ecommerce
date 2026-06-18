package com.app.ecom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUser() {
        return userRepository.findAll().stream().map(this::mapUserToUserResponse).collect(Collectors.toList());
    }

     public void addUser(UserRequest userRequest) {
        User user = new User();
        mapUserToUserRequest(user,userRequest);
        userRepository.save(user);

    }  

    public UserResponse fetchUserById(Long id) {

        return userRepository.findById(id).map(this::mapUserToUserResponse).get();
    }

     public boolean updateUserById(UserRequest userRequest,Long id) {

        return  userRepository.findById(id).map(existingUser -> {mapUserToUserRequest(existingUser, userRequest);
                                                                 userRepository.save(existingUser);   
                                                                  return true;
                                                                }).orElse(false); 
    
     }

     public void deleteUser(Long id) {
        userRepository.deleteById(id);
     }


     /*Helper Method */

     public UserResponse mapUserToUserResponse(User user) {

        UserResponse userResponse = new UserResponse();

        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setRole(user.getRole());

        if(user.getAddress()!=null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setPincode(user.getAddress().getPincode());

            userResponse.setAddress(addressDTO);
        }
        return userResponse;
     }

     public void mapUserToUserRequest(User user,UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());

        if(userRequest.getAddress()!=null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setPincode(userRequest.getAddress().getPincode());

            user.setAddress(address);
        }
      
     }

}
