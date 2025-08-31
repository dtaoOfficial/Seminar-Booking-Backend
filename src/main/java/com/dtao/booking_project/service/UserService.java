package com.dtao.booking_project.service;

import com.dtao.booking_project.model.User;
import com.dtao.booking_project.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Regex for email validation -> only @newhorizonindia.edu allowed
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@newhorizonindia\\.edu$");

    // Regex for Indian phone numbers (start with 6-9, 10 digits)
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[6-9][0-9]{9}$");

    // -------------------------
    // Create User (Registration)
    // -------------------------
    public User addUser(User user) {
        // Validate name
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new RuntimeException("Name is required!");
        }

        // Validate email format
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new RuntimeException("Invalid email! Must end with @newhorizonindia.edu");
        }

        // Validate phone format
        if (!PHONE_PATTERN.matcher(user.getPhone()).matches()) {
            throw new RuntimeException("Invalid phone number! Must be 10 digits starting with 6/7/8/9");
        }

        // Check duplicates (email + phone together)
        boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean phoneExists = userRepository.findByPhone(user.getPhone()).isPresent();

        if (emailExists && phoneExists) {
            throw new RuntimeException("Email and Phone already registered!");
        } else if (emailExists) {
            throw new RuntimeException("Email already registered!");
        } else if (phoneExists) {
            throw new RuntimeException("Phone already registered!");
        }

        // Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user to DB
        return userRepository.save(user);
    }

    // -------------------------
    // Authenticate user (Login)
    // -------------------------
    public User authenticateUser(String email, String rawPassword) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (passwordEncoder.matches(rawPassword, user.get().getPassword())) {
                return user.get();
            }
        }
        return null;
    }

    // -------------------------
    // Get all users
    // -------------------------
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // -------------------------
    // Get single user by ID
    // -------------------------
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    // -------------------------
    // Delete user
    // -------------------------
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
