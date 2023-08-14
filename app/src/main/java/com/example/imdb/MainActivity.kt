package com.example.imdb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.example.imdb.ui.theme.IMDBTheme
import org.json.JSONObject


class MainActivity : ComponentActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var movie by remember {
                mutableStateOf("")
            }
            var movieResponse by remember {
                mutableStateOf<JSONObject?>(null)
            }

            IMDBTheme {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "IMDB",
                        fontSize = 20.sp
                    )

                    OutlinedTextField(
                        value = movie
                        , onValueChange = { movie = it },
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    Button(onClick = { /*TODO*/
                        movieViewModel.fetchMovie(movie)

                        movieResponse = movieViewModel.movieLiveData.value


                    }) {
                        Text("Search")

                    }

                    Divider()
                    if(movieViewModel.isDataFetched()) {
                        movieResponse?.let { response ->
                            ViewMovieResult(response)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ViewMovieResult(movieDetails: JSONObject) {
    println(movieDetails)
    val title = movieDetails.optString("Title", "")
    val year = movieDetails.optString("Year", "")
    val genre = movieDetails.optString("Genre", "")
    val director = movieDetails.optString("Director", "")
    val rating = movieDetails.optString("imdbRating","")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Title: $title",
                style = MaterialTheme.typography.h6
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Year: $year",
                style = MaterialTheme.typography.subtitle1
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Genre: $genre",
                style = MaterialTheme.typography.body2
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Director: $director",
                style = MaterialTheme.typography.body2
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Rating: $rating/10",
                style = MaterialTheme.typography.body2
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IMDBTheme {

    }
}