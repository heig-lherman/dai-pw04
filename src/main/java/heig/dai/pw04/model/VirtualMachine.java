package heig.dai.pw04.model;

import java.io.Serializable;

/**
 * Simple model for a virtual machine that can be serialied and store on disk.
 *
 * @author Olin Bourquin
 * @author Kevin Farine
 * @author Loïc Herman
 * @author Massimo Stefani
 */
public record VirtualMachine(
        String hostname,
        String os,
        String ip,
        Integer cpu,
        Float ram
) implements Serializable {
}
