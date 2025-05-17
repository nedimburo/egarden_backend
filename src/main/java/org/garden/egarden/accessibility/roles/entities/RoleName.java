package org.garden.egarden.accessibility.roles.entities;

public enum RoleName {
    CLIENT,
    ADMIN;

    public boolean client() {return this.equals(CLIENT);}
    public boolean admin() {return this.equals(ADMIN);}
}
