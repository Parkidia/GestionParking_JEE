/*
 * AbstractDAO.java
 */
package com.parkidia.dao;

import com.parkidia.modeles.IEntity;
import com.sun.istack.internal.Nullable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public abstract void creer(E entite);

    /**
     * Met à jour l'entité passée en argument dans la base de données.
     * @param entite l'entité à mettre à jour.
     * @return l'entité mise à jour.
     */
    public abstract E maj(E entite);

    /**
     * Supprime l'entité passée en argument de la base de données.
     * @param entite l'entité à supprimer.
     */
    public abstract void supprimer(E entite);

    /**
     * Recherche l'objet passée en argument dans la base de données.
     * @param entite l'entité à rechercher.
     * @return l'objet trouvée ou <code>null</code> si aucuns objets n'a été
     * trouvé.
     */
    @Nullable
    public abstract E rechercher(E entite);

    /**
     * Recherche tous les objets de ce type dans la base de données.
     * @return la liste des objets trouvés.
     */
    public abstract List<E> rechercherTous();
}
