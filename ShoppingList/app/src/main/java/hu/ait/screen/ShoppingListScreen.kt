package hu.ait.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.ait.data.ShoppingItem
import hu.ait.shoppinglist.ShoppingViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit = {},
    onAddItem: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Todo app")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(Icons.Filled.Add, null)
                    }

                    IconButton(onClick = {
                        viewModel.deleteAllItems()
                    }) {
                        Icon(Icons.Filled.Delete, null)
                    }
                    IconButton(onClick = {
                        //
                    }) {
                        Icon(Icons.Filled.Info, null)
                    }
                }
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                if (showDialog) {
                    AddItemDialog(
                        onDismissRequest = { showDialog = false },
                        onSubmit = { title, description, price, category ->
                            try {
                                val priceValue = price.toFloat()
                                val newItem = ShoppingItem(
                                    name = title,
                                    description = description,
                                    price = priceValue,
                                    category = category,
                                    bought = false
                                )
                                viewModel.addItem(newItem)
                                showDialog = false
                            } catch (e: NumberFormatException) {
                                errorMessage = "Please enter a valid price."
                                showError = true
                            }
                        }
                    )
                }

                if (showError) {
                    Text(text = errorMessage, color = Color.Red)
                }
            }

            ShoppingListContent(
                Modifier.padding(it), viewModel)

        }
    )
}

@Composable
fun ShoppingListContent(
    modifier: Modifier,
    shoppingViewModel: ShoppingViewModel
) {
    val shoppingItemList by shoppingViewModel.allItems.collectAsState(emptyList())

    //var todoToEdit: TodoItem? by rememberSaveable {
    //    mutableStateOf(null)
    //}
    var showEditTodoDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        // show TodoItems from the ViewModel in a LayzColumn
        if (shoppingItemList.isEmpty()) {
            Text(text = "No items")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(shoppingItemList) {
                    ShoppingCard(it,
                        onEditItem = {},
                        onRemoveItem = {shoppingViewModel.deleteItem(it)})
                }
            }

            if (showEditTodoDialog) {
                //AddNewTodoDialog(todoViewModel = todoViewModel,
                //    todoToEdit = todoToEdit,
                //) {
                //    showEditTodoDialog = false
                //}
            }
        }
    }
}

@Composable
fun ShoppingCard(
    shoppingItem: ShoppingItem,
    onTodoCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {},
    onEditItem: (ShoppingItem) -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize()
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                /*Image(
                    painter = painterResource(id = todoItem.priority.getIcon()),
                    contentDescription = "Priority",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )*/


                Text("""${shoppingItem.name}
                    |${shoppingItem.price}""".trimMargin(), modifier = Modifier.fillMaxWidth(0.2f))
                Spacer(modifier = Modifier.fillMaxSize(0.55f))

                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        onEditItem(shoppingItem)
                    },
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onRemoveItem()
                    },
                    tint = Color.Red
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else
                            Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            "Less"
                        } else {
                            "More"
                        }
                    )
                }
            }

            if (expanded) {
                Text(text = shoppingItem.description)
            }
        }
    }
}

@Composable
fun AddItemDialog(
    onDismissRequest: () -> Unit,
    onSubmit: (String, String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text("Add New Item") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
//                TextField(
//                    value = category,
//                    onValueChange = { category = it },
//                    label = { Text("Category") }
//                )
                SpinnerSample(
                    listOf("Food", "Electricity", "Sport"),
                    preselected = "Food",
                    onSelectionChanged = {  category = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(title, description, price, category)
                    title = ""
                    description = ""
                    price = ""
                    category = ""
                }
            ) { Text("Add") }
        },
        dismissButton = {
            Button(onClick = { onDismissRequest() }) { Text("Cancel") }
        }
    )
}

@Composable
fun OutlinedCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
    ) {
        content()
    }
}

@Composable
fun SpinnerSample(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (myData: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value
    OutlinedCard(
        modifier = modifier.clickable {
            expanded = !expanded
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = selected,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(
                Icons.Outlined.ArrowDropDown, null, modifier =
            Modifier.padding(8.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                            )
                        },
                    )
                }
            }
        }

    }
}