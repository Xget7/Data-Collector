package com.sublimetech.supervisor.core.location

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

fun checkAndRequestLocationPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    onPermissionAccepted: () -> Unit
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        // Use location because permissions are already granted
        onPermissionAccepted()
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}