package heig.dai.pw04.command;

import heig.dai.pw04.api.VirtualMachineController;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

/**
 * Command to start the API server
 *
 * @author Olin Bourquin
 * @author Kevin Farine
 * @author Loïc Herman
 * @author Massimo Stefani
 */
@Slf4j
@Command(
        name = "server",
        description = "Start a node unit to send metrics",
        mixinStandardHelpOptions = true
)
public class ServerCommand implements Runnable {

    @Option(
            names = {"-p", "--port"},
            description = "server port",
            defaultValue = "8080"
    )
    private int port;

    @Override
    public void run() {
        Javalin.create().routes(() -> {
            get("/", ctx -> ctx.redirect("/virtual-machines"));
            path("virtual-machines", VirtualMachineController.registerController());
        }).start(port);
        log.info("Server started on port {}", port);
    }
}
