package hightech.edu.inscription.controller;

import hightech.edu.inscription.dto.LoginRequest;
import hightech.edu.inscription.dto.SignupRequest;
import hightech.edu.inscription.dto.JwtResponse;
import hightech.edu.inscription.entity.User;
import hightech.edu.inscription.repository.UserRepository;
import hightech.edu.inscription.security.JwtUtils;
import hightech.edu.inscription.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Étape i & ii: Authentifier avec AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Mettre l'authentification dans le contexte
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Étape iii: Générer le JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Récupérer les détails de l'utilisateur et les rôles
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Étape iv: Retourner la réponse avec le token, username et rôles
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        // Étape i: Valider que le username n'existe pas déjà
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Valider que l'email n'existe pas déjà
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Étape ii: Hasher le mot de passe
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        // Étape iii: Créer et enregistrer l'utilisateur avec rôle(s)
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encodedPassword);

        // Gérer les rôles
        HashSet<String> roles = new HashSet<>();
        if (signUpRequest.getRoles() != null && !signUpRequest.getRoles().isEmpty()) {
            // Si des rôles sont fournis, les utiliser
            String[] rolesArray = signUpRequest.getRoles().split(",");
            for (String role : rolesArray) {
                roles.add(role.trim());
            }
        } else {
            // Sinon, rôle par défaut
            roles.add("ROLE_USER");
        }
        user.setRoles(roles);

        userRepository.save(user);

        // Étape iv: Retourner la réponse de confirmation
        return ResponseEntity.ok("User registered successfully!");
    }
}