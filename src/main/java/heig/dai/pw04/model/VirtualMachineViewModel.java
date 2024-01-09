package heig.dai.pw04.model;

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

    private final UUID _id;
    private final String hostname;
    private final String os;
    private final String ip;
    private final Integer cpu;
    private final Float ram;

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

    public static VirtualMachineViewModel map(Entry<UUID, VirtualMachine> entry) {
        return map(entry.getKey(), entry.getValue());
    }
}
