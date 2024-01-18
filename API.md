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

### Get all the VMs

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

