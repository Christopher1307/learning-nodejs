package util;

import javafx.beans.property.SimpleDoubleProperty;

public class Cuota {

    private int numero;
    private int anyo;
    private int mes;
    private double capitalInicial;
    private double intereses;
    private double capitalAmortizado;
    private final SimpleDoubleProperty cuota = new SimpleDoubleProperty();
    private double capitalPendiente;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getCapitalInicial() {
        return capitalInicial;
    }

    public void setCapitalInicial(double capitalInicial) {
        this.capitalInicial = capitalInicial;
    }

    public double getIntereses() {
        return intereses;
    }

    public void setIntereses(double intereses) {
        this.intereses = intereses;
    }

    public double getCapitalAmortizado() {
        return capitalAmortizado;
    }

    public void setCapitalAmortizado(double capitalAmortizado) {
        this.capitalAmortizado = capitalAmortizado;
    }

    public double getCuota() {
        return cuota.get();
    }

    public SimpleDoubleProperty cuotaProperty() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota.set(cuota);
    }

    public double getCapitalPendiente() {
        return capitalPendiente;
    }

    public void setCapitalPendiente(double capitalPendiente) {
        this.capitalPendiente = capitalPendiente;
    }
}