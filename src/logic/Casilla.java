package logic;

public class Casilla {

    private Boolean fueAtacada;
    private Boolean tieneBarco;

    public Casilla(Boolean fueAtacada, Boolean tieneBarco) {
        this.fueAtacada = fueAtacada;
        this.tieneBarco = tieneBarco;
    }

    public Casilla() {
        this.fueAtacada = false;
        this.tieneBarco = false;
    }

    public Boolean getFueAtacada() {
        return fueAtacada;
    }

    public Boolean getTieneBarco() {
        return tieneBarco;
    }

    public void setFueAtacada(Boolean fueAtacada) {
        this.fueAtacada = fueAtacada;
    }

    public void setTieneBarco(Boolean tieneBarco) {
        this.tieneBarco = tieneBarco;
    }

}
