package com.project.demo.logic.entity.rol;


import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserSeeder  implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserSeeder(
            RoleRepository roleRepository,
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createUser();
    }


    private void createUser() {
        User newUser = new User();
        newUser.setName("Regular");
        newUser.setLastname("user");
        newUser.setEmail("regular.user@gmail.com");
        newUser.setPassword("user123");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        Optional<User> optionalUser = userRepository.findByEmail(newUser.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var regular_user = new User();
        regular_user.setName(newUser.getName());
        regular_user.setLastname(newUser.getLastname());
        regular_user.setEmail(newUser.getEmail());
        regular_user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        regular_user.setRole(optionalRole.get());

        userRepository.save(regular_user);
    }













}
