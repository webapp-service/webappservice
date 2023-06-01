package org.example.service;

import org.example.entity.Person;
import org.example.entity.Provider;
import org.example.repository.ProviderRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService implements UserDetailsService {
    private final ProviderRepository providerRepository;
    private final UserRepository userRepository;

    public PersonService(ProviderRepository providerRepository, UserRepository userRepository) {
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Provider provider = providerRepository.findByEmail(email);
        org.example.entity.User user = userRepository.findByEmail(email);

        Person person = null;

        if (provider != null && provider.isActive()) {
            person = provider;
        } else if (user != null && user.isActive()) {
            person = user;
        }

        if (person != null) {
            List<GrantedAuthority> permits = new ArrayList<>();

            GrantedAuthority permit = new SimpleGrantedAuthority("ROLE_" + person.getRole().toString());
            permits.add(permit);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usersession", person);

            return new User(person.getEmail(), person.getPassword(), permits);
        } else {
            return null;
        }
    }
}
