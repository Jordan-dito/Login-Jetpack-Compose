package com.example.login2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login2024.ui.theme.Login2024Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Login2024Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var showLoginDialog by remember { mutableStateOf(false) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Imagen (shape) que ocupa la mitad superior de la pantalla y todo el ancho
        Image(
            painter = painterResource(id = R.drawable.shape),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho
                .fillMaxHeight(fraction = 0.5f) // Ocupa la mitad superior de la pantalla
                .align(Alignment.TopCenter),
            contentScale = ContentScale.FillBounds
        )

        // Texto debajo del shape
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 250.dp), // Ajusta el padding para ubicar el texto correctamente
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ingreso",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Gray),
                modifier = Modifier.padding(top = 16.dp) // Espacio entre el shape y el texto
            )
        }

        // Contenido del formulario
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 320.dp) // Ajuste del padding superior para mover el contenido hacia abajo
                .padding(horizontal = 30.dp), // Asegura que el contenido tenga el padding horizontal deseado
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start // Alinea el contenido a la izquierda
        ) {
            Text(
                text = "Correo Electrónico",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(bottom = 8.dp) // Espacio entre el texto y el campo de email
            )
            EmailTextField(
                email = email,
                onEmailChange = { email = it },
                errorMessage = emailError
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Contraseña",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(bottom = 8.dp) // Espacio entre el texto y el campo de contraseña
            )
            PasswordTextField(
                password = password,
                onPasswordChange = { password = it },
                errorMessage = passwordError
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Recuérdame")
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Contraseña olvidada",
                    color = Color.Blue,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.clickable { showForgotPasswordDialog = true }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            LoginButton(
                email = email,
                password = password,
                onSuccess = { showLoginDialog = true },
                onError = { emailErr, passwordErr ->
                    emailError = emailErr
                    passwordError = passwordErr
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No tienes una cuenta? Regístrate",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }
    }

    // Alert Dialog for successful login
    if (showLoginDialog) {
        AlertDialog(
            onDismissRequest = { showLoginDialog = false },
            title = { Text("Inicio Exitoso") },
            text = { Text("¡Has iniciado sesión correctamente!") },
            confirmButton = {
                Button(onClick = { showLoginDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    // Alert Dialog for forgotten password
    if (showForgotPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showForgotPasswordDialog = false },
            title = { Text("Contraseña Olvidada") },
            text = { Text("¿Olvidaste tu contraseña? Por favor contacta al soporte.") },
            confirmButton = {
                Button(onClick = { showForgotPasswordDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun EmailTextField(email: String, onEmailChange: (String) -> Unit, errorMessage: String) {
    Column {
        TextField(
            value = email,
            onValueChange = { onEmailChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp) // Espacio entre el ícono y el texto
                        .border(1.dp, Color.Black, shape = CircleShape) // Borde cuadrado alrededor del ícono
                        .padding(8.dp) // Espacio dentro del borde
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email Icon",
                        tint = Color.Black
                    )
                }
            },
            placeholder = {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                )
            },
            isError = errorMessage.isNotEmpty(),
            shape = MaterialTheme.shapes.small,
            singleLine = true
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun PasswordTextField(password: String, onPasswordChange: (String) -> Unit, errorMessage: String) {
    val passwordVisible = remember { mutableStateOf(false) }

    Column {
        TextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp) // Espacio entre el ícono y el texto
                        .border(1.dp, Color.Black, shape = CircleShape) // Borde alrededor del ícono
                        .padding(8.dp) // Espacio dentro del borde
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password Icon",
                        tint = Color.Black
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        imageVector = if (passwordVisible.value) Icons.Filled.CheckCircle else Icons.Filled.Close,
                        contentDescription = if (passwordVisible.value) "Hide password" else "Show password",
                        tint = Color.Black
                    )
                }
            },
            placeholder = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                )
            },
            isError = errorMessage.isNotEmpty(),
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            visualTransformation = if (passwordVisible.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun LoginButton(email: String, password: String, onSuccess: () -> Unit, onError: (String, String) -> Unit) {
    Button(
        onClick = {
            val emailError = if (email.isBlank() || !email.contains('@')) "Correo inválido" else ""
            val passwordError = if (password != "abcABC#123") "Contraseña incorrecta" else ""

            if (emailError.isEmpty() && passwordError.isEmpty()) {
                onSuccess()
            } else {
                onError(emailError, passwordError)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF005C48))
    ) {
        Text("Login")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    // Pasar un NavController ficticio para la vista previa
    LoginScreen(navController = rememberNavController())
}

