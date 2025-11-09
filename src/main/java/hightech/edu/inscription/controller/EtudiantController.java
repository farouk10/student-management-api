package hightech.edu.inscription.controller;

import hightech.edu.inscription.entity.Etudiant;
import hightech.edu.inscription.service.EtudiantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api-1/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping("/list")
    public List<Etudiant> getAllEtudiants(){
        return  etudiantService.getAllEtudiants();
    }

    @GetMapping("/{id}")
    public Etudiant getEtudiantById(@PathVariable Long id){
        return etudiantService.getEtudiantById(id);
    }

    @PostMapping
    public Etudiant addEtudiant(@Valid @RequestBody Etudiant etudiant){
        return etudiantService.addEtudiant(etudiant);
    }

    @PutMapping("/{id}")
    public Etudiant updateEtudiant(@PathVariable Long id,@Valid @RequestBody Etudiant etudiant){
        return etudiantService.updateEtudiant(id,etudiant);
    }

    @DeleteMapping("/{id}")
    public void deleteEtudiant(@PathVariable Long id){
        etudiantService.deleteEtudiant(id);
    }

}
