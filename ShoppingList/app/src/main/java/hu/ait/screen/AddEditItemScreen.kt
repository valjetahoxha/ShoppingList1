package hu.ait.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hu.ait.data.ShoppingItem
import hu.ait.shoppinglist.ShoppingViewModel

@Composable
fun AddEditItemScreen(
    viewModel: ShoppingViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    val navController = androidx.navigation.compose.rememberNavController()
    val itemId = navController.previousBackStackEntry?.arguments?.getInt("itemId")

    val item by viewModel.editableItem.collectAsState()

    // Form fields state
    var name by rememberSaveable { mutableStateOf(item?.name ?: "") }
    var description by rememberSaveable { mutableStateOf(item?.description ?: "") }
    var price by rememberSaveable { mutableStateOf(item?.price?.toString() ?: "") }
    var category by rememberSaveable { mutableStateOf(item?.category ?: "") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Effect to load the item when itemId is not null
    LaunchedEffect(key1 = itemId) {
        itemId?.let {
            viewModel.loadItemById(it)
        }
    }

    // ... existing UI code ...

    // Button to save or update an item
    Button(
        onClick = {
            if (name.isBlank() || description.isBlank() || price.isBlank() || category.isBlank()) {
                errorMessage = "Please fill in all fields."
                showError = true
            } else {
                try {
                    val priceValue = price.toDouble()
                    if (itemId == null) {
                        // Adding a new item
                        val newItem = ShoppingItem(
                            name = name,
                            description = description,
                            price = priceValue.toFloat(),
                            category = category,
                            bought = false
                        )
                        viewModel.addItem(newItem)
                    } else {
                        // Updating an existing item
                        val updateItem = item!!.copy(
                            name = name,
                            description = description,
                            price = priceValue.toFloat(),
                            category = category
                        )
                        viewModel.updateItem(updateItem)
                    }
                    navController.popBackStack()
                } catch (e: NumberFormatException) {
                    errorMessage = "Please enter a valid price."
                    showError = true
                }
            }
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Save")
    }

    if (showError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
