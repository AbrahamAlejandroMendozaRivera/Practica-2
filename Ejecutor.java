import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.IOException;

public class Ejecutor extends TareaPeriodica {

    private String comando;

    public Ejecutor(String comando, int periodo) {
        super(periodo);
        this.comando = comando;
    }

    public String leerComando() {
        return comando;
    }

    @Override
    public boolean necesitaEjecucion() {
        if (!activa) return false;

        Calendar proximaEjecucion = new GregorianCalendar();
        proximaEjecucion.setTime(ultimaEjecucion);
        proximaEjecucion.add(Calendar.SECOND, periodo);
        Calendar ahora = new GregorianCalendar();

        return (proximaEjecucion.before(ahora));
    }

    @Override
    public int ejecutarTarea() {
        try {
            Runtime.getRuntime().exec(comando);
            actualizar(); // Actualizar la última ejecución
            return 0;
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        return -1;
    }

    @Override
    public void run() {
        if (necesitaEjecucion()) {
            ejecutarTarea();
        }
    }

    public static void main(String[] args) {
        Ejecutor e = new Ejecutor("calc", 1); // Cambiar comillas para Java
        new Thread(e).start(); // Iniciar hilo de Ejecutor
        new Thread(new Reloj()).start(); // Iniciar hilo de Reloj
    }
}
