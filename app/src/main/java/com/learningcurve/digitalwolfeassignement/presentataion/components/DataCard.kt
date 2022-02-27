package com.learningcurve.digitalwolfeassignement.presentataion.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.learningcurve.digitalwolfeassignement.domain.model.MyModel
import java.util.*
import com.learningcurve.digitalwolfeassignement.R
import com.learningcurve.digitalwolfeassignement.presentataion.navigation.Screen
import com.learningcurve.digitalwolfeassignement.util.TAG

@Composable
fun DataCard(
    myModel: MyModel,
    onNavigateToEditScreen: (String) -> Unit
) {

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 8.dp,
    ) {
        Column() {
            Text(
                text = myModel.title.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 4.dp, end = 4.dp)
                    .fillMaxWidth()
            )

            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (line, avatar) = createRefs()
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = MaterialTheme.colors.secondary)
                        .constrainAs(line) {
                            centerVerticallyTo(parent)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                )
                OutlinedAvatar(
                    modifier = Modifier
                        .constrainAs(avatar) {
                            centerVerticallyTo(parent)
                            centerHorizontallyTo(parent)
                        },
                    res = R.drawable.ic_edit_note,
                    size = 48.dp,
                    filledColor = MaterialTheme.colors.primaryVariant,
                    onClick = {
                        val route = if(!myModel.title.isNullOrEmpty())
                        {
                            Screen.EDIT_SCREEN_ROUTE.withArgs(myModel.title)
                        } else {
                            Screen.EDIT_SCREEN_ROUTE.withArgs("emptyTitle")
                        }
                        onNavigateToEditScreen(route)
                    }
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Type: ${myModel.typeString}",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp, start = 4.dp, end = 4.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = "Value: ${myModel.valueString}",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 16.dp, start = 4.dp, end = 4.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}