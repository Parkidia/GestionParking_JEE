/*
 * AbstractEntity.java
 */
package com.parkidia.modeles;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Représente une entité persistable dans la base de données.
 */
@MappedSuperclass
public abstract class AbstractEntity implements IEntity {

    /** La version de l'entité. */
    @Version
    protected int version;

    /** Créé une nouvelle entité. */
    public AbstractEntity() {
        version = 1;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }
}
