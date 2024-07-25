package hu.ait.navigation

sealed class MainNavigation(val route: String) {
    object SplashScreen : MainNavigation("splash_screen")
    object ShoppingListScreen : MainNavigation("shopping_list_screen")
    object AddEditItemScreen : MainNavigation("add_edit_item_screen")

}

fun getRouteWithId(route: MainNavigation, id: Int): String {
    return "${route.route}/$id"
}