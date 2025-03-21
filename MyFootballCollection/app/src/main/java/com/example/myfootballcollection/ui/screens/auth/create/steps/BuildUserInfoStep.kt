package com.example.myfootballcollection.ui.screens.auth.create.steps

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfootballcollection.R
import com.example.myfootballcollection.ui.composeUtils.BuildDefaultOutlinedTextField
import com.example.myfootballcollection.ui.theme.White
import com.example.myfootballcollection.utils.IMAGE_DIAMETER
import com.example.myfootballcollection.utils.OFFSET
import com.example.myfootballcollection.utils.modifyOrientation

@Composable
fun BuildUserInfoStep(
    firstName: String,
    lastName: String,
    username: String,
    currentImage: Bitmap,
    onImageChange: (Bitmap) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        val context = LocalContext.current
        val photoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                val stream = context.contentResolver.openInputStream(uri)
                val bytes = stream!!.readBytes()
                val bitmap = modifyOrientation(
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size),
                    stream
                )
                onImageChange(bitmap)
                stream.close()
            }
        }
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                ) {
                    append("Complete your account\n")
                }
                withStyle(style = SpanStyle(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                ) {
                    append("Your account is almost finished, just a few more steps before you can start")
                }
            }
        )
        Row(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    bottom = 20.dp,
                    start = 10.dp,
                    end = 10.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(IntrinsicSize.Max),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    bitmap = currentImage.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(IMAGE_DIAMETER)
                        .clip(CircleShape)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier
                        .size(25.dp)
                        .offset(x = OFFSET)
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    ) {
                        append("Profile Picture\n")
                    }
                    withStyle(style = SpanStyle(
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    ) {
                        append("Every collector is part of the family")
                    }
                }
            )
        }
        BuildDefaultOutlinedTextField(
            placeholder = R.string.first_name,
            value = firstName,
            onChange = onFirstNameChange
        )
        BuildDefaultOutlinedTextField(
            placeholder = R.string.last_name,
            value = lastName,
            onChange = onLastNameChange
        )
        BuildDefaultOutlinedTextField(
            placeholder = R.string.username,
            value = username,
            onChange = onUsernameChange
        )
    }
}