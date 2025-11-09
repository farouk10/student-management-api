package hightech.edu.inscription.controller;

import hightech.edu.inscription.service.EtudiantService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {

    @Autowired
    private EtudiantService etudiantService;

    // Gestionnaire d'erreurs principal
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Authentication authentication) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Ajouter des informations au modèle
            model.addAttribute("statusCode", statusCode);
            model.addAttribute("timestamp", new Date());
            model.addAttribute("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

            if (authentication != null) {
                model.addAttribute("username", authentication.getName());
            }

            // Gérer les différents codes d'erreur
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "access-denied";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "redirect:/login";
            }
        }

        // Erreur générique
        return "error/error";
    }

    // Route pour les ressources statiques non trouvées
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFound(NoResourceFoundException ex) {
        return "error/404";
    }
}
