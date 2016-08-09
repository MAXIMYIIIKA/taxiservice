package by.nichipor.taxiservice.entity;

/**
 * Created by Max Nichipor on 09.08.2016.
 */
public enum Roles {
    admin("ROLE_ADMIN"), user("ROLE_USER");

    private String role;

    Roles(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
