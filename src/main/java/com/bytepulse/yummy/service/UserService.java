package com.bytepulse.yummy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bytepulse.yummy.model.User;
import com.bytepulse.yummy.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, Long> otpExpiry = new HashMap<>();

    public UserService(UserRepository _userRepository, PasswordEncoder _encoder) {
        this.userRepository = _userRepository;
        this.encoder = _encoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(userDetails.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Converting userDetail to UserDetails
        Optional<User> userDetail = userRepository.findByEmail(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String generateOtp(String email) {
        System.out.println("Generating OTP for : " + email);
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("User found: " + user.isEmpty());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        String otp = String.valueOf((int) (Math.random() * 9000) + 1000); // Generate 4-digit OTP
        otpStorage.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + (5 * 60 * 1000)); // 5 minutes expiry
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        Long expiryTime = otpExpiry.get(email);

        if (storedOtp != null && expiryTime != null) {
            if (storedOtp.equals(otp) && System.currentTimeMillis() < expiryTime) {
                otpStorage.remove(email);
                otpExpiry.remove(email);
                return true;
            }
        }
        return false;
    }

    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
}
