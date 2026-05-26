package se.jensen.felicia.cloudstore.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import se.jensen.felicia.cloudstore.model.User;
import se.jensen.felicia.cloudstore.repository.UserRepository;
import se.jensen.felicia.cloudstore.security.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("LOGG: Försöker logga in användare: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("LOGG: Hittade inte användare i databasen.");
                    return new UsernameNotFoundException("User not found: " + email);
                });
        System.out.println("LOGG: Hittade användare. " + email);

        return new MyUserDetails(user);
    }
}
