package heig.dai.pw04.api;

import heig.dai.pw04.model.VirtualMachine;
import heig.dai.pw04.model.VirtualMachineViewModel;
import heig.dai.pw04.util.FileBackedPersistentHashMap;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.JavalinValidation;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

/**
 * Controller that handles the virtual machine CRUD endpoints.
 * It can be registered to a Javalin instance using the {@link #registerController()} method.
 *
 * @author Olin Bourquin
 * @author Kevin Farine
 * @author Loïc Herman
 * @author Massimo Stefani
 */
public class VirtualMachineController implements EndpointGroup {

    private final static Path STORE_PATH = Path.of("virtual-machines.dat");

    // We use a persistent map to store the virtual machines in a file
    private final Map<UUID, VirtualMachine> store;

    private VirtualMachineController() {
        this.store = new FileBackedPersistentHashMap<>(STORE_PATH);
    }

    /**
     * GET /api/virtual-machines
     * <p>
     * Returns a list of all virtual machines
     * <p>
     * Path params: none
     * <p>
     * Return codes:
     * - 200: OK with the list of virtual machines
     */
    void getVirtualMachines(Context ctx) {
        ctx.json(
                store.entrySet().stream()
                        .map(VirtualMachineViewModel::map)
                        .toList()
        );
    }

    /**
     * GET /api/virtual-machines/{vmId}
     * <p>
     * Returns one virtual machine using its id
     * <p>
     * Path params:
     * - vmId: the id of the virtual machine to get
     * <p>
     * Return codes:
     * - 200: OK with the virtual machine
     * - 400: Bad Request if the vmId is not a valid UUID
     * - 404: Not Found if the virtual machine doesn't exist
     */
    void getVirtualMachine(Context ctx) {
        var vmId = ctx.pathParamAsClass("vmId", UUID.class).get();
        if (!store.containsKey(vmId)) {
            throw new NotFoundResponse("Virtual machine not found");
        }

        ctx.json(VirtualMachineViewModel.map(vmId, store.get(vmId)));
    }

    /**
     * POST /api/virtual-machines
     * <p>
     * Create a new virtual machine
     * <p>
     * Path params: none
     * <p>
     * Request body:
     * - the virtual machine object to create
     * <p>
     * Return codes:
     * - 200: OK with the virtual machine
     * - 400: Bad Request if the request body is invalid
     */
    void createVirtualMachine(Context ctx) {
        var vm = ctx.bodyValidator(VirtualMachine.class).get();
        var id = UUID.randomUUID();

        store.put(id, vm);

        ctx.status(HttpStatus.CREATED).json(VirtualMachineViewModel.map(id, vm));
    }

    /**
     * PUT /api/virtual-machines/{vmId}
     * <p>
     * Updates one virtual machine using its id
     * <p>
     * Path params:
     * - vmId: the id of the virtual machine to get
     * <p>
     * Request body:
     * - the virtual machine object to update
     * <p>
     * Return codes:
     * - 200: OK with the virtual machine
     * - 400: Bad Request if the vmId is not a valid UUID
     * - 400: Bad Request if the request body is invalid
     * - 404: Not Found if the virtual machine doesn't exist
     */
    void updateVirtualMachine(Context ctx) {
        var vmId = ctx.pathParamAsClass("vmId", UUID.class).get();
        if (!store.containsKey(vmId)) {
            throw new NotFoundResponse("Virtual machine not found");
        }

        var vm = ctx.bodyValidator(VirtualMachine.class).get();
        store.put(vmId, vm);

        ctx.status(HttpStatus.NO_CONTENT);
    }

    /**
     * DELETE /api/virtual-machines/{vmId}
     * <p>
     * Deletes one virtual machine using its id
     * <p>
     * Path params:
     * - vmId: the id of the virtual machine to get
     * <p>
     * Return codes:
     * - 204: No Content if the virtual machine was deleted
     * - 400: Bad Request if the vmId is not a valid UUID
     * - 404: Not Found if the virtual machine doesn't exist
     */
    void deleteVirtualMachine(Context ctx) {
        var vmId = ctx.pathParamAsClass("vmId", UUID.class).get();
        if (!store.containsKey(vmId)) {
            throw new NotFoundResponse("Virtual machine not found");
        }

        store.remove(vmId);

        ctx.status(HttpStatus.NO_CONTENT);
    }

    @Override
    public void addEndpoints() {
        // register UUID converter for path parameters
        JavalinValidation.register(UUID.class, UUID::fromString);

        // register endpoints
        get(this::getVirtualMachines);
        post(this::createVirtualMachine);
        path("{vmId}", () -> {
            get(this::getVirtualMachine);
            put(this::updateVirtualMachine);
            delete(this::deleteVirtualMachine);
        });
    }

    /**
     * Static method to help with registering the controller
     *
     * @return the controller as an EndpointGroup to be given to Javalin
     */
    public static EndpointGroup registerController() {
        return new VirtualMachineController();
    }
}
