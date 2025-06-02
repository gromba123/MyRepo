package com.example.myfootballcollection.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import androidx.compose.ui.unit.dp
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

val IMAGE_DIAMETER = 150.dp
val OFFSET = IMAGE_DIAMETER / 2

fun extractImage(picture: String) = stringToBitmap(picture)

/**
 * Decodes the string and converts it into a Bitmap
 */
private fun stringToBitmap(string: String): Bitmap {
    val imageBytes = Base64.decode(string, 0)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

/**
 * Converts a Bitmap into a string codified in Base64
 */
fun compressBitmap(bmp: Bitmap): String {
    val stream = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    val b = stream.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

/**
 * Used to modify the orientation of the image selected as profile picture
 */
@Throws(IOException::class)
fun modifyOrientation(
    bitmap: Bitmap,
    inputStream: InputStream
): Bitmap {
    val ei = ExifInterface(inputStream)
    return when (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotate(bitmap = bitmap, degrees = 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotate(bitmap = bitmap, degrees = 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotate(bitmap = bitmap, degrees = 270f)
        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flip(
            bitmap = bitmap,
            horizontal = true,
            vertical = false
        )
        ExifInterface.ORIENTATION_FLIP_VERTICAL -> flip(
            bitmap = bitmap,
            horizontal = false,
            vertical = true
        )
        else -> bitmap
    }
}

private fun rotate(bitmap: Bitmap, degrees: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

private fun flip(bitmap: Bitmap, horizontal: Boolean, vertical: Boolean): Bitmap {
    val matrix = Matrix()
    matrix.preScale(if (horizontal) -1F else 1F, if (vertical) -1F else 1F)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}