package ru.kata.spring.boot_security.demo.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing data...");

        Role adminRole = createRoleIfNotExists("ROLE_ADMIN");
        Role userRole = createRoleIfNotExists("ROLE_USER");

        createUserIfNotExists("admin", "admin", adminRole);
        createUserIfNotExists("user", "user", userRole);
    }

    private Role createRoleIfNotExists(String role) {
        Optional<Role> existingRole = roleRepository.findByRole(role);
        if (existingRole.isPresent()) {
            return existingRole.get();
        } else {
            Role newRole = new Role();
            newRole.setRole(role);
            return roleRepository.save(newRole);
        }
    }

    private void createUserIfNotExists(String name, String password, Role role) {
        if (!userRepository.existsByName(name)) {
            System.out.println("Creating user: " + name);
            User user = new User();
            user.setName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }
}