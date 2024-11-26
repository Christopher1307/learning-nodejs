package util;

public class Cuota {

    private int numero;
    private int anyo;
    private int mes;
    private double capitalInicial;
    private double intereses;
    private double capitalAmortizado;
    private double cuota;
    private double capitalPendiente;

    // Default constructor
    public Cuota() {}

    // Constructor to initialize with JSON data
    public Cuota(int numero, int anyo, int mes, double capitalInicial, double intereses,
                 double capitalAmortizado, double cuota, double capitalPendiente) {
        this.numero = numero;
        this.anyo = anyo;
        this.mes = mes;
        this.capitalInicial = capitalInicial;
        this.intereses = intereses;
        this.capitalAmortizado = capitalAmortizado;
        this.cuota = cuota;
        this.capitalPendiente = capitalPendiente;
    }

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
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    public double getCapitalPendiente() {
        return capitalPendiente;
    }

    public void setCapitalPendiente(double capitalPendiente) {
        this.capitalPendiente = capitalPendiente;
    }
}