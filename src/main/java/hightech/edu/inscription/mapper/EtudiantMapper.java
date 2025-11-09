package hightech.edu.inscription.mapper;

import ch.qos.logback.core.model.ComponentModel;
import hightech.edu.inscription.dto.EtudiantRequest;
import hightech.edu.inscription.dto.EtudiantResponse;
import hightech.edu.inscription.entity.Etudiant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EtudiantMapper {
    EtudiantMapper INSTANCE = Mappers.getMapper(EtudiantMapper.class);
    // DTO -> entity
    Etudiant toEntity(EtudiantRequest dto);

    // Entity -> DTO (on peut customiser la sortie  )
    @Mapping(target = "nomComplet", expression = "java(etudiant.getNom() +  \" \" + etudiant.getPrenom())")
    EtudiantResponse toResponse(Etudiant etudiant);

}
