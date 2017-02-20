/*
 * AbstractDAO.java
 */
package com.parkidia.dao;

import com.parkidia.modeles.IEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Représente un DAO permettant de gérer les entrées/sorties des entité dans la
 * base de données.
 * @param <E> le type de l'entité que le DAO gère.
 */
public abstract class AbstractDAO<E extends IEntity> {

    /** Objet permettant d'accéder à la persistance automatique des objets. */
    @PersistenceContext(unitName = "Parking")
    protected EntityManager em;

    /**
     * Persiste l'entité passée en argument dans la base de données.<br>
     * Si elle existe déjà, alors la met à jour.
     * @param entite l'entité à persister.
     */
    public void creer(E entite) {
        em.persist(entite);
    }

    /**
     * Met à jour l'entité passée en argument dans la base de données.
     * @param entite l'entité à mettre à jour.
     * @return l'entité mise à jour.
     */
    public E maj(E entite) {
        return em.merge(entite);
    }

    /**
     * Supprime l'entité passée en argument de la base de données.
     * @param entite l'entité à supprimer.
     */
    public void supprimer(E entite) {
        em.remove(entite);
    }
}
