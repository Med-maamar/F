package tn.esprit.spring.services.foyer;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.dao.entities.*;
import tn.esprit.spring.dao.repositories.BlocRepository;
import tn.esprit.spring.dao.repositories.FoyerRepository;
import tn.esprit.spring.dao.repositories.UniversiteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FoyerService implements IFoyerService {
    private final FoyerRepository foyerRepository;
    FoyerRepository repo;
    UniversiteRepository universiteRepository;
    BlocRepository blocRepository;

    @Override
    public Foyer addOrUpdate(Foyer f) {
        return repo.save(f);
    }

    @Override
    public List<Foyer> findAll() {
        return repo.findAll();
    }

    @Override
    public Foyer findById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foyer non trouvé avec ID: " + id));
    }


    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public void delete(Foyer f) {
        repo.delete(f);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer f = findById(idFoyer); // Child
        Universite u = universiteRepository.findByNomUniversite(nomUniversite); // Parent
        // On affecte le child au parent
        u.setFoyer(f);
        return universiteRepository.save(u);
    }


    @Override
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        // Récuperer la liste des blocs avant de faire l'ajout
        List<Bloc> blocs = foyer.getBlocs();
        // Foyer est le child et universite est parent
        Foyer f = repo.save(foyer);
        Universite u = universiteRepository.findById(idUniversite).get();
        // Foyer est le child et bloc est le parent
        //On affecte le child au parent
        for (Bloc bloc : blocs) {
            bloc.setFoyer(foyer);
            blocRepository.save(bloc);
        }
        u.setFoyer(f);
        return universiteRepository.save(u).getFoyer();
    }

    @Override
    public Foyer ajoutFoyerEtBlocs(Foyer foyer) {

        List<Bloc> blocs = foyer.getBlocs();
        foyer = repo.save(foyer);
        for (Bloc b : blocs) {
            b.setFoyer(foyer);
            blocRepository.save(b);
        }
        return foyer;
    }

    @Override
    public Universite affecterFoyerAUniversite(long idF, long idU) {
        Universite u = universiteRepository.findById(idU)
                .orElseThrow(() -> new EntityNotFoundException("Université non trouvée avec ID: " + idU));

        Foyer f = foyerRepository.findById(idF)
                .orElseThrow(() -> new EntityNotFoundException("Foyer non trouvé avec ID: " + idF));

        u.setFoyer(f);
        return universiteRepository.save(u);
    }


    @Override
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        Universite u = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new EntityNotFoundException("Université non trouvée avec ID: " + idUniversite));

        u.setFoyer(null);
        return universiteRepository.save(u);
    }



}
