package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.ZainXRepository
import com.example.model.UserRole
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPurple

@Composable
fun AuthDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    var isSignUp by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("developer@zainx.io") }
    var password by remember { mutableStateOf("••••••••") }
    var name by remember { mutableStateOf("Developer Zain") }
    var selectedRole by remember { mutableStateOf(UserRole.DEVELOPER) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF0C1024),
            border = BorderStroke(1.dp, NeonPurple.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ZainXLogo(size = 32.dp, showWordmark = true)
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isSignUp) "Create your ZainX Account" else "Welcome to ZainX AppHub",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Text(
                    text = if (isSignUp) "Publish and discover next-gen software" else "Sign in to access downloads, dashboards & uploads",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Mode switch
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .background(Color(0xFF141936))
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                            .background(if (!isSignUp) NeonCyan else Color.Transparent)
                            .clickable { isSignUp = false }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sign In",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (!isSignUp) Color.Black else Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                            .background(if (isSignUp) NeonCyan else Color.Transparent)
                            .clickable { isSignUp = true }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sign Up",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isSignUp) Color.Black else Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isSignUp) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Display Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = Color(0xFF263366)
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Color(0xFF263366)
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Color(0xFF263366)
                    )
                )

                if (isSignUp) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Account Role:",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF94A3B8),
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(UserRole.USER, UserRole.DEVELOPER, UserRole.ADMIN).forEach { role ->
                            FilterChip(
                                selected = selectedRole == role,
                                onClick = { selectedRole = role },
                                label = { Text(role.displayName.substringBefore(" ")) }
                            )
                        }
                    }
                }

                if (statusMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = statusMessage ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = NeonCyan
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                PillButton(
                    text = if (isSignUp) "Create Free Account" else "Sign In",
                    onClick = {
                        ZainXRepository.login(email, name, selectedRole)
                        statusMessage = "Authenticated as ${selectedRole.displayName}!"
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isPrimary = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // One click Google sign in
                OutlinedButton(
                    onClick = {
                        ZainXRepository.login("google.user@gmail.com", "Google Verified User", UserRole.DEVELOPER)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Color(0xFF334155))
                ) {
                    Text(
                        text = "Continue with Google",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
