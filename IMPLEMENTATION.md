The "Share Ride" project's implementation involves various components that work together to provide a ride-sharing application. Here's a detailed explanation of the key components and their functionalities:

### Application Structure
1. **Main Application (Application.java):** Entry point for the application. It initializes the main JavaFX scene and displays the login screen.

2. **Login Management (LoginManager.java):** Manages the login process, including displaying the login, registration, and main application screens based on the user's authentication status.

3. **User Session (Session.java):** A singleton class that manages the current user's session details, including user information.

4. **User Model (User.java):** Represents a user in the system with attributes like username, password, contact details, etc.

### Core Functionalities
1. **Ride Management (Ride.java):** Represents a ride, including details like origin, destination, date, time, available seats, and the ride creator.

2. **Database Integration (DatabaseHelper.java):** Handles interactions with the SQLite database, including operations like creating users, rides, and managing ride participants.

### User Interface Controllers
1. **Login (LoginController.java):** Controls the login screen, handling user authentication and navigation to the main view or registration screen.

2. **Main View (MainViewController.java):** Manages the main application view post-login, allowing navigation to different functionalities like creating a ride or viewing available rides.

3. **Profile View (ProfileViewController.java):** Manages the user's profile view, allowing users to view and edit their profile information.

4. **Ride Creation (RideCreationFormController.java):** Handles the creation of new rides by taking inputs like origin, destination, date, and time.

5. **Available Rides View (AvailableRidesViewController.java):** Displays a list of available rides and allows users to join or leave rides.

### Server-Side Handlers
1. **Available Rides (AvailableRidesHandler.java):** Handles server requests to fetch available rides.

2. **Create Ride (CreateRideHandler.java):** Processes requests to create a new ride.

3. **User Registration (RegisterHandler.java):** Handles new user registrations.

4. **User Login (LoginHandler.java):** Manages user login requests.

5. **Edit User (EditUserHandler.java):** Processes requests to edit user details.

6. **Join/Leave Ride (JoinRideHandler.java and LeaveRideHandler.java):** Handles requests for joining or leaving a ride.

The application is built using JavaFX for the frontend and Java for the backend logic. It uses HTTP requests to communicate between the frontend and backend, and the data is stored in a SQLite database.
