package edu.usal.modelo.clientes;

public class DatosContacto {

    private String telefonoParticular;
    private String celular;
    private String email;
    private String domicilio;

    public DatosContacto() {
    }

    public DatosContacto(String telefonoParticular, String celular, String email, String domicilio) {
        this.telefonoParticular = telefonoParticular;
        this.celular = celular;
        this.email = email;
        this.domicilio = domicilio;
    }

    public String getTelefonoParticular() {
        return telefonoParticular;
    }

    public void setTelefonoParticular(String telefonoParticular) {
        this.telefonoParticular = telefonoParticular;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "DatosContacto{" +
                "telefonoParticular='" + telefonoParticular + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", domicilio='" + domicilio + '\'' +
                '}';
    }
}
