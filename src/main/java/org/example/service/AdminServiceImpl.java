package org.example.service;

import org.example.entity.Provider;
import org.example.entity.User;
import org.example.repository.ProviderRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.util.Role.ADMIN;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ProviderService providerService;
    private final ProviderRepository providerRepository;

    public AdminServiceImpl(UserService userService, UserRepository userRepository, ProviderService providerService, ProviderRepository providerRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.providerService = providerService;
        this.providerRepository = providerRepository;
    }

    @Override
    public void changeToAdmin(Long accountId) {
        Optional<User> userOpc = userService.getOneById(accountId);
        Provider provider = providerService.getOne(accountId);

        if (userOpc.isPresent()) {
            User user = userOpc.get();
            user.setRole(ADMIN);
            userRepository.save(user);
        }
        if (provider != null) {
            provider.setRole(ADMIN);
            providerRepository.save(provider);
        }
    }
}
