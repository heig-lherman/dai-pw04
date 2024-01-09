package heig.dai.pw04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map.Entry;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * View model to present virtual machines to a client using the API.
 *
 * @author Olin Bourquin
 * @author Kevin Farine
 * @author Loïc Herman
 * @author Massimo Stefani
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VirtualMachineViewModel {

    private final @JsonProperty("_id") UUID id;
    private final String hostname;
    private final String os;
    private final String ip;
    private final Integer cpu;
    private final Float ram;

    /**
     * Map a virtual machine to a view model
     *
     * @param id the virtual machine id
     * @param vm the virtual machine
     * @return the view model
     */
    public static VirtualMachineViewModel map(UUID id, VirtualMachine vm) {
        return new VirtualMachineViewModel(
                id,
                vm.hostname(),
                vm.os(),
                vm.ip(),
                vm.cpu(),
                vm.ram()
        );
    }

    /**
     * Map a virtual machine entry to a view model
     *
     * @param entry the virtual machine entry
     * @return the view model
     */
    public static VirtualMachineViewModel map(Entry<UUID, VirtualMachine> entry) {
        return map(entry.getKey(), entry.getValue());
    }
}
