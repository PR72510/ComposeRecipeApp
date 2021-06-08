package com.example.composerecipeapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun CircularIndeterminateProgressBar(
    isVisible: Boolean,
    verticalBias: Float
) {
    if (isVisible) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val progressBar = createRef()
            val topBias = createGuidelineFromTop(verticalBias)
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(topBias)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = MaterialTheme.colors.primary
            )

        }
    }
}