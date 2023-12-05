/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contarpatrones;

/**
 *
 * @author alanm
 */
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class conteoParalelo implements Serializable {
    private static final long serialVersionUID = 1L;
     public AtomicInteger count = new AtomicInteger(0); 
     public long END =0;
    public double tiempo;

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public double getTiempo() {
        return tiempo;
    }
}