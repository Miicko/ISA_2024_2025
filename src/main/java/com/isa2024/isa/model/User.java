package com.isa2024.isa.model;

import com.isa2024.isa.model.enums.UserRole;
import jakarta.persistence.*;

    @Entity
    @Table(name = "users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private long id;

        @Column(name = "username")
        private String username;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "email")
        private String email;

        @Column(name = "password")
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        private UserRole role;

        public User(){}
        public User(String email, String firstName, long id, String lastName, String password, UserRole role, String username) {
            this.email = email;
            this.firstName = firstName;
            this.id = id;
            this.lastName = lastName;
            this.password = password;
            this.role = role;
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String emailId) {
            this.email = emailId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public UserRole getRole() {
            return role;
        }

        public void setRole(UserRole role) {
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
