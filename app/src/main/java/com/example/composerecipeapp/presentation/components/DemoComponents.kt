package com.example.composerecipeapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun MyBottomBar(
) {
    BottomNavigation(elevation = 12.dp) {
        BottomNavigationItem(selected = false, onClick = { }, icon = {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = null
            )
        })

        BottomNavigationItem(selected = true, onClick = { }, icon = {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null
            )
        })

        BottomNavigationItem(selected = false, onClick = { }, icon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        })
    }
}

@Composable
fun MyDrawer() {
    Column {
        Text(text = "Item 1")
        Text(text = "Item 2")
        Text(text = "Item 3")
        Text(text = "Item 4")
        Text(text = "Item 5")
    }
}

@Composable
fun SnackbarDemo(
    isShowing: Boolean,
    onHideSnackbar: () -> Unit
) {
    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()
            Snackbar(
                modifier = Modifier.constrainAs(snackbar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                action = {
                    Text(
                        text = "Hide",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.clickable { onHideSnackbar() }
                    )
                },
            ) {
                Text(text = "This is a Snackbar")
            }
        }
    }
}

@Composable
fun DecoupledSnackbar(
    snackbarHostState: SnackbarHostState
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(onClick = { it.dismiss() }) {
                            Text(text = it.actionLabel ?: "null")
                        }
                    }
                ) {
                    Text(text = it.message)
                }
            }
        )
    }
}