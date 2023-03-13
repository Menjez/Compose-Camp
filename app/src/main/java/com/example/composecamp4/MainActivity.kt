package com.example.composecamp4

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composecamp4.api.SpeakerViewModel
import com.example.composecamp4.ui.theme.ComposeCamp4Theme

sealed class Screen(val route: String, val icon: ImageVector) {
    object Compose : Screen("Compose", icon = Icons.Rounded.Draw)
    object ComposeDetails : Screen("ComposeDetails", icon = Icons.Rounded.Draw)
    object Kotlin : Screen("Kotlin", icon = Icons.Rounded.Translate)
    object KotlinDetails : Screen("KotlinDetails", icon = Icons.Rounded.Translate)
    object Speakers : Screen("Speakers", icon = Icons.Rounded.Person)
    object Speaker : Screen("Speaker", icon = Icons.Rounded.Person)
}

val navList = listOf(
    Screen.Compose,
    Screen.Kotlin,
    Screen.Speakers,
)

data class Day(var day: String, var topic: String, var date: String, var color: Color)

var titleList = listOf(
    Day("Compose Camp ", "Rendering UI", "4th Feb", Color(0xFFC6E6FA)),
    Day("Compose Camp ", "States in Compose", "11th Feb", Color(0xFFFAB3B6)),
    Day("Compose Camp ", "Working with Lists", "18th Feb", Color(0xFFF8DAB4)),
    Day("Compose Camp ", "Navigation in Compose", "24th Feb", Color(0xFFEFA9E3))
)

data class Details(
    var title: String,
    var date: String,
    var speaker: String,
    var description: String,
    val github: String
)

var detailsList = listOf(
    Details(
        "Rendering UI",
        "4th Feb",
        "Mambo Bryan",
        "Jetpack Compose is a new UI toolkit for building native Android apps using a declarative programming model. It simplifies the process of creating and customizing UI components, and developers can write UI code in a more intuitive and efficient way using Kotlin. It offers features such as code reuse, easy layout design, and seamless integration with other Android architecture components, and is built on top of the Android framework. Jetpack Compose is designed to work seamlessly with existing Android app codebases, making it easy to integrate into existing projects.",
        "MamboBryan"
    ), Details(
        "States in Compose",
        "11th Feb",
        "Rachel Murabula",
        "Jetpack Compose states hold and manage data that can change over time, enabling reactive programming to automatically update UI components when a state value changes. This simplifies code, reduces bugs, and makes it easier to develop and maintain complex UI components in Android apps.",
        "Raynafs"
    ), Details(
        "Working with Lists",
        "18th Feb",
        "Charles Kagiri",
        "In Jetpack Compose, lists, lazy rows and columns, and grids are used to display collections of items in a UI. Lists are used to display a scrollable list of items, while lazy rows and columns allow for efficient loading of large collections. Grids enable the display of items in a grid format, with customization options such as item spacing and alignment. All these components are highly customizable and easy to use, enabling developers to create complex and dynamic UIs with minimal effort.",
        "Kagiri11"
    ), Details(
        "Navigation",
        "25th Feb",
        "Mambo Bryan",
        "Jetpack Compose Navigation simplifies the process of implementing navigation in Android apps, providing a declarative way to define the structure of an app's navigation with built-in support for back-stack management, deep linking, and animations. It enables developers to create efficient and scalable navigation patterns, resulting in better user experiences in their Android apps.",
        "MamboBryan"
    )
)

data class KotlinDays(var topic: String, var date: String, var color: Color)

var kotlinList = listOf(
    KotlinDays("Kotlin Functions", "4th Feb", Color(0xFFDDED31)),
    KotlinDays("Kotlin States", "11th Feb", Color(0xFFABD8B0)),
    KotlinDays("Kotlin Collections", "18th Feb", Color(0xFFB08DF6)),
    KotlinDays("Sealed Classes", "24th Feb", Color(0xFFEDBFD2))
)

data class KotlinDetails(var title: String, var desc: String)

var kotlinDetails = listOf(
    KotlinDetails(
        "Kotlin Functions and Data Classes",
        "Functions in Kotlin are reusable code blocks that perform a specific task and can be called from other parts of the code to execute the code within the function body. They have features such as default parameter values, extension functions, and function literals, which allow for modular and reusable code."
    ),
    KotlinDetails(
        "Kotlin States",
        "Functions in Kotlin are reusable code blocks that perform a specific task and can be called from other parts of the code to execute the code within the function body. They have features such as default parameter values, extension functions, and function literals, which allow for modular and reusable code."
    ),
    KotlinDetails(
        "Kotlin Collections",
        "Higher-order functions in Kotlin enable functional programming paradigms and allow for concise, modular, and reusable code. They can take other functions as parameters or return functions as results, and are commonly used for implementing common programming patterns such as mapping, filtering, and reducing collections. Higher-order functions also support anonymous functions called lambdas, which make Kotlin highly expressive and powerful for functional programming."
    ),
    KotlinDetails(
        "Kotlin Sealed Classes",
        "Kotlin's sealed classes represent a restricted hierarchy of classes, restricting the possible subclasses to a specific set of classes defined within the same file. They are commonly used in when expressions for exhaustive pattern matching and modeling state to ensure type safety. Sealed classes provide a way to create a fixed set of types that can be used as a part of data models in Kotlin code."
    ),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCamp4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        if (navList.map { it.route }.contains(currentDestination?.route)) BottomNavigation {
            navList.forEach {
                BottomNavigationItem(icon = {
                    Icon(
                        imageVector = it.icon, contentDescription = null
                    )
                },
                    label = { Text(it.route) },
                    selected = currentDestination?.hierarchy?.any { screen -> it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(it.route) {

                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        }
    }) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screen.Compose.route
        ) {
            composable(route = Screen.Speakers.route) {
                SpeakersScreen(navController)
            }
            composable(route = Screen.Compose.route) {
                ComposeScreen(navController)
            }
            composable(
                route = Screen.ComposeDetails.route.plus("?id={id}"),
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                val id = it.arguments?.getString("id")?.toInt()
                ComposeCampDetails(id ?: 0, navController)
            }
            composable(route = Screen.Kotlin.route) {
                KotlinScreen(navController)
            }
            composable(
                route = Screen.KotlinDetails.route.plus("?id={id}"),
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                val id = it.arguments?.getString("id")?.toInt()
                KotlinDetailsScreen(id ?: 0, navController)
            }
            composable(
                route = Screen.Speaker.route.plus("/{name}"),
                arguments = listOf(navArgument("name") {
                    type = NavType.StringType
                    nullable = true
                })
            ) {
                SpeakScreen(navController)
            }
            composable(
                route = Screen.Speakers.route
            ) {
                SpeakersScreen(navController = navController)
            }
//
        }
    }

}

@Composable
fun SpeakersScreen(
    navController: NavController
) {

    val myList = listOf(
        Triple("Annunziata Kinya", "AnnieKobia", "https://github.com/AnnieKobia.png"),
        Triple("Charles Kagiri", "Kagiri11", "https://github.com/kagiri11.png"),
        Triple("Jacquiline Gitau", "Jacquigee", "https://github.com/jacquigee.png"),
        Triple("Harun Wangereka", "wangerekaharun", "https://github.com/wangerekaharun.png"),
        Triple("Oliver Muthomi", "muth0mi", "https://github.com/muth0mi.png"),
        Triple("Evans Chepsiror", "chepsi", "https://github.com/chepsi.png"),
        Triple("Mambo Bryan", "mambobryan", "https://github.com/MamboBryan.png"),
        Triple("Rachel Murabula", "Raynafs", "https://github.com/Raynafs.png")
    )

    fun generateRandomList(): List<Triple<String, String, String>> {
        // val size = Random.nextInt(2, myList.size)
        return myList.shuffled().subList(0, myList.size)
    }

    var selected by remember {
        mutableStateOf(generateRandomList())
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Speakers") })
    }, floatingActionButtonPosition = FabPosition.End, floatingActionButton = {
        FloatingActionButton(onClick = { selected = generateRandomList() }) {
            Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "randomize button")
        }
    }) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            columns = GridCells.Fixed(2)
        ) {
            items(items = selected) {
                SpeakerCard(it, navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpeakerCard(selected: Triple<String, String, String>, navController: NavController) {
    Card(modifier = Modifier
        .padding(8.dp)
        .background(Color(0xFFFFFFFF))
        .height(250.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            navController.navigate(Screen.Speaker.route.plus("/${selected.second}"))
        }) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                model = ImageRequest.Builder(LocalContext.current).data(selected.third)
                    .crossfade(true).build(),
                contentDescription = "github user image",
                contentScale = ContentScale.Crop,
            )
            Text(text = selected.first, modifier = Modifier.padding(16.dp))
        }
    }

}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SpeakScreen(
    navController: NavController,
    viewModel: SpeakerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val details by viewModel._speaker.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
            }
        })
    }) {

        Column(modifier = Modifier.padding(it)) {
            if (details == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp), shape = RoundedCornerShape(25)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(details?.avatarUrl)
                                .build(),
                            contentDescription = "speaker image"
                        )
                    }

                    Text(
                        text = details?.name ?: "",
                        modifier = Modifier.padding(vertical = 16.dp),
                        fontSize = TextUnit(36f, TextUnitType.Sp)
                    )

                    Text(
                        text = "Bio", modifier = Modifier.alpha(0.5f)
                    )
                    Text(
                        text = (details?.bio ?: "").ifBlank { "Developer has no bio, but they're still cool though" },
                        modifier = Modifier.width(250.dp),
                        textAlign = TextAlign.Center
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Followers")
                            Text(text = details?.followers.toString(),fontSize = TextUnit(36f, TextUnitType.Sp))
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Following")
                            Text(text = details?.following.toString(),fontSize = TextUnit(36f, TextUnitType.Sp))
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)
@Composable
fun ComposeScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Compose") })
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color(0xFFF5F5F5))
        ) {
            items(items = titleList) { day ->
                val id = titleList.indexOf(day)
                Card(modifier = Modifier
                    .padding(
                        start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp
                    )
                    .fillMaxWidth()
                    .height(200.dp),
                    elevation = 0.dp,
                    backgroundColor = day.color,
                    shape = RoundedCornerShape(20f),
                    onClick = {
                        navController.navigate(Screen.ComposeDetails.route.plus("?id=").plus(id))
                    }) {

                    Box(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp)) {
                        Text(
                            text = (id + 1).toString(),
                            modifier = Modifier
                                .alpha(0.1f)
                                .align(Alignment.TopEnd)
                                .rotate(-32f),
                            softWrap = false,
                            fontSize = TextUnit(200f, TextUnitType.Sp),
                            fontWeight = FontWeight.Black
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = day.topic,
                                modifier = Modifier,
                                fontSize = TextUnit(30f, TextUnitType.Sp)
                            )
                            Text(
                                text = day.date,
                                modifier = Modifier.alpha(0.7f),
                            )
                        }

                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalUnitApi::class)
@Composable
fun ComposeCampDetails(id: Int, navController: NavController) {
    val item = detailsList[id]
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
            }
        })
    }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = "Topic",
                        modifier = Modifier.alpha(0.5f),
                    )
                    Text(
                        text = item.title,
                        fontSize = TextUnit(24f, TextUnitType.Sp),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Column {
                    Text(
                        text = item.date,
                        //fontSize = TextUnit(20f, TextUnitType.Sp),
                        modifier = Modifier.alpha(0.5f),
                    )
                }
            }

            Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                Text(
                    text = "Speaker",
                    //fontSize = TextUnit(20f, TextUnitType.Sp),
                    modifier = Modifier.alpha(0.5f),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.speaker,
                        // fontSize = TextUnit(30f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(
                        onClick = { navController.navigate(Screen.Speaker.route.plus("/${item.github}")) },
                    ) {
                        Text(text = "more")
                    }
                }

            }
            Text(
                text = "About",
                //fontSize = TextUnit(20f, TextUnitType.Sp),
                modifier = Modifier.alpha(0.5f),

                )
            Text(
                text = item.description,
                //fontSize = TextUnit(24f, TextUnitType.Sp)
            )

        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)
@Composable
fun KotlinScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Kotlin") })
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color(0xFFF5F5F5))
        ) {
            items(items = kotlinList) { card ->
                val id = kotlinList.indexOf(card)
                Card(modifier = Modifier
                    .padding(
                        start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp
                    )
                    .fillMaxWidth()
                    .height(200.dp),
                    elevation = 0.dp,
                    shape = RoundedCornerShape(20f),
                    backgroundColor = card.color,
                    onClick = {
                        navController.navigate(
                            Screen.KotlinDetails.route.plus("?id=").plus(id)
                        )
                    }) {

                    Box(modifier = Modifier) {

                        val day = card.date.split(" ")

                        Text(
                            text = day[0],
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .alpha(0.1f)
                                .rotate(-32f),
                            fontWeight = FontWeight.Black,
                            fontSize = TextUnit(175f, TextUnitType.Sp)
                        )

                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.BottomStart)
                                .fillMaxWidth(),
                        ) {

                            Text(
                                text = card.topic,
                                fontSize = TextUnit(24f, TextUnitType.Sp),

                                )
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun KotlinDetailsScreen(id: Int, navController: NavController) {
    val kotItem = kotlinDetails[id]
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                }
            },
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            Text(text = "Topic: ", modifier = Modifier.alpha(0.5f))
            Text(text = kotItem.title)
            Text(
                text = "Details", modifier = Modifier
                    .alpha(0.5f)
                    .padding(top = 16.dp)
            )
            Text(text = kotItem.desc, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeCamp4Theme {
        KotlinScreen(navController = NavController(LocalContext.current))
    }
}