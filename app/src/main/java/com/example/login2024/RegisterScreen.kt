package com.example.login2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.login2024.ui.theme.Login2024Theme

class RegisterScreen(navController: NavHostController) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Login2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterContent(navController = rememberNavController(), Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegisterContent(navController: NavController, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize()
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
                text = "Registro",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                modifier = Modifier.padding(top = 30.dp) // Espacio entre el shape y el texto
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
            Text(
                text = "Confirmar Contraseña",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(bottom = 8.dp) // Espacio entre el texto y el campo de confirmación de contraseña
            )
            PasswordTextField(
                password = confirmPassword,
                onPasswordChange = { confirmPassword = it },
                errorMessage = confirmPasswordError
            )
            Spacer(modifier = Modifier.height(24.dp))
            RegisterButton(
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                onError = { emailErr, passwordErr, confirmPasswordErr ->
                    emailError = emailErr
                    passwordError = passwordErr
                    confirmPasswordError = confirmPasswordErr
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "¿Ya tienes una cuenta? Inicia sesión",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }
    }
}

@Composable
fun RegisterButton(
    email: String,
    password: String,
    confirmPassword: String,
    onError: (emailError: String, passwordError: String, confirmPasswordError: String) -> Unit
) {
    Button(
        onClick = {
            // Lógica de validación y registro aquí
            var emailErr = ""
            var passwordErr = ""
            var confirmPasswordErr = ""

            // Validación del email
            if (email.isBlank()) {
                emailErr = "El email no puede estar vacío"
            } else if (!email.contains("@")) {
                emailErr = "El email no es válido"
            }

            // Validación de la contraseña
            if (password.isBlank()) {
                passwordErr = "La contraseña no puede estar vacía"
            } else if (password != "abcABC#123") {
                passwordErr = "La contraseña debe ser igual a 'abcABC#123'"
            }

            // Validación de la confirmación de la contraseña
            if (confirmPassword.isBlank()) {
                confirmPasswordErr = "La confirmación de la contraseña no puede estar vacía"
            } else if (confirmPassword != password) {
                confirmPasswordErr = "Las contraseñas no coinciden"
            }

            // Si hay errores, llamar a onError con los mensajes de error
            if (emailErr.isNotBlank() || passwordErr.isNotBlank() || confirmPasswordErr.isNotBlank()) {
                onError(emailErr, passwordErr, confirmPasswordErr)
            } else {
                // Lógica de registro exitosa aquí
            }
        }
    ) {
        Text("Registrarse")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    Login2024Theme {
        RegisterContent(navController = rememberNavController())
    }
}
