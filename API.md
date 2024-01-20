# Practical Work 4 - API server for managing VMs

## Endpoints

### Get all the VMs

- `GET /virtual-machines`

List all the creates VMs.

#### Request

This route does not require any parameters.

#### Response 

The response body contains a JSON array with the following properties:

- `hostname` - the hostname of the VM
- `os` - the operating system of the VM
- `ip` - the IP address of the VM
- `cpu` - the number of CPU cores of the VM
- `ram` - the amount of RAM of the VM (in GB)
- `_id` - the UUID of the VM

#### Status codes

- `200` (OK) - The VMs have been successfully retrieved

#### Example

```bash
curl -B http://localhost:8080/virtual-machines
```

### Get one the VM using its id

- `GET /virtual-machines/{vmId}`

Get one VM by its Id.

#### Request

The request path must contain the Id of the VM.

#### Response

The response body contains a JSON object with the following properties:

- `hostname` - the hostname of the VM
- `os` - the operating system of the VM
- `ip` - the IP address of the VM
- `cpu` - the number of CPU cores of the VM
- `ram` - the amount of RAM of the VM (in GB)
- `_id` - the UUID of the VM

#### Status codes

- `200` (OK) - The VM has been successfully retrieved
- `400` (Bad Request) - The vmId is not a valid UUID
- `404` (Not Found) - a VM with that uuid doesn't exist

#### Example

```bash
curl -B http://localhost:8080/virtual-machines/e722f4cc-4c37-4d93-9c97-ff0d4fa82fbe
```

### Create a new VM

- `POST /virtual-machines`

Create one VM.

#### Request

The request body must contain a JSON object with the following properties:

- `hostname` - the hostname of the VM
- `os` - the operating system of the VM
- `ip` - the IP address of the VM
- `cpu` - the number of CPU cores of the VM
- `ram` - the amount of RAM of the VM (in GB)


#### Response

The response body contains a JSON object with the following properties:

- `hostname` - the hostname of the VM
- `os` - the operating system of the VM
- `ip` - the IP address of the VM
- `cpu` - the number of CPU cores of the VM
- `ram` - the amount of RAM of the VM (in GB)
- `_id` - the UUID of the VM

#### Status codes

- `200` (OK) - The VM has been successfully created
- `400` (Bad Request) - The request body is not valid

#### Example

```bash
curl -i -X POST -v "Content-Type: application/json" -d '{"hostname":"example.test","os":"Windows","ip":"1.1.1.1","cpu":"3","ram":"32"}' http://localhost:8080/virtual-machines
```

### Update an existing VM using its id

- `PUT /virtual-machines/{vmId}`

Update one VM by its Id.

#### Request

The request path must contain the Id of the VM.

The request body must contain a JSON object with the following properties:

- `hostname` - the hostname of the VM
- `os` - the operating system of the VM
- `ip` - the IP address of the VM
- `cpu` - the number of CPU cores of the VM
- `ram` - the amount of RAM of the VM (in GB)


#### Response

The response body is empty

#### Status codes

- `200` (OK) - The VM has been successfully updated
- `400` (Bad Request) - The vmId is not a valid UUID
- `400` (Bad Request) - The request body is not valid
- `404` (Not Found) - a VM with that uuid doesn't exist

#### Example

```bash
curl -i -X PUT -v "Content-Type: application/json" -d '{"hostname":"example.test","os":"Windows","ip":"1.1.1.1","cpu":"3","ram":"32"}' http://localhost:8080/virtual-machines/e722f4cc-4c37-4d93-9c97-ff0d4fa82fbe
```

### Delete an existing VM using its id

- `DELETE /virtual-machines/{vmId}`

Delete one VM by its Id.

#### Request

The request path must contain the Id of the VM.

#### Response

The response body is empty.

#### Status codes

- `204` (No Content) - The virtual machine was deleted
- `400` (Bad Request) - The vmId is not a valid UUID
- `404` (Not Found) - The virtual machine doesn't exist

#### Example

```bash
curl -i -X DELETE -v "Content-Type: application/json"http://localhost:8080/virtual-machines/e722f4cc-4c37-4d93-9c97-ff0d4fa82fbe
```

