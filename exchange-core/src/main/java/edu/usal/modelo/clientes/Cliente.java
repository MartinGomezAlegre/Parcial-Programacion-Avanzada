package edu.usal.modelo.clientes;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String nombre;
    private String apellido;
    private String cuit;
    private DatosContacto datosContacto;
    private List<String> numerosCuenta;

    public Cliente() {
        this.numerosCuenta = new ArrayList<>();
    }

    public Cliente(String nombre, String apellido, String cuit, DatosContacto datosContacto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuit = cuit;
        this.datosContacto = datosContacto;
        this.numerosCuenta = new ArrayList<>();
    }

    public Cliente(String nombre, String apellido, String cuit, DatosContacto datosContacto, List<String> numerosCuenta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuit = cuit;
        this.datosContacto = datosContacto;
        this.numerosCuenta = numerosCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public DatosContacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(DatosContacto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public List<String> getNumerosCuenta() {
        return numerosCuenta;
    }

    public void setNumerosCuenta(List<String> numerosCuenta) {
        this.numerosCuenta = numerosCuenta;
    }

    public void agregarNumeroCuenta(String numeroCuenta) {
        this.numerosCuenta.add(numeroCuenta);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cuit='" + cuit + '\'' +
                ", datosContacto=" + datosContacto +
                ", numerosCuenta=" + numerosCuenta +
                '}';
    }
}
