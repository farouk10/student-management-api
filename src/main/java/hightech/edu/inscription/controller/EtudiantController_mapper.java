package hightech.edu.inscription.controller;

import hightech.edu.inscription.dto.EtudiantRequest;
import hightech.edu.inscription.dto.EtudiantResponse;
import hightech.edu.inscription.entity.Etudiant;
import hightech.edu.inscription.mapper.EtudiantMapper;
import hightech.edu.inscription.service.EtudiantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController_mapper {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private EtudiantMapper etudiantMapper;

    // Lecture - accessible à USER et ADMIN
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<EtudiantResponse> getAllEtudiants(){
        return etudiantService.getAllEtudiants()
                .stream()
                .map(etudiantMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Lecture - accessible à USER et ADMIN
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public EtudiantResponse getEtudiantById(@PathVariable Long id){
        Etudiant etudiant = etudiantService.getEtudiantById(id);
        return etudiantMapper.toResponse(etudiant);
    }

    // Écriture - accessible seulement à ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EtudiantResponse addEtudiant(@Valid @RequestBody EtudiantRequest dto){
        Etudiant etudiant = etudiantMapper.toEntity(dto);
        Etudiant saved = etudiantService.addEtudiant(etudiant);
        return etudiantMapper.toResponse(saved);
    }

    // Écriture - accessible seulement à ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EtudiantResponse updateEtudiant(@PathVariable Long id, @Valid @RequestBody EtudiantRequest dto){
        Etudiant etudiant = etudiantMapper.toEntity(dto);
        Etudiant updated = etudiantService.updateEtudiant(id, etudiant);
        return etudiantMapper.toResponse(updated);
    }

    // Écriture - accessible seulement à ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEtudiant(@PathVariable Long id){
        etudiantService.deleteEtudiant(id);
    }
}