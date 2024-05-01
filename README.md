The project includes creating a directory myPermissions that manages permissions.

It includes a sample app with buttons to request camera, contact, and location permissions.
The edge cases I handled:
1. Permission request - requests permissions from the user.
2. Checking the permission status - checks if permission has already been granted before asking for it again. This will prevent unnecessary permission requests.
3. Handling the authorization response - handling the response after the user grants or rejects authorization. The library distinguishes between different permissions. and performs appropriate actions.
4. Permission denied or revoked - the library implements return mechanisms or informs the application to handle these cases. For example, displaying a message to the user or providing alternative functionality.
5. Handling of runtime permissions - the library handles permissions at runtime. It requests permissions at runtime when necessary and responds to user actions (granting or denying permissions).
6. Unit tests - the project includes unit tests.
