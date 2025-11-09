package hightech.edu.inscription.service;

import hightech.edu.inscription.entity.Etudiant;
import hightech.edu.inscription.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    @Autowired
    EtudiantRepository etudiantRepository;

    public List<Etudiant> getAllEtudiants() {

        return  etudiantRepository.findAll();
    }

    public Etudiant getEtudiantById(Long id){
        Optional<Etudiant> et = etudiantRepository.findById(id);
        return et.orElseThrow(() -> new RuntimeException("Etudiant non trouve avec id=" + id));
    }

    public Etudiant addEtudiant(Etudiant etudiant){
        return etudiantRepository.save(etudiant);
    }

    public Etudiant updateEtudiant(Long id,Etudiant etudiantDetails){
        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(() -> new RuntimeException("Etudiant non trouve avec id=" + id));
        etudiant.setNom(etudiantDetails.getNom());
        etudiant.setPrenom(etudiantDetails.getPrenom());
        etudiant.setEmail(etudiantDetails.getEmail());
        etudiant.setAge(etudiantDetails.getAge());
        return etudiantRepository.save(etudiant);
    }

    public void deleteEtudiant(Long id){
        Etudiant et = etudiantRepository.findById(id).orElseThrow(() -> new RuntimeException("Etudiant non trouve avec id=" + id));
        etudiantRepository.delete(et);
    }
}
