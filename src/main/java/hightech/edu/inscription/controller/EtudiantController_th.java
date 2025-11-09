package hightech.edu.inscription.controller;

import hightech.edu.inscription.entity.Etudiant;
import hightech.edu.inscription.service.EtudiantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/etudiants")
public class EtudiantController_th {

    @Autowired
    private EtudiantService etudiantService;

    // Lecture - accessible à USER et ADMIN
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getAllEtudiants(Model model){
        model.addAttribute("etudiants", etudiantService.getAllEtudiants());
        return "ListEtudiants";
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String details(@PathVariable Long id, Model model){
        model.addAttribute("etudiant", etudiantService.getEtudiantById(id));
        return "detailsEtudiant";
    }

    // Écriture - accessible seulement à ADMIN
    @GetMapping("/ajout")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddForm(Model model){
        model.addAttribute("etudiant", new Etudiant());
        return "formEtudiant";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@Valid @ModelAttribute Etudiant etudiant, BindingResult result){
        if (result.hasErrors()) {
            return "formEtudiant";
        }
        etudiantService.addEtudiant(etudiant);
        return "redirect:/etudiants/list";
    }

    @GetMapping("/modifier/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model){
        model.addAttribute("etudiant", etudiantService.getEtudiantById(id));
        return "modifierEtudiantForm";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("etudiant") Etudiant etudiant, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "modifierEtudiantForm";
        }
        etudiantService.updateEtudiant(id, etudiant);
        return "redirect:/etudiants/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id){
        etudiantService.deleteEtudiant(id);
        return "redirect:/etudiants/list";
    }

}