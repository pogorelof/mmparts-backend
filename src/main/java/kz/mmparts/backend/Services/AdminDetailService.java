package kz.mmparts.backend.Services;

import kz.mmparts.backend.Models.Admin;
import kz.mmparts.backend.Repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminDetailService implements UserDetailsService {

    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found"));
        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword_hash())
                .roles("ADMIN")
                .build();
    }
}
