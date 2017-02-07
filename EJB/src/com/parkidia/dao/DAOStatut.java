/*
 * DAOStatut.java
 */
package com.parkidia.dao;

import com.parkidia.modeles.place.IPlace;
import com.parkidia.modeles.place.statut.IStatut;

import javax.ejb.Singleton;
import java.util.List;

/**
 * DAO permettant de gérer les statuts de places de parking dans la base de
 * données.
 */
@Singleton
public class DAOStatut extends AbstractDAO<IStatut> {

    @Override
    public void creer(IStatut entite) {
        em.persist(entite);
    }

    @Override
    public IStatut maj(IStatut entite) {
        return em.merge(entite);
    }

    @Override
    public void supprimer(IStatut entite) {
        em.detach(entite);
    }

    @Override
    public IStatut rechercher(IStatut entite) {
        return em.find(IStatut.class, entite.getId());
    }

    @Override
    public List<IStatut> rechercherTous() {
        return em.createQuery("select s from Statut s", IStatut.class)
                 .getResultList();
    }

    /**
     * Retourne tous les status d'une place de parking rangées du plus récent au
     * plus ancien.
     * @param place la place de parking dont on veut savoir le statut.
     * @return la liste des statuts de la place.
     */
    public List<IStatut> statusPlace(IPlace place) {
        return em.createQuery(
                "select s from Statut s where s.place.nom = :nomPlace " +
                "and s.place.parking.id = :idParking order by s.date desc ",
                IStatut.class)
                 .setParameter("nomPlace", place.getNom())
                 .setParameter("idParking", place.getParking().getId())
                 .getResultList();
    }

    /**
     * Retourne le dernier statut de la place de parking passé en argument.
     * @param place la place de parking dont on veut connaître le dernier
     * statut.
     * @return le dernier statut de la place.
     */
    public IStatut dernierStatut(IPlace place) {
        return em.createQuery(
                "select s from Statut s where s.date = (select max(s.date) " +
                "from Statut s where s.place.nom = :nomPlace and s.place" +
                ".parking.id = :idParking)", IStatut.class)
                 .setParameter("nomPlace", place.getNom())
                 .setParameter("idParking", place.getParking().getId())
                 .getSingleResult();
    }
}
