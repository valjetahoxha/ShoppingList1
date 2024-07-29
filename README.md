During the development I practiced the following techniques in Android:

Compose

Navigation

LazyColumn

Dialog

Persistence data storage (Room) The application starts with an opening screen that displays an image and jumps to the Shopping List after 3 seconds. An Item has the following attributes: category,name,description, estimated price, status: true/false weather it has been bought yet or not.

The Shopping List displays the items in a LazyColumn. The Shopping List has a "New Item" menu as a FloatingActionButton, which opens a New Item Dialog, where the user can pick up new items that appear on the Shopping List.

Extra architecture I used is:

SplashScreen composable
Navigation with NavHost
ShoppingViewModel for ShoppingListScreen
Room database

![image](https://github.com/user-attachments/assets/ccf260bd-898a-4b25-bddf-ca97d93a1b56)
