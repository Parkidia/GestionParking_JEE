/*
 * DAOStatut.java
 */
package com.parkidia.dao;

import com.parkidia.modeles.place.statut.IStatut;

import javax.ejb.Singleton;

/**
 * DAO permettant de gérer les places de parking dans la base de données.
 */
@Singleton
public class DAOStatut extends AbstractDAO<IStatut> {

}
