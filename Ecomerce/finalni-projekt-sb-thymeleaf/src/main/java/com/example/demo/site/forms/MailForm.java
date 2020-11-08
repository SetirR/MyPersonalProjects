package com.example.demo.site.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MailForm
{
    @NotBlank(message = "E-mailová adresa je povinná, nesmí být prázdná.")
    @Size(min=2, max=100, message = "Délka e-mailové adresy musí být mezi {min} a {max} znaky.")
    @Email(message = "Neplatná e-mailová adresa")
    private String email;

    @NotBlank(message = "Předmět zprávy je povinný a nesmí být prázdný.")
    @Size(min=2, max=100, message = "Délka předmětu musí být mezi {min} a {max} znaky.")
    private String subject;

    @NotBlank(message = "Zpráva je povinná a nesmí být prázdná.")
    @Size(min=2, max=1000, message = "Délka zprávy musí být mezi {min} a {max} znaky.")
    private String message;

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
