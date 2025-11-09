package hightech.edu.inscription.controller;

import hightech.edu.inscription.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthWebController {

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        // Ajouter le nombre d'étudiants pour les statistiques
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                model.addAttribute("etudiants", etudiantService.getAllEtudiants());
            } catch (Exception e) {
                // En cas d'erreur, initialiser avec une liste vide
                model.addAttribute("etudiants", java.util.Collections.emptyList());
            }
        } else {
            // Pour les utilisateurs non connectés, liste vide
            model.addAttribute("etudiants", java.util.Collections.emptyList());
        }
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
}