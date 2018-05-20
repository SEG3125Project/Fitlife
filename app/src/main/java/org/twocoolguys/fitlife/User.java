package org.twocoolguys.fitlife;

/**
 * Created by Kevin on 27/11/17.
 *
 * Container class for Users. Contains information regarding a given user.
 * Also helps for passing information to an from the database
 *
 * Security is very clearly not something we're worrying about.
 */


public class User {
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password; //could hash this but not useful for

    public User(String name, String firstName, String lastName, String email, String password){
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
